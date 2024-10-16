package com.natha.dev.Controller;

import com.natha.dev.Model.JwtRequest;
import com.natha.dev.Model.JwtResponse;
import com.natha.dev.ServiceImpl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @PostMapping({"/authenticate"})
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return jwtService.createJwtToken(jwtRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Récupérez le nom d'utilisateur à partir du token JWT ou de la session
        String userName = getCurrentUserName(); // Remplacez cette méthode par votre propre logique pour obtenir le nom d'utilisateur

        // Appelez la méthode de logout du service d'authentification
        jwtService.logout(userName);

        // Répondez avec un message de succès
        return ResponseEntity.ok("Vous avez été déconnecté avec succès.");
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


}
