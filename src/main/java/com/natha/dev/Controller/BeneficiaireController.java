package com.natha.dev.Controller;

import com.natha.dev.Dto.BeneficiaireDto;
import com.natha.dev.IService.BeneficiaireIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/beneficiaires")
public class BeneficiaireController {

    @Autowired
    private BeneficiaireIService beneficiaireIService;

    //Get All Beneficiaire (Yes Verify)
    @PostMapping("/create")
    public ResponseEntity<BeneficiaireDto> create(@RequestBody BeneficiaireDto dto) {
        return ResponseEntity.ok(beneficiaireIService.save(dto));
    }

    //Get All Beneficiaire (Yes Verify)
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping("/all")
    public List<BeneficiaireDto> getAll() {
        return beneficiaireIService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeneficiaireDto> getById(@PathVariable String idBeneficiaire) {
        Optional<BeneficiaireDto> dto = beneficiaireIService.findById(idBeneficiaire);
        return dto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String idBeneficiaire) {
        beneficiaireIService.deleteById(idBeneficiaire);
        return ResponseEntity.noContent().build();
    }
    // Transfer beneficiares with anoter project ((Yes Verify))
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PostMapping("/{idBeneficiaire}/projets/{idProjet}")
    public ResponseEntity<BeneficiaireDto> ajouterProjetDansBeneficiaire(
            @PathVariable String idBeneficiaire,
            @PathVariable String idProjet
    ) {
        return ResponseEntity.ok(beneficiaireIService.ajouterProjetDansBeneficiaire(idBeneficiaire, idProjet));
    }

    //Add Beneficiaire to project (Yes Verify)
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN','MANAGER','USER')")
    @PostMapping("/ajouterAvecProjet/{idProjet}")
    public ResponseEntity<BeneficiaireDto> ajouterBeneficiaireAvecProjet(
            @RequestBody BeneficiaireDto dto,
            @PathVariable String idProjet
    ) {
        return ResponseEntity.ok(beneficiaireIService.ajouterAvecProjet(dto, idProjet));
    }

    //get beneficiaire with id (yes verify)
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN','MANAGER','USER')")
    @GetMapping("/projets/{idProjet}/beneficiaires/{idBeneficiaire}")
    public ResponseEntity<BeneficiaireDto> getBeneficiaireByIdAndProjetId(
            @PathVariable String idProjet,
            @PathVariable String idBeneficiaire) {

        Optional<BeneficiaireDto> dto = beneficiaireIService.findByIdAndProjetId(idBeneficiaire, idProjet);
        return dto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //Modify benerifiaire with project (yes Verify)
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN','MANAGER','USER')")
    @PutMapping("/projets/{idProjet}/beneficiaires/{idBeneficiaire}")
    public ResponseEntity<BeneficiaireDto> updateBeneficiaireDansProjet(
            @PathVariable String idProjet,
            @PathVariable String idBeneficiaire,
            @RequestBody BeneficiaireDto dto
    ) {
        BeneficiaireDto updated = beneficiaireIService.updateBeneficiaireDansProjet(idProjet, idBeneficiaire, dto);
        return ResponseEntity.ok(updated);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @DeleteMapping("/projets/{idProjet}/beneficiaires/{idBeneficiaire}")
    public ResponseEntity<Void> retirerBeneficiaireDuProjet(
            @PathVariable String idProjet,
            @PathVariable String idBeneficiaire
    ) {
        beneficiaireIService.retirerBeneficiaireDuProjet(idProjet, idBeneficiaire);
        return ResponseEntity.noContent().build();
    }


}

