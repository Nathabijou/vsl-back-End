package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.RoleDao;
import com.natha.dev.Dao.UserDao;
import com.natha.dev.Exeption.MessagingException;
import com.natha.dev.Model.Role;
import com.natha.dev.Model.Users;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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

        // Enregistrer l'utilisateur dans la base de données
        Users savedUser = userDao.save(user);

        // Si l'enregistrement a réussi, envoyer le message d'activation par email
        if (savedUser != null) {
            sendActivationEmail(userEmail, userFirstName, userLastName, userName);
        }

        return savedUser;
    }

    public void sendActivationEmail(String userEmail, String userFirstName, String userLastName, String userName) {
        // Obtenir l'heure actuelle
        LocalTime currentTime = LocalTime.now();

        // Déterminer salutation en fonction de l'heure
        String greeting = "";
        if (currentTime.isAfter(LocalTime.MIDNIGHT) && currentTime.isBefore(LocalTime.NOON)) {
            greeting = "Bonjour";
        } else {
            greeting = "Bonsoir";
        }

        // Créer le message d'activation par e-mail
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("Activation de votre compte sur Top Hospital");

        // Créer le contenu du message
        String activationLink = "http://www.algoleaders.com/activate?email=" + userEmail;
        String emailContent = greeting + " " + userFirstName + " " + userLastName + ",\n\n";
        emailContent += "Nous vous informons que vous avez créé un compte avec l'adresse e-mail : " + userEmail + ".\n";
        emailContent += "Vous êtes maintenant membre de Top Hospital. Pour finaliser la création de votre compte, veuillez cliquer sur le lien suivant :\n\n";
        emailContent += activationLink + "\n\n";
        emailContent += "Votre nom d'utilisateur est : " + userName + "\n\n";
        emailContent += "Le lien expirera dans 72 heures.\n\n";
        emailContent += "Cordialement,\n\nTop Hospital";

        message.setText(emailContent);

        // Envoyer l'e-mail
        emailSender.send(message);
    }

    // Obtenir le mot de passe encodé
    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}