package com.natha.dev.IService;


import com.natha.dev.Dto.BeneficiaireDto;
import com.natha.dev.Dto.ProjetDto;
import com.natha.dev.Model.Projet;

import java.util.List;
import java.util.Optional;

public interface ProjetIService {
    ProjetDto save(ProjetDto dto);
    List<ProjetDto> findAll();
    Optional<ProjetDto> findById(String idProjet);
    void deleteById(String idProjet);
    public ProjetDto createProjetInQuartierAndComposante(ProjetDto projetDto, String quartierId, Long composanteId);

    List<ProjetDto> findByComposante(Long composanteId);
    List<ProjetDto> findByComposanteAndQuartier(Long composanteId, String quartierId);

    ProjetDto updateInComposante(Long composanteId, String idProjet, ProjetDto dto);
    ProjetDto updateInComposanteAndQuartier(Long composanteId, String quartierId, String idProjet, ProjetDto dto);

    void setProjetActiveStatus(String idProjet, boolean active);

    List<BeneficiaireDto> getBeneficiairesByProjetId(String idProjet);




}
