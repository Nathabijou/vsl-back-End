package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.GroupeDao;
import com.natha.dev.Dao.Groupe_UserDao;
import com.natha.dev.Dao.RoleDao;
import com.natha.dev.Dao.UserDao;
import com.natha.dev.Model.Groupe;
import com.natha.dev.Model.Groupe_Users;
import com.natha.dev.Model.Role;
import com.natha.dev.Model.Users;
import com.natha.dev.Util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.internet.MimeMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@EnableAsync
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender emailSender;
    // Removed self-injection to prevent circular dependency
    @Autowired
    private Groupe_UserDao groupeUserDao;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private GroupeDao groupeDao;

    // Initialiser les rôles et les utilisateurs
    public void initRoleAndUser() {

        // Créer le rôle Admin
        Role superAadminRole = new Role();
        superAadminRole.setRoleName("SUPERADMIN");
        superAadminRole.setRoleDescription("Rôle super administrateur");
        roleDao.save(superAadminRole);

        // Créer le rôle Admin
        Role adminRole = new Role();
        adminRole.setRoleName("ADMIN");
        adminRole.setRoleDescription("Rôle d'administrateur");
        roleDao.save(adminRole);

        // Créer le rôle Manager
        Role managerRole = new Role();
        managerRole.setRoleName("MANAGER");
        managerRole.setRoleDescription("Rôle de gestionnaire");
        roleDao.save(managerRole);

        // Créer le rôle User
        Role userRole = new Role();
        userRole.setRoleName("USER");
        userRole.setRoleDescription("Rôle utilisateur");
        roleDao.save(userRole);

        // Créer le rôle Moderant
        Role moderantRole = new Role();
        moderantRole.setRoleName("MODERANT");
        moderantRole.setRoleDescription("Rôle de modérateur");
        roleDao.save(moderantRole);
    }


    public Users registerNewUserWithRole(String userName, String userEmail, String userPassword,
                                         String userFirstName, String userLastName, String userSexe,
                                         String roleName, String createdBy, String userTelephone, String userIdentification) {

        // Vérification utilisateur existant
        Users existingUser = userDao.findByUserName(userName);
        if (existingUser != null) {
            throw new RuntimeException("L'utilisateur avec le nom d'utilisateur '" + userName + "' existe déjà.");
        }


        existingUser = userDao.findByUserEmail(userEmail);
        if (existingUser != null) {
            throw new RuntimeException("L'email '" + userEmail + "' est déjà enregistré.");
        }

        Optional<Users> existingUserOpt = userDao.findByUserIdentification(userIdentification);
        if (existingUserOpt.isPresent()) {
            throw new RuntimeException("Le Numero d'identification " + userIdentification + " est déjà enregistré.");
        }


        // Récupération rôle
        Role role = roleDao.findByRoleName(roleName);
        if (role == null) {
            throw new RuntimeException("Rôle '" + roleName + "' non trouvé.");
        }

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);

        // Création utilisateur
        Users user = new Users();
        user.setUserName(userName);
        user.setUserEmail(userEmail);
        user.setUserFirstName(userFirstName);
        user.setUserLastName(userLastName);
        user.setUserSexe(userSexe);
        user.setRole(userRoles);
        user.setCreatedBy(createdBy);
        user.setStatus(true);
        user.setCreateDate(new Date());
        user.setUserTelephone(userTelephone);
        user.setUserIdentification(userIdentification);

        // Encode et set mot de passe (assure que userPassword != null)
        if (userPassword != null && !userPassword.isEmpty()) {
            user.setUserPassword(getEncodedPassword(userPassword));
        } else {
            throw new RuntimeException("Le mot de passe ne peut pas être vide.");
        }

        // Sauvegarde utilisateur
        Users savedUser = userDao.save(user);

        if (savedUser != null) {
            this.sendActivationEmail(userEmail, userFirstName, userLastName, userName);
        }

        return savedUser;
    }


    @Async
    public void sendActivationEmail(String userEmail, String userFirstName, String userLastName, String userName) {
        try {
            // Generate JWT token using JwtUtil
            UserDetails userDetails = User.builder()
                .username(userEmail)
                .password("") // Password is not needed for token generation
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
            
            String jwtToken = jwtUtil.generateToken(userDetails);
            String activationLink = "https://padf.up.railway.app/activation?token=" + jwtToken;
            String greeting = getCurrentTimeGreeting();

            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom("haitipadf@gmail.com", "PADF Team");
            helper.setTo(userEmail);
            helper.setSubject("Activation de votre compte");
            
            String emailContent = String.format("""
                <html>
                <body>
                    <p>%s %s %s,</p>
                    <p>Nous vous informons que vous avez créé un compte avec l'adresse e-mail : %s</p>
                    <p>Pour finaliser la création de votre compte, veuillez cliquer sur le lien suivant :</p>
                    <p><a href="%s">Activer mon compte</a></p>
                    <p>Votre nom d'utilisateur est : <strong>%s</strong></p>
                    <br>
                    <p>Cordialement,<br>L'équipe PADF</p>
                </body>
                </html>
                """, greeting, userFirstName, userLastName, userEmail, activationLink, userName);
            
            helper.setText(emailContent, true); // true = isHtml
            
            // Send email with retry logic
            int maxRetries = 3;
            int attempt = 0;
            boolean sent = false;
            
            while (attempt < maxRetries && !sent) {
                attempt++;
                try {
                    emailSender.send(message);
                    sent = true;
                    logger.info("Activation email sent to: {}", userEmail);
                } catch (Exception e) {
                    if (attempt == maxRetries) {
                        logger.error("Failed to send activation email after {} attempts to: {}", maxRetries, userEmail, e);
                        // Consider adding the email to a queue for later retry
                    } else {
                        logger.warn("Attempt {}/{} failed to send email to: {}", attempt, maxRetries, userEmail, e);
                        Thread.sleep(2000); // Wait 2 seconds before retry
                    }
                }
            }
            
        } catch (Exception e) {
            logger.error("Failed to prepare or send activation email to: " + userEmail, e);
            // Don't fail the entire registration if email sending fails
            // The account is still created, but the user might need to request a new activation email
        }
    }
    
    private String getCurrentTimeGreeting() {
        LocalTime currentTime = LocalTime.now();
        if (currentTime.isBefore(LocalTime.NOON)) {
            return "Bonjour";
        } else if (currentTime.isBefore(LocalTime.of(18, 0))) {
            return "Bon après-midi";
        } else {
            return "Bonsoir";
        }
    }


    // Obtenir le mot de passe encodé
    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }



    public void createNewPassword(String userEmail, String newPassword) {
        Users user = userDao.findByUserEmail(userEmail);
        if (user == null) {
            throw new RuntimeException("Utilisateur non trouvé pour l'e-mail : " + userEmail);
        }

        // Mettre à jour le mot de passe de l'utilisateur avec le nouveau mot de passe
        user.setUserPassword(passwordEncoder.encode(newPassword));
        userDao.save(user);
    }



    // Méthode pour envoyer un email informant l'utilisateur que son mot de passe a été créé avec succès
    public void sendPasswordCreationEmail(String userEmail, String userFirstName, String userLastName, String userName) {
        // Préparer le contenu de l'e-mail
        //String emailContent = "Bonjour " + userFirstName + " " + userLastName + ",\n\n"
        String emailContent =    ""
                + "Votre mot de passe a été créé avec succès pour le compte associé à l'adresse e-mail : \n\n" + userEmail + ".\n\n"
                + "Vous pouvez maintenant vous connecter à votre compte en utilisant votre nom d'utilisateur dans le message précédent.\n\n";


        // Créer le message d'e-mail
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("Création de mot de passe réussie");
        message.setText(emailContent);

        // Envoyer l'e-mail
        emailSender.send(message);
    }


    //pour reset password
    // Méthode pour réinitialiser le mot de passe
    private Map<String, String> otpMap = new HashMap<>();

    // Méthode pour réinitialiser le mot de passe
    public void resetPassword(String userEmail) {
        // Vérifier si l'utilisateur existe avec cet e-mail
        Users user = userDao.findByUserEmail(userEmail);
        if (user == null) {
            throw new RuntimeException("Utilisateur non trouvé pour l'e-mail : " + userEmail);
        }

        // Générer un code OTP aléatoire
        String otpCode = generateOTP();

        // Stocker le code OTP dans l'objet Users
        user.setOtpCode(otpCode);

        // Mettre à jour l'objet Users dans la base de données
        userDao.save(user);

        // Envoyer le code OTP par e-mail
        sendOTPByEmail(userEmail, otpCode);
    }


    private void sendOTPByEmail(String userEmail, String otpCode) {
        // Préparer le contenu de l'e-mail
        String emailContent = "Votre code OTP est :\n\n " + otpCode + "\n\n"
                + "Veuillez utiliser ce code pour confirmer votre demande .\n\n"
                + ".\n\n"
                + ",\n\n \n" +
                " \n" +
                "        \n";

        // Créer le message d'e-mail
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("Reset Password");
        message.setText(emailContent);

        // Envoyer l'e-mail
        emailSender.send(message);
    }


    // Méthode pour générer un code OTP aléatoire
    private String generateOTP() {
        // Générer un code OTP aléatoire à 6 chiffres
        int otpLength = 6;
        String digits = "0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder(otpLength);
        for (int i = 0; i < otpLength; i++) {
            otp.append(digits.charAt(random.nextInt(digits.length())));
        }
        return otp.toString();
    }

    // Méthode pour envoyer le code OTP par e-mail






    // Méthode pour vérifier si le code OTP est valide
    public boolean verifyOTP(String userEmail, String otpCode) {
        try {
            // Récupérer l'utilisateur par son e-mail
            Users user = userDao.findByUserEmail(userEmail);

            if (user == null) {
                throw new RuntimeException("Utilisateur non trouvé pour l'e-mail : " + userEmail);
            }

            // Récupérer l'OTP stocké dans l'objet Users
            String storedOTP = user.getOtpCode();

            // Vérifier si l'OTP stocké est non null et correspond au code OTP fourni
            if (storedOTP != null && storedOTP.equals(otpCode)) {
                // Si l'OTP correspond, vous pouvez effacer l'OTP stocké dans l'objet User,
                // car il ne devrait être utilisé qu'une seule fois
                user.setOtpCode(null);
                userDao.save(user); // Enregistrer les modifications dans la base de données
                return true;
            } else {
                // Si l'OTP ne correspond pas, retourner false
                return false;
            }
        } catch (Exception e) {
            // Gérer toute exception survenue lors de la vérification de l'OTP
            throw new RuntimeException("Une erreur s'est produite lors de la vérification de l'OTP.", e);
        }
    }





    // Méthode pour mettre à jour le mot de passe
    public void modifyPassword(String userEmail, String oldPassword, String newPassword) {
        // Récupérer l'utilisateur par son email
        Users user = userDao.findByUserEmail(userEmail);
        if (user == null) {
            throw new RuntimeException("Utilisateur non trouvé pour l'e-mail : " + userEmail);
        }

        // Vérifier si l'ancien mot de passe est correct
        if (!passwordEncoder.matches(oldPassword, user.getUserPassword())) {
            throw new RuntimeException("L'ancien mot de passe est incorrect.");
        }

        // Mettre à jour le mot de passe de l'utilisateur
        user.setUserPassword(passwordEncoder.encode(newPassword));
        userDao.save(user);
    }

    public void sendPasswordChangeConfirmation(String userEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("Password reset Confirmation");
        message.setText("Successful .");

        emailSender.send(message);
    }
    public void sendPasswordChange(String userEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("Confirmation de changement de mot de passe");
        message.setText("Felicitaion \n Votre mot de passe a été modifié avec succès.");

        emailSender.send(message);
    }


    // Dans votre service ou contrôleur, enregistrez l'heure de connexion lorsqu'un utilisateur se connecte
    public void updateLastLoginTime(String userEmail) {
        Users user = userDao.findByUserEmail(userEmail);
        if (user != null) {
            user.setLastLoginTime(LocalDateTime.now());
            userDao.save(user);
        }
    }

    public LocalDateTime getLastLoginTime(String userEmail) {
        Users user = userDao.findByUserEmail(userEmail);
        if (user != null) {
            return user.getLastLoginTime();
        }
        return null;
    }


    public Users findByEmail(String userEmail) {
        return userDao.findByUserEmail(userEmail);
    }


   public List<Users> getUsersByGroupe(Long groupeId) {

        List<Groupe_Users> groupeUsers = groupeUserDao.findByGroupeId(groupeId);
        return groupeUsers.stream()
                .map(Groupe_Users::getUsers)
                .collect(Collectors.toList());
    }

