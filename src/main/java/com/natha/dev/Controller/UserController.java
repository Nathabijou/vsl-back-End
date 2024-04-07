package com.natha.dev.Controller;

import com.natha.dev.Configuration.EmailConfig;
import com.natha.dev.Dao.UserDao;
import com.natha.dev.Model.Users;
import com.natha.dev.ServiceImpl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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



    @PostMapping("/registerNewUser")
    public ResponseEntity<String> registerNewUserWithRole(@RequestBody Map<String, String> userRequest) {
        String userName = userRequest.get("userName");
        String userEmail = userRequest.get("userEmail");
        String userPassword = userRequest.get("userPassword");
        String userFirstName = userRequest.get("userFirstName");
        String userLastName = userRequest.get("userLastName");
        String roleName = userRequest.get("roleName");

        // Vérifier si l'e-mail de l'utilisateur est vide ou nul
        if (userEmail == null || userEmail.isEmpty()) {
            throw new IllegalArgumentException("L'e-mail de l'utilisateur est requis.");
        }

        // Autres validations des données d'entrée

        // Enregistrer l'utilisateur avec son rôle en appelant le service utilisateur
        Users newUser = userService.registerNewUserWithRole(userName, userEmail, userPassword, userFirstName, userLastName, roleName);

        // Générer et envoyer l'OTP à l'utilisateur
        userService.sendActivationEmail( userEmail, userFirstName, userLastName, userName);

        // Retourner une réponse indiquant que l'utilisateur a été enregistré avec succès
        return ResponseEntity.ok("Utilisateur enregistré avec succès. Un e-mail contenant un code OTP a été envoyé à " + userEmail + ".");
    }


    @PostMapping("/createPassword")
    public ResponseEntity<String> createPassword(@RequestBody Map<String, String> request) {
        String userEmail = request.get("userEmail");


        String userFirstName = request.get("userFirstName");
        String userLastName = request.get("userLastName");
        String userName = request.get("userName");
        String newPassword = request.get("newPassword");
        // Appel du service pour créer ou mettre à jour le mot de passe
        if (newPassword != null && !newPassword.isEmpty()) {
            userService.createPassword(userEmail, newPassword);
        } else {
            // Gérer le cas où aucun nouveau mot de passe n'est fourni
            // Par exemple, vous pouvez ignorer la mise à jour du mot de passe
            // ou définir un mot de passe par défaut, selon les besoins de votre application
        }
        userService.sendPasswordCreationEmail(userEmail,  userFirstName , userLastName , userName );

        // Réponse indiquant que le mot de passe a été créé ou mis à jour avec succès
        return ResponseEntity.ok("Mot de passe créé ou mis à jour avec succès pour l'utilisateur avec l'email : " + userEmail);
    }


    @PostMapping("/reset")
    public void resetPassword(@RequestBody Map<String, String> requestBody) {
        // Récupérer l'e-mail de l'utilisateur à partir du corps de la requête
        String userEmail = requestBody.get("userEmail");

        // Vérifier si l'e-mail existe dans la base de données
        Users user = userService.findByEmail(userEmail);
        if (user == null) {
            throw new RuntimeException("Utilisateur non trouvé pour l'e-mail : " + userEmail);
        }

        // Réinitialiser le mot de passe
        userService.resetPassword(userEmail);
    }



    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOTP(@RequestParam("userEmail") String userEmail,
                                            @RequestParam("otpCode") String otpCode) {
        try {
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



    @PostMapping("/update")
    public void updatePassword(@RequestParam("email") String userEmail,
                               @RequestParam("newPassword") String newPassword,
                               @RequestParam("otpCode") String otpCode) {
        userService.updatePassword(userEmail, newPassword, otpCode);
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
}
