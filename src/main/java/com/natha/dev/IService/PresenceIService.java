package com.natha.dev.IService;

import com.natha.dev.Dto.PresenceDto;

import java.util.List;

public interface PresenceIService {

    PresenceDto ajouterPresence(String projetId, String beneficiaireId, PresenceDto dto);

    List<PresenceDto> getPresencesByProjetBeneficiaire(String projetId, String beneficiaireId);

    PresenceDto modifierPresence(String idPresence, PresenceDto dto);

    void supprimerPresence(String idPresence);

    PresenceDto ajouterPresenceFormation(String projetId, String beneficiaireId, String formationId, PresenceDto dto);

    List<PresenceDto> getPresencesByProjetBeneficiaireFormation(String projetId, String beneficiaireId, String formationId);


}
