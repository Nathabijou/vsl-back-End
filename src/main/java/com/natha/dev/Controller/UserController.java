package com.natha.dev.Controller;

import com.natha.dev.Configuration.EmailConfig;
import com.natha.dev.Dao.GroupeDao;
import com.natha.dev.Dao.UserDao;
import com.natha.dev.IService.Groupe_UsersIService;
import com.natha.dev.Model.Users;
import com.natha.dev.ServiceImpl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailConfig emailConfig;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private GroupeDao groupeDao;
    @Autowired
    private Groupe_UsersIService groupeUsersIService;



//pour creer un compte
    @PostMapping("/registerNewUser")
    public ResponseEntity<String> registerNewUserWithRole(@RequestBody Map<String, String> userRequest, Principal principal) {


//        String createdBy = principal.getName();
        String createdBy = userRequest.get("createdBy");
        String userName      = userRequest.get("userName");
        String userEmail     = userRequest.get("userEmail");
        String userPassword  = userRequest.get("userPassword");
        String userFirstName = userRequest.get("userFirstName");
        String userLastName  = userRequest.get("userLastName");
        String userSexe      = userRequest.get("userSexe");
        String roleName      = userRequest.get("roleName");
        String userTelephone = userRequest.get("userTelephone");



        // Vérifier si l'e-mail de l'utilisateur est vide ou nul
        if (userEmail == null || userEmail.isEmpty()) {
            throw new IllegalArgumentException("L'e-mail de l'utilisateur est requis.");
        }
        // Autres validations des données d'entrée

        // Enregistrer l'utilisateur avec son rôle en appelant le service utilisateur
        Users newUser = userService.registerNewUserWithRole(userName, userEmail, userPassword, userFirstName, userLastName, userSexe, roleName, createdBy, userTelephone);

        // Générer et envoyer l'OTP à l'utilisateur
        userService.sendActivationEmail( userEmail, userFirstName, userLastName, userName);

        // Retourner une réponse indiquant que l'utilisateur a été enregistré avec succès
        return ResponseEntity.ok(" ✅ Utilisateur enregistré avec succès. Un e-mail contenant un code OTP a été envoyé à " + userEmail );
    }

//pour permettre  a l'utilisateur de creer son mot de passe apres la creation du compte
    @PostMapping("/createPassword")
    public ResponseEntity<String> createPassword(@RequestBody Map<String, String> request) {
        String userEmail = request.get("userEmail");
        String newPassword = request.get("newPassword");
        String confirmPassword = request.get("confirmPassword");

        // Vérifier si les mots de passe correspondent
        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("Les mots de passe ne correspondent pas.");
        }

        // Créer un nouveau mot de passe pour l'utilisateur
        userService.createNewPassword(userEmail, newPassword);

        // Répondre pour indiquer que le mot de passe a été créé avec succès
        return ResponseEntity.ok("Mot de passe créé avec succès pour l'utilisateur avec l'email : " + userEmail);
    }


    //Apres les processus de verification pour forget password , l'utilisateur peut ajouter un password a nouveau
    @PostMapping("/newPasswordRegister")
    public ResponseEntity<String> createdPassword(@RequestBody Map<String, String> request) {
        String userEmail = request.get("userEmail");
        String newPassword = request.get("newPassword");
        String confirmPassword = request.get("confirmPassword");

        // Vérifier si les mots de passe correspondent
        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("Les mots de passe ne correspondent pas.");
        }
        userService.sendPasswordChangeConfirmation( userEmail);
        // Créer un nouveau mot de passe pour l'utilisateur
        userService.createNewPassword(userEmail, newPassword);



        // Répondre pour indiquer que le mot de passe a été créé avec succès
        return ResponseEntity.ok("Mot de passe créé avec succès pour l'utilisateur avec l'email : " + userEmail);
    }

