package com.natha.dev.IService;

import com.natha.dev.Dto.BeneficiaireDto;
import java.util.List;
import java.util.Optional;

public interface BeneficiaireIService {
    BeneficiaireDto save(BeneficiaireDto dto);
    List<BeneficiaireDto> findAll();
    Optional<BeneficiaireDto> findById(String idBeneficiaire);
    void deleteById(String idBeneficiaire);

    BeneficiaireDto ajouterProjetDansBeneficiaire(String idBeneficiaire, String idProjet);

    BeneficiaireDto ajouterAvecProjet(BeneficiaireDto dto, String idProjet);

    Optional<BeneficiaireDto> findByIdAndProjetId(String idBeneficiaire, String idProjet);

    BeneficiaireDto updateBeneficiaireDansProjet(String idProjet, String idBeneficiaire, BeneficiaireDto dto);

    void retirerBeneficiaireDuProjet(String idProjet, String idBeneficiaire);

}
