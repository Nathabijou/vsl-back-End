package com.natha.dev.Controller;

import com.natha.dev.Dto.PresenceDto;
import com.natha.dev.IService.PresenceIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class PresenceController {

    @Autowired
    private PresenceIService presenceIService;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN','MANAGER','USER')")
    @PostMapping("/presencesss/{projetId}/{beneficiaireId}")
    public ResponseEntity<PresenceDto> ajouterPresence(
            @PathVariable String projetId,
            @PathVariable String beneficiaireId,
            @RequestBody PresenceDto dto) {
        return ResponseEntity.ok(presenceIService.ajouterPresence(projetId, beneficiaireId, dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN','MANAGER','USER')")
    @GetMapping("/presencess/{projetId}/{beneficiaireId}")
    public ResponseEntity<List<PresenceDto>> getPresences(
            @PathVariable String projetId,
            @PathVariable String beneficiaireId) {
        return ResponseEntity.ok(presenceIService.getPresencesByProjetBeneficiaire(projetId, beneficiaireId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN','MANAGER','USER')")
    @PutMapping("/presences/{idPresence}")
    public ResponseEntity<PresenceDto> modifierPresence(
            @PathVariable String idPresence,
            @RequestBody PresenceDto dto) {
        return ResponseEntity.ok(presenceIService.modifierPresence(idPresence, dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN','MANAGER','USER')")
    @DeleteMapping("/presence/{idPresence}")
    public ResponseEntity<String> supprimerPresence(@PathVariable String idPresence) {
        presenceIService.supprimerPresence(idPresence);
        return ResponseEntity.ok("Presence supprime avèk siksè");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN','MANAGER','USER')")
    @PostMapping("/projet/{projetId}/beneficiaire/{beneficiaireId}/formation/{formationId}")
    public ResponseEntity<PresenceDto> ajouterPresenceFormation(
            @PathVariable String projetId,
            @PathVariable String beneficiaireId,
            @PathVariable String formationId,
            @RequestBody PresenceDto dto) {
        return ResponseEntity.ok(presenceIService.ajouterPresenceFormation(projetId, beneficiaireId, formationId, dto));
    }

    // Liste presence pou yon beneficiaire nan yon formation nan yon projet
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN','MANAGER','USER')")
    @GetMapping("/projet/{projetId}/beneficiaire/{beneficiaireId}/formation/{formationId}")
    public ResponseEntity<List<PresenceDto>> getPresencesByProjetBeneficiaireFormation(
            @PathVariable String projetId,
            @PathVariable String beneficiaireId,
            @PathVariable String formationId) {
        return ResponseEntity.ok(presenceIService.getPresencesByProjetBeneficiaireFormation(projetId, beneficiaireId, formationId));
    }
}
