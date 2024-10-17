package com.natha.dev.Controller;

import com.natha.dev.Dto.CompteDto;
import com.natha.dev.IService.CompteISercive;
import com.natha.dev.Model.Compte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompteController {

    @Autowired
    private CompteISercive compteISercive;

    @GetMapping("/user/{username}")
    List<CompteDto> compteDtos(@PathVariable String username) {
        return compteISercive.findByUserName(username);
    }




//    @PostMapping("/create/{groupeId}/{userName}")
//    public ResponseEntity<CompteDto> createCompte(@PathVariable Long groupeId,
//                                                  @PathVariable String userName,
//                                                  @RequestBody CompteDto compteDto) {
//        try {
//            CompteDto newCompte = compteISercive.createCompteForUserInGroupe(userName, groupeId, compteDto);
//            return ResponseEntity.status(HttpStatus.CREATED).body(newCompte);
//        } catch (RuntimeException e) {
//            // Afficher le message d'erreur dans les logs pour le débogage
//            e.printStackTrace(); // Ajoutez cette ligne pour voir l'erreur
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//    }


}
