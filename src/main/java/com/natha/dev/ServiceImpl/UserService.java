package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.RoleDao;
import com.natha.dev.Dao.UserDao;
import com.natha.dev.Exeption.MessagingException;
import com.natha.dev.Model.Role;
import com.natha.dev.Model.Users;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalTime;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private UserService userService;

    // Initialiser les rôles et les utilisateurs
    public void initRoleAndUser() {
        // Créer le rôle Admin
        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Rôle d'administrateur");
        roleDao.save(adminRole);

        // Créer le rôle Manager
        Role managerRole = new Role();
        managerRole.setRoleName("Manager");
        managerRole.setRoleDescription("Rôle de gestionnaire");
        roleDao.save(managerRole);

        // Créer le rôle User
        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Rôle utilisateur");
        roleDao.save(userRole);

        // Créer le rôle Moderant
        Role moderantRole = new Role();
        moderantRole.setRoleName("Moderant");
        moderantRole.setRoleDescription("Rôle de modérateur");
        roleDao.save(moderantRole);
    }


    public Users registerNewUserWithRole(String userName, String userEmail, String userPassword, String userFirstName, String userLastName, String roleName) {
        // Vérifier si l'utilisateur avec le même nom d'utilisateur existe déjà
        Users existingUser = userDao.findByUserName(userName);
        if (existingUser != null) {
            throw new RuntimeException("L'utilisateur avec le nom d'utilisateur '" + userName + "' existe déjà.");
        }

        // Vérifier si l'email est déjà enregistré dans la base de données
        existingUser = userDao.findByUserEmail(userEmail);
        if (existingUser != null) {
            throw new RuntimeException("L'email '" + userEmail + "'  est déjà enregistré.");
        }

        // Récupérer le rôle associé au nom de rôle donné
        Role role = roleDao.findByRoleName(roleName);
        if (role == null) {
            throw new RuntimeException("Rôle '" + roleName + "' non trouvé.");
        }

        // Créer un nouvel ensemble de rôles et y ajouter le rôle
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);

        // Créer un nouvel utilisateur
        Users user = new Users();
        user.setUserName(userName);
        user.setUserEmail(userEmail);
        user.setUserPassword(getEncodedPassword(userPassword));
        user.setUserFirstName(userFirstName);
        user.setUserLastName(userLastName);
        user.setRole(userRoles);

        user.setUserPassword(userPassword != null ? getEncodedPassword(userPassword) : "");

        // Enregistrer l'utilisateur dans la base de données
        Users savedUser = userDao.save(user);

        // Si l'enregistrement a réussi, envoyer le message d'activation par email
        if (savedUser != null) {
            sendActivationEmail(userEmail, userFirstName, userLastName, userName);
        }

        return savedUser;
    }

    @Value("${jwt.secret}")
    private String jwtSecret;

    public void sendActivationEmail(String userEmail, String userFirstName, String userLastName, String userName) {
        // Generate JWT token
        String jwtToken = Jwts.builder()
                .setSubject(userEmail) // You can customize the token payload
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 72 * 60 * 60 * 1000)) // 72 hours expiration
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        // Create activation link with JWT token

        String activationLink =  jwtToken;
        //String activationLink = "http://www.algoleaders.com/activate?token=" + jwtToken;


        // Get current time
        LocalTime currentTime = LocalTime.now();

        // Determine greeting based on the current time
        String greeting = "";
        if (currentTime.isAfter(LocalTime.MIDNIGHT) && currentTime.isBefore(LocalTime.NOON)) {
            greeting = "Bonjour";
        } else {
            greeting = "Bonsoir";
        }

        // Prepare email message
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userEmail);
        mailMessage.setSubject("Activation de votre compte sur Top Hospital");
        mailMessage.setText(greeting + " " + userFirstName + " " + userLastName + ",\n\n"
                + "Nous vous informons que vous avez créé un compte avec l'adresse e-mail : " + userEmail + ".\n"
                + " Pour finaliser la création de votre compte, veuillez cliquer sur le lien suivant :\n"
                + activationLink + "\n\n"
                + "Votre nom d'utilisateur est : " + userName + "\n\n"
                + "Le lien expirera dans 72 heures.\n\n"
                + "Cordialement,\n\nTop Hospital");

        // Send email
        emailSender.send(mailMessage);
    }


    // Obtenir le mot de passe encodé
    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }



    public void createPassword(String userEmail, String newPassword) {
        // Récupérer l'utilisateur par son email
        Users user = userDao.findByUserEmail(userEmail);
        if (user == null) {
            throw new RuntimeException("Utilisateur non trouvé pour l'email : " + userEmail);
        }

        // Mettre à jour le mot de passe de l'utilisateur
        user.setUserPassword(passwordEncoder.encode(newPassword));
        userDao.save(user);

        // Envoyer l'email informant l'utilisateur que son mot de passe a été créé avec succès
        sendPasswordCreationEmail(userEmail, user.getUserFirstName(), user.getUserLastName(), user.getUserName());
    }


    // Méthode pour envoyer un email informant l'utilisateur que son mot de passe a été créé avec succès
    public void sendPasswordCreationEmail(String userEmail, String userFirstName, String userLastName, String userName) {
        // Préparer le contenu de l'e-mail
        String emailContent = "Bonjour " + userFirstName + " " + userLastName + ",\n\n"
                + "Votre mot de passe a été créé avec succès pour le compte associé à l'adresse e-mail : " + userEmail + ".\n\n"
                + "Vous êtes maintenant membre de Top Hospital. Vous pouvez maintenant vous connecter à votre compte en utilisant votre nom d'utilisateur : " + userName + ".\n\n"
                + "Cordialement,\n\nTop Hospital";

        // Créer le message d'e-mail
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);

        message.setSubject("Création de mot de passe réussie");
        message.setText(emailContent);

        // Envoyer l'e-mail
        emailSender.send(message);
    }



}