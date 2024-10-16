package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.UserDao;
import com.natha.dev.Model.JwtRequest;
import com.natha.dev.Model.JwtResponse;
import com.natha.dev.Model.Users;
import com.natha.dev.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
        String userName = jwtRequest.getUserName();
        String userPassword = jwtRequest.getUserPassword();
        authenticate(userName, userPassword);

        UserDetails userDetails = loadUserByUsername(userName);
        String newGeneratedToken = jwtUtil.generateToken(userDetails);

        Users user = userDao.findById(userName).get();

        user.setLastLoginTime(LocalDateTime.now());

        // Enregistrer les modifications dans la base de données
        userDao.save(user);

        return new JwtResponse(user, newGeneratedToken);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userDao.findById(username).get();

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getUserPassword(),
                    getAuthority(user)
            );
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    private Set getAuthority(Users user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });
        return authorities;
    }

    private void authenticate(String userName, String userPassword) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }



    public JwtResponse login(JwtRequest jwtRequest) throws Exception {
        String userName = jwtRequest.getUserName();
        String userPassword = jwtRequest.getUserPassword();

        // Vérifier les informations d'identification de l'utilisateur
        authenticate(userName, userPassword);

        // Si les informations d'identification sont valides, générer un token JWT
        UserDetails userDetails = loadUserByUsername(userName);
        String jwtToken = jwtUtil.generateToken(userDetails);

        // Récupérer les détails de l'utilisateur
        Users user = userDao.findByUserName(userName);

        // Retourner la réponse avec le token JWT et les détails de l'utilisateur
        return new JwtResponse(user, jwtToken);
    }
    public LocalDateTime getLastLoginTime(String userName) {
        Users user = userDao.findByUserName(userName);
        if (user != null) {
            return user.getLastLoginTime();
        }
        return null;
    }

    public void logout(String userName) {
        Users user = userDao.findByUserName(userName);
        if (user != null) {
            // Mettre à jour l'heure de déconnexion dans la base de données
            user.setLastLogoutTime(LocalDateTime.now());
            userDao.save(user);
        }
    }

}
