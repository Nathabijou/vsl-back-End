package com.natha.dev.IService;

import com.natha.dev.Dto.ProjetBeneficiaireDto;
import java.util.List;
import java.util.Optional;

public interface ProjetBeneficiaireIService {

    ProjetBeneficiaireDto save(ProjetBeneficiaireDto dto);

    List<ProjetBeneficiaireDto> findAll();

    Optional<ProjetBeneficiaireDto> findById(String idProjetBeneficiaire);

    void deleteById(String idProjetBeneficiaire);

    // Metòd pou kreye yon relasyon ant yon projet ak beneficiaire (pase ID yo)
   ProjetBeneficiaireDto creerRelationProjetBeneficiaire(String projetId, String beneficiaireId);


    List<ProjetBeneficiaireDto> findByProjetId(String projetId);

}