// pour password oublier

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> requestBody) {
        String userEmail = requestBody.get("userEmail");
        System.out.println("📩 Requête reçue pour réinitialiser: " + userEmail);

        Users user = userService.findByEmail(userEmail);
        if (user == null) {
            System.out.println("❌ Email non trouvé: " + userEmail);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email non trouvé");
        }

        userService.resetPassword(userEmail); // Fè ou voye OTP a
        System.out.println("✅ OTP envoyé à: " + userEmail);
        return ResponseEntity.ok("OTP envoyé est:");
    }


    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOTP(@RequestBody Map<String, String> requestBody) {
        try {
            // Récupérer l'e-mail de l'utilisateur et le code OTP à partir du corps de la requête
            String userEmail = requestBody.get("userEmail");
            String otpCode = requestBody.get("otpCode");

            // Vérifier l'OTP
            boolean isOTPValid = userService.verifyOTP(userEmail, otpCode);

            if (isOTPValid) {
                // OTP valide
                return ResponseEntity.ok("OTP vérifié avec succès. Vous pouvez réinitialiser votre mot de passe.");
            } else {
                // OTP invalide
                return ResponseEntity.badRequest().body("Code OTP invalide. Veuillez réessayer.");
            }
        } catch (RuntimeException e) {
            // Erreur lors de la vérification
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Une erreur s'est produite lors de la vérification de l'OTP.");
        }
    }


    @PostMapping("/modifyPassword")
    public ResponseEntity<String> modifyPassword(@RequestBody Map<String, String> request) {
        String userEmail = request.get("userEmail");
        String oldPassword = request.get("oldPassword");
        String newPassword = request.get("newPassword");
        String confirmPassword = request.get("confirmPassword");

        // Vérifier si les mots de passe correspondent
        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("Les nouveaux mots de passe ne correspondent pas.");
        }

        // Modifier le mot de passe de l'utilisateur
        userService.modifyPassword(userEmail, oldPassword, newPassword);
        userService.sendPasswordChange( userEmail);

        // Répondre pour indiquer que le mot de passe a été modifié avec succès
        return ResponseEntity.ok("Mot de passe modifié avec succès pour l'utilisateur avec l'email : " + userEmail);
    }



    private String getCurrentUserName() {
        // Obtenez les détails de l'utilisateur actuellement authentifié à partir du contexte de sécurité
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Vérifiez si l'utilisateur est authentifié et si ses détails peuvent être extraits
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            return userDetails.getUsername(); // Renvoie le nom d'utilisateur de l'utilisateur authentifié
        } else {
            return null; // Retourne null si aucun utilisateur n'est authentifié
        }
    }


    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "This URL is only accessible to the admin";
    }

    @GetMapping({"/forManager"})
    @PreAuthorize("hasRole('Manager')")
    public String forManager(){
        return "This URL is only accessible to the Manager";
    }

    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "This URL is only accessible to the user";
    }


//    pour recuperer une lise utilisateur  dans un groupe specifique
    @GetMapping("/groupe/{groupeId}/users")
    public ResponseEntity<List<Users>> getUsersByGroupeId(@PathVariable Long groupeId) {
        List<Users> users = userService.getUsersByGroupe(groupeId);
        return ResponseEntity.ok(users);
    }

   //pour enregistrer une personne dans un groupe
    @PostMapping("/{groupeId}/users/{username}")
    public String addUserToGroupe(@PathVariable Long groupeId, @PathVariable String username) {
        userService.addUserToGroupe(username, groupeId);
        return "Utilisateur ajouté au groupe avec succès !";
    }
    @GetMapping("/AllUser")
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{userName}")  // 'users' olye de 'user'
    public ResponseEntity<Users> getUserByUserName(@PathVariable String userName) {
        Users user = userService.findByUserName(userName);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/users/{userName}")
    public ResponseEntity<Users> updateUser(
            @PathVariable String userName,
            @RequestBody Users updatedUserData) {

        Users existingUser = userService.findByUserName(userName);

        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }

        // ✅ Mete ajou sèlman chan ou vle modifye
        existingUser.setUserFirstName(updatedUserData.getUserFirstName());
        existingUser.setUserLastName(updatedUserData.getUserLastName());
        existingUser.setUserEmail(updatedUserData.getUserEmail());
        existingUser.setUserSexe(updatedUserData.getUserSexe());
        existingUser.setStatus(updatedUserData.isStatus());

        // Si w vle mete ajou role (optionnel)
        if (updatedUserData.getRole() != null) {
            existingUser.setRole(updatedUserData.getRole());
        }

        Users updatedUser = userService.saveUser(existingUser);

        return ResponseEntity.ok(updatedUser);
    }


}
