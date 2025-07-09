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

        Users user = userDao.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé.");
        }

        if (!user.isStatus()) {
            throw new DisabledException("Votre compte est désactivé. Veuillez contacter l'administrateur.");
        }

        authenticate(userName, userPassword);

        UserDetails userDetails = loadUserByUsername(userName);
        String newGeneratedToken = jwtUtil.generateToken(userDetails);

        user.setLastLoginTime(LocalDateTime.now());
        userDao.save(user);

        return new JwtResponse(user, newGeneratedToken);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userDao.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getUserPassword(),
                getAuthority(user)
        );
    }

    private Set<SimpleGrantedAuthority> getAuthority(Users user) {
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
            throw new DisabledException("USER_DISABLED");
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS");
        }
    }

    public JwtResponse login(JwtRequest jwtRequest) throws Exception {
        String userName = jwtRequest.getUserName();
        String userPassword = jwtRequest.getUserPassword();

        Users user = userDao.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé.");
        }

        if (!user.isStatus()) {
            throw new DisabledException("Votre compte est désactivé. Veuillez contacter l'administrateur.");
        }

        authenticate(userName, userPassword);

        UserDetails userDetails = loadUserByUsername(userName);
        String jwtToken = jwtUtil.generateToken(userDetails);

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
            user.setLastLogoutTime(LocalDateTime.now());
            userDao.save(user);
        }
    }

    public boolean validateToken(String token) {
        String username = jwtUtil.getUsernameFromToken(token);
        UserDetails userDetails = loadUserByUsername(username);
        return jwtUtil.validateToken(token, userDetails);
    }


    public String extractUsername(String token) {
        // Sipoze ou gen yon JwtUtil ak metòd extractUsername ki egziste
        return jwtUtil.extractUsername(token);
    }



}
