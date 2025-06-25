package com.natha.dev.Controller;

import com.natha.dev.Dto.ProjetDto;
import com.natha.dev.IService.ProjetIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projets")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjetController {

    @Autowired
    private ProjetIService projetIService;

    //Create Project with component and Quatier (Yes Verify)
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN','MANAGER','USER')")
    @PostMapping("/composante/{composanteId}/quartier/{quartierId}")
    public ResponseEntity<ProjetDto> createProjet(
            @PathVariable Long composanteId,
            @PathVariable String quartierId,
            @RequestBody ProjetDto projetDto) {
        ProjetDto createdProjet = projetIService.createProjetInQuartierAndComposante(projetDto, quartierId, composanteId);
        return ResponseEntity.ok(createdProjet);
    }

    //Get List Project with component  (Yes Verify)
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN','MANAGER','USER')")
    @GetMapping("/composante/{composanteId}")
    public List<ProjetDto> getProjetsByComposante(@PathVariable Long composanteId) {
        return projetIService.findByComposante(composanteId);
    }
    // Get List project with component and Quartier (Yes Verify)
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN','MANAGER','USER')")
    @GetMapping("/composante/{composanteId}/quartier/{quartierId}")
    public List<ProjetDto> getProjetsByComposanteAndQuartier(
            @PathVariable Long composanteId,
            @PathVariable String quartierId) {
        return projetIService.findByComposanteAndQuartier(composanteId, quartierId);
    }


    //Modify Project with component (Yes Verify)
    @PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','MANAGER')")
    @PutMapping("/composante/{composanteId}/projet/{idProjet}")
    public ResponseEntity<ProjetDto> updateByComposante(
            @PathVariable Long composanteId,
            @PathVariable String idProjet,
            @RequestBody ProjetDto dto) {
        return ResponseEntity.ok(
                projetIService.updateInComposante(composanteId, idProjet, dto)
        );
    }

    // 2) Update project with Component and Quartier
    @PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','MANAGER')")
    @PutMapping("/composante/{composanteId}/quartier/{quartierId}/projet/{idProjet}")
    public ResponseEntity<ProjetDto> updateByComposanteAndQuartier(
            @PathVariable Long composanteId,
            @PathVariable String quartierId,
            @PathVariable String idProjet,
            @RequestBody ProjetDto dto) {
        return ResponseEntity.ok(
                projetIService.updateInComposanteAndQuartier(composanteId, quartierId, idProjet, dto)
        );
    }


    //Get All project in the App (Yes Verify)
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN','MANAGER','USER')")
    @GetMapping("/All")
    public ResponseEntity<List<ProjetDto>> getAllProjets() {
        return ResponseEntity.ok(projetIService.findAll());
    }

    //Get Project With Id (Yes Verify)
    @GetMapping("/{idProjet}")
    public ResponseEntity<ProjetDto> getProjetById(@PathVariable String idProjet) {
        return projetIService.findById(idProjet)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Delete Project With Id (Yes Verify)
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @DeleteMapping("/{idProjet}")
    public ResponseEntity<Void> deleteProjet(@PathVariable String idProjet) {
        projetIService.deleteById(idProjet);
        return ResponseEntity.noContent().build();
    }

    //Enable Project (Yes Verify)
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PutMapping("/projet/{idProjet}/activate")
    public ResponseEntity<String> activateProjet(@PathVariable String idProjet) {
        projetIService.setProjetActiveStatus(idProjet, true);
        return ResponseEntity.ok("Projet activé avec succès");
    }

    //Disable Project (Yes Verify)
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PutMapping("/projet/{idProjet}/deactivate")
    public ResponseEntity<String> deactivateProjet(@PathVariable String idProjet) {
        projetIService.setProjetActiveStatus(idProjet, false);
        return ResponseEntity.ok("Projet désactivé avec succès");
    }


}