//Pour ajouter une liste utilisateur dans un groupe specifique

    public void addUserToGroupe(String username, Long groupeId) {
        // Récupérer l'utilisateur par username
        Optional<Users> optionalUser = Optional.ofNullable(userDao.findByUserName(username));
        Users user = optionalUser.orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Récupérer le groupe
        Optional<Groupe> optionalGroupe = groupeDao.findById(groupeId);
        Groupe groupe = optionalGroupe.orElseThrow(() -> new RuntimeException("Groupe non trouvé"));

        // Créer la relation Groupe_Users
        Groupe_Users groupeUsers = new Groupe_Users();
        groupeUsers.setUsers(user);
        groupeUsers.setGroupe(groupe);

        // Sauvegarder la relation
        groupeUserDao.save(groupeUsers);
    }

    // Voir liste Beneficiaire creer par un Admin
    public List<Users> getUsersCreatedBy(String creatorUsername) {
        return userDao.findByCreatedBy(creatorUsername);
    }

    public List<Users> getUsersCreatedByNotInGroupe(String creatorUsername, Long groupeId) {
        // 1. Jwenn tout user ki te kreye pa creatorUsername
        List<Users> createdUsers = userDao.findByCreatedBy(creatorUsername);

        // 2. Jwenn tout user ki **deja** nan groupe sa
        List<Groupe_Users> groupeUsers = groupeUserDao.findByGroupeId(groupeId);
        Set<String> usernamesInGroup = groupeUsers.stream()
                .map(gu -> gu.getUsers().getUserName())
                .collect(Collectors.toSet());

        // 3. Retire tout moun ki deja nan groupe a
        return createdUsers.stream()
                .filter(user -> !usernamesInGroup.contains(user.getUserName()))
                .collect(Collectors.toList());
    }


    public Users findByUserName(String userName) {
        return userDao.findByUserName(userName);
    }
    public List<Users> getAllUsers() {
        List<Users> userList = new ArrayList<>();
        userDao.findAll().forEach(userList::add);
        return userList;
    }


    public Users saveUser(Users user) {
        return userDao.save(user);
    }

    public void removeUsersFromGroupe(Long groupeId, List<String> usernames) {
        for (String username : usernames) {
            // Rechèch relasyon Groupe_Users ki konekte user ak groupe
            Optional<Groupe_Users> groupeUserOpt = groupeUserDao.findByGroupeIdAndUsersUserName(groupeId, username);

            if (groupeUserOpt.isPresent()) {
                groupeUserDao.delete(groupeUserOpt.get());
            }
        }
    }


}