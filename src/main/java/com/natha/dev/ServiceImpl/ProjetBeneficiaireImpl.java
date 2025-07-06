package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.BeneficiaireDao;
import com.natha.dev.Dao.ProjetBeneficiaireDao;
import com.natha.dev.Dao.ProjetDao;
import com.natha.dev.Dto.ProjetBeneficiaireDto;
import com.natha.dev.IService.ProjetBeneficiaireIService;
import com.natha.dev.Model.Beneficiaire;
import com.natha.dev.Model.Projet;
import com.natha.dev.Model.ProjetBeneficiaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjetBeneficiaireImpl implements ProjetBeneficiaireIService {

    @Autowired
    private ProjetDao projetDao;

    @Autowired
    private BeneficiaireDao beneficiaireDao;

    @Autowired
    private ProjetBeneficiaireDao projetBeneficiaireDao;


    @Override
    public ProjetBeneficiaireDto save(ProjetBeneficiaireDto dto) {
        return null;
    }

    @Override
    public List<ProjetBeneficiaireDto> findAll() {
        return null;
    }

    @Override
    public Optional<ProjetBeneficiaireDto> findById(String idProjetBeneficiaire) {
        return Optional.empty();
    }

    @Override
    public void deleteById(String idProjetBeneficiaire) {
        projetBeneficiaireDao.deleteById(idProjetBeneficiaire);
    }

    @Override
    public ProjetBeneficiaireDto creerRelationProjetBeneficiaire(String projetId, String beneficiaireId) {
        Projet projet = projetDao.findById(projetId)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé"));

        Beneficiaire beneficiaire = beneficiaireDao.findById(beneficiaireId)
                .orElseThrow(() -> new RuntimeException("Beneficiaire non trouvé"));

        ProjetBeneficiaire pb = new ProjetBeneficiaire();
        pb.setProjet(projet);
        pb.setBeneficiaire(beneficiaire);

        ProjetBeneficiaire saved = projetBeneficiaireDao.save(pb);

        return convertToDto(saved);
    }
    private ProjetBeneficiaireDto convertToDto(ProjetBeneficiaire pb) {
        ProjetBeneficiaireDto dto = new ProjetBeneficiaireDto();
        dto.setIdProjetBeneficiaire(pb.getIdProjetBeneficiaire());
        dto.setProjetId(pb.getProjet().getIdProjet());
        dto.setBeneficiaireId(pb.getBeneficiaire().getIdBeneficiaire());
        // Ou kapab ajoute plis detay si ou vle
        return dto;
    }

    @Override
    public List<ProjetBeneficiaireDto> findByProjetId(String projetId) {
        List<ProjetBeneficiaire> relations = projetBeneficiaireDao.findByProjetIdProjet(projetId);
        return relations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
