package com.natha.dev.IService;

import com.natha.dev.Dto.BeneficiaireDto;
import java.util.List;
import java.util.Optional;

public interface BeneficiaireIService {


    BeneficiaireDto save(BeneficiaireDto dto);

    void deleteBeneficiaireFromProjet(String beneficiaireId, String projetId);



    BeneficiaireDto creerBeneficiaireEtAssocierAuProjet(BeneficiaireDto dto, String idProjet);

    BeneficiaireDto updateBeneficiaireDansProjet(String projetId, String beneficiaireId, BeneficiaireDto dto);
    Optional<BeneficiaireDto> findBeneficiaireInProjet(String projetId, String beneficiaireId);


    void transfererBeneficiaireDansProjet(String beneficiaireId, String ancienProjetId, String nouveauProjetId);

    void ajouterBeneficiaireDansFormation(String idBeneficiaire, String idProjet, String idFormation);

    Optional<BeneficiaireDto> findById(String beneficiaireId);
}
