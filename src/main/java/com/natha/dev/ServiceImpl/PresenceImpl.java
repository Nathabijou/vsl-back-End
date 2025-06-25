package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.PresenceDao;
import com.natha.dev.Dao.ProjetBeneficiaireDao;
import com.natha.dev.Dto.PresenceDto;
import com.natha.dev.IService.PresenceIService;
import com.natha.dev.Model.Presence;
import com.natha.dev.Model.ProjetBeneficiaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PresenceImpl implements PresenceIService {

    @Autowired
    private PresenceDao presenceDao;

    @Autowired
    private ProjetBeneficiaireDao projetBeneficiaireDao;

    @Override
    public PresenceDto ajouterPresence(String projetId, String beneficiaireId, PresenceDto dto) {
        ProjetBeneficiaire pb = projetBeneficiaireDao
                .findByProjetIdProjetAndBeneficiaireIdBeneficiaire(projetId, beneficiaireId)
                .orElseThrow(() -> new RuntimeException("Relation Projet-Bénéficiaire pa jwenn"));

        Presence presence = new Presence();
        presence.setIdPresence(UUID.randomUUID().toString());
        presence.setDatePresence(dto.getDatePresence());
        presence.setHeurEntrer(dto.getHeurEntrer());
        presence.setHeureSorti(dto.getHeureSorti());
        presence.setCreateBy(dto.getCreateBy());
        presence.setProjetBeneficiaire(pb);

        return convertToDto(presenceDao.save(presence));
    }

    @Override
    public List<PresenceDto> getPresencesByProjetBeneficiaire(String projetId, String beneficiaireId) {
        ProjetBeneficiaire pb = projetBeneficiaireDao
                .findByProjetIdProjetAndBeneficiaireIdBeneficiaire(projetId, beneficiaireId)
                .orElseThrow(() -> new RuntimeException("Relasyon pa egziste"));

        return presenceDao.findByProjetBeneficiaireIdProjetBeneficiaire(pb.getIdProjetBeneficiaire())
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PresenceDto modifierPresence(String idPresence, PresenceDto dto) {
        Presence presence = presenceDao.findById(idPresence)
                .orElseThrow(() -> new RuntimeException("Presence pa jwenn"));

        presence.setDatePresence(dto.getDatePresence());
        presence.setHeurEntrer(dto.getHeurEntrer());
        presence.setHeureSorti(dto.getHeureSorti());
        presence.setCreateBy(dto.getCreateBy());

        return convertToDto(presenceDao.save(presence));
    }

    @Override
    public void supprimerPresence(String idPresence) {
        presenceDao.deleteById(idPresence);
    }

    private PresenceDto convertToDto(Presence p) {
        return new PresenceDto(
                p.getIdPresence(), p.getDatePresence(),
                p.getHeurEntrer(), p.getHeureSorti(),
                p.getCreateBy()
        );
    }
}

