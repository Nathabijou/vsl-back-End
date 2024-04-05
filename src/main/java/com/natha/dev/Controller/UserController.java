package com.natha.dev.Controller;

import com.natha.dev.Configuration.EmailConfig;
import com.natha.dev.Model.Users;
import com.natha.dev.ServiceImpl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
        // ...

        // Enregistrer l'utilisateur avec son rôle en appelant le service utilisateur
        Users newUser = userService.registerNewUserWithRole(userName, userEmail, userPassword, userFirstName, userLastName, roleName);

        // Générer et envoyer l'OTP à l'utilisateur
        userService.sendActivationEmail( userEmail, userFirstName, userLastName, userName);

        // Retourner une réponse indiquant que l'utilisateur a été enregistré avec succès
        return ResponseEntity.ok("Utilisateur enregistré avec succès. Un e-mail contenant un code OTP a été envoyé à " + userEmail + ".");
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
