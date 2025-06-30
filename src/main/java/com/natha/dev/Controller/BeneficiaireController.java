package com.natha.dev.Controller;

import com.natha.dev.Dto.BeneficiaireDto;
import com.natha.dev.IService.BeneficiaireIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:4200")
public class BeneficiaireController {

    @Autowired
    private BeneficiaireIService beneficiaireIService;

    @GetMapping("/beneficiaires/beneficiaires/{beneficiaireId}")
    public ResponseEntity<BeneficiaireDto> getBeneficiaireById(@PathVariable String beneficiaireId) {
        return beneficiaireIService.findById(beneficiaireId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Get All Beneficiaire (Yes Verify)
    @PostMapping("/beneficiaires/create")
    public ResponseEntity<BeneficiaireDto> create(@RequestBody BeneficiaireDto dto) {
        return ResponseEntity.ok(beneficiaireIService.save(dto));
    }

     //Add Beneficiaire to project (Yes Verify)
    //@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN','MANAGER','USER')")
    @PostMapping("/beneficiaires/projets/{idProjet}/beneficiaires")
    public ResponseEntity<BeneficiaireDto> createBeneficiaireInProjet(
            @RequestBody BeneficiaireDto beneficiaireDto,
            @PathVariable String idProjet) {

        BeneficiaireDto createdBeneficiaire = beneficiaireIService.creerBeneficiaireEtAssocierAuProjet(beneficiaireDto, idProjet);
        return new ResponseEntity<>(createdBeneficiaire, HttpStatus.CREATED);
    }

    //Modify benerifiaire with project (yes Verify)
    //@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN','MANAGER','USER')")
    @PutMapping("/beneficiaires/projets/{projetId}/beneficiaires/{beneficiaireId}")
    public ResponseEntity<BeneficiaireDto> updateBeneficiaireDansProjet(
            @PathVariable String projetId,
            @PathVariable String beneficiaireId,
            @RequestBody BeneficiaireDto dto) {
        BeneficiaireDto updated = beneficiaireIService.updateBeneficiaireDansProjet(projetId, beneficiaireId, dto);
        return ResponseEntity.ok(updated);
    }

    //get beneficiaire with id (yes verify)
    //@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN','MANAGER','USER')")
    @GetMapping("/beneficiaires/projets/{projetId}/beneficiaires/{beneficiaireId}")
    public ResponseEntity<BeneficiaireDto> getBeneficiaireInProjet(
            @PathVariable String projetId,
            @PathVariable String beneficiaireId) {
        return beneficiaireIService.findBeneficiaireInProjet(projetId, beneficiaireId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    //Transfer Beneficiares with anoter project
    //@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN','MANAGER','USER')")
    @PutMapping("/beneficiaires/beneficiaires/{beneficiaireId}/transfer/{ancienProjetId}/{nouveauProjetId}")
    public ResponseEntity<String> transfererBeneficiaire(
            @PathVariable String beneficiaireId,
            @PathVariable String ancienProjetId,
            @PathVariable String nouveauProjetId) {
        beneficiaireIService.transfererBeneficiaireDansProjet(beneficiaireId, ancienProjetId, nouveauProjetId);
        return ResponseEntity.ok("Beneficiaire transfere soti nan pwojè " + ancienProjetId + " pou ale nan pwojè " + nouveauProjetId);
    }
    //@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @DeleteMapping("/beneficiaires/projets/{projetId}/beneficiaires/{beneficiaireId}")
    public ResponseEntity<String> deleteBeneficiaireFromProjet(
            @PathVariable String projetId,
            @PathVariable String beneficiaireId) {
        beneficiaireIService.deleteBeneficiaireFromProjet(beneficiaireId, projetId);
        return ResponseEntity.ok("Beneficiaire efase nan pwojè avèk siksè");
    }

    //Transferer un beneficiaire dans formation
    //@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN','MANAGER')")
    @PostMapping("/beneficiaires/beneficiaires/{idBeneficiaire}/formation/{idProjet}/{idFormation}")
    public ResponseEntity<?> ajouterBeneficiaireDansFormation(
            @PathVariable String idBeneficiaire,
            @PathVariable String idProjet,
            @PathVariable String idFormation) {
        beneficiaireIService.ajouterBeneficiaireDansFormation(idBeneficiaire, idProjet, idFormation);
        return ResponseEntity.ok("Successfully");
    }



}

