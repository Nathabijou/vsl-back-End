package com.natha.dev.Controller;

import com.natha.dev.Dto.ProjetBeneficiaireDto;
import com.natha.dev.IService.ProjetBeneficiaireIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class ProjetBeneficiaireController {

    @Autowired
    private ProjetBeneficiaireIService projetBeneficiaireIService;

    @GetMapping("/projet/{projetId}/beneficiaires")
    public ResponseEntity<List<ProjetBeneficiaireDto>> getBeneficiairesByProjet(@PathVariable String projetId) {
        List<ProjetBeneficiaireDto> list = projetBeneficiaireIService.findByProjetId(projetId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
