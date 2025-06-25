package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.BeneficiaireDao;
import com.natha.dev.Dao.ProjetDao;
import com.natha.dev.Dto.BeneficiaireDto;
import com.natha.dev.IService.BeneficiaireIService;
import com.natha.dev.Model.Beneficiaire;
import com.natha.dev.Model.Projet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BeneficiaireImpl implements BeneficiaireIService {

    @Autowired
    private BeneficiaireDao dao;
    @Autowired
    private ProjetDao projetDao;

    @Override
    public BeneficiaireDto save(BeneficiaireDto dto) {
        Beneficiaire b = convertToEntity(dto);
        return convertToDto(dao.save(b));
    }

    @Override
    public List<BeneficiaireDto> findAll() {
        return dao.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public Optional<BeneficiaireDto> findById(String idBeneficiaire) {
        return dao.findById(idBeneficiaire).map(this::convertToDto);
    }

    @Override
    public void deleteById(String idBeneficiaire) {
        dao.deleteById(idBeneficiaire);
    }

    @Override
    public BeneficiaireDto ajouterProjetDansBeneficiaire(String idBeneficiaire, String idProjet) {
        Beneficiaire beneficiaire = dao.findById(idBeneficiaire)
                .orElseThrow(() -> new RuntimeException("Beneficiaire pa jwenn"));
        Projet projet = projetDao.findById(idProjet)
                .orElseThrow(() -> new RuntimeException("Projet pa jwenn"));

        beneficiaire.getProjets().add(projet);
        Beneficiaire updatedBeneficiaire = dao.save(beneficiaire);

        return BeneficiaireDto.fromEntity(updatedBeneficiaire);
    }


    @Override
    public BeneficiaireDto ajouterAvecProjet(BeneficiaireDto dto, String idProjet) {
        Projet projet = projetDao.findById(idProjet)
                .orElseThrow(() -> new RuntimeException("Projet pa jwenn"));

        Beneficiaire beneficiaire = convertToEntity(dto);
        beneficiaire.getProjets().add(projet);  // ajoute projet lan

        Beneficiaire saved = dao.save(beneficiaire);
        return BeneficiaireDto.fromEntity(saved);
    }

    //Get beneficiaire by id with project
    @Override
    public Optional<BeneficiaireDto> findByIdAndProjetId(String idBeneficiaire, String idProjet) {
        // Jwenn pwojè a
        Projet projet = projetDao.findById(idProjet)
                .orElseThrow(() -> new RuntimeException("Projet pa jwenn"));

        // Chèche Beneficiaire a nan lis beneficiaires pwojè a
        return projet.getBeneficiaires().stream()
                .filter(b -> b.getIdBeneficiaire().equals(idBeneficiaire))
                .findFirst()
                .map(b -> new BeneficiaireDto(
                        b.getIdBeneficiaire(),
                        b.getNom(),
                        b.getPrenom(),
                        b.getSexe(),
                        b.getDateNaissance(),
                        b.getDomaineDeFormation(),
                        b.getTypeIdentification(),
                        b.getIdentification(),
                        b.getLienNaissance(),
                        b.getQualification(),
                        b.getTelephoneContact(),
                        b.getTelephonePaiement(),
                        b.getOperateurPaiement()
                ));
    }

    //Update Beneficiaire with Project
    @Override
    public BeneficiaireDto updateBeneficiaireDansProjet(String idProjet, String idBeneficiaire, BeneficiaireDto dto) {
        Projet projet = projetDao.findById(idProjet)
                .orElseThrow(() -> new RuntimeException("Projet pa jwenn"));

        Beneficiaire beneficiaire = dao.findById(idBeneficiaire)
                .orElseThrow(() -> new RuntimeException("Beneficiaire pa jwenn"));

        if (!beneficiaire.getProjets().contains(projet)) {
            throw new RuntimeException("Beneficiaire sa a pa fè pati de projet sa a.");
        }

        // Mete ajou chan yo
        beneficiaire.setNom(dto.getNom());
        beneficiaire.setPrenom(dto.getPrenom());
        beneficiaire.setSexe(dto.getSexe());
        beneficiaire.setDateNaissance(dto.getDateNaissance());
        beneficiaire.setDomaineDeFormation(dto.getDomaineDeFormation());
        beneficiaire.setTypeIdentification(dto.getTypeIdentification());
        beneficiaire.setIdentification(dto.getIdentification());
        beneficiaire.setLienNaissance(dto.getLienNaissance());
        beneficiaire.setQualification(dto.getQualification());
        beneficiaire.setTelephoneContact(dto.getTelephoneContact());
        beneficiaire.setTelephonePaiement(dto.getTelephonePaiement());
        beneficiaire.setOperateurPaiement(dto.getOperateurPaiement());

        Beneficiaire updated = dao.save(beneficiaire);
        return BeneficiaireDto.fromEntity(updated);
    }




    private BeneficiaireDto convertToDto(Beneficiaire b) {
        return new BeneficiaireDto(
                b.getIdBeneficiaire(), b.getNom(), b.getPrenom(), b.getSexe(), b.getDateNaissance(),
                b.getDomaineDeFormation(), b.getTypeIdentification(), b.getIdentification(),
                b.getLienNaissance(), b.getQualification(), b.getTelephoneContact(),
                b.getTelephonePaiement(), b.getOperateurPaiement()
        );
    }

    public static Beneficiaire convertToEntity(BeneficiaireDto d) {
        if (d == null) {
            return null;
        }
        Beneficiaire b = new Beneficiaire();
        b.setIdBeneficiaire(d.getIdBeneficiaire());
        b.setNom(d.getNom());
        b.setPrenom(d.getPrenom());
        b.setSexe(d.getSexe());
        b.setDateNaissance(d.getDateNaissance());
        b.setDomaineDeFormation(d.getDomaineDeFormation());
        b.setTypeIdentification(d.getTypeIdentification());
        b.setIdentification(d.getIdentification());
        b.setLienNaissance(d.getLienNaissance());
        b.setQualification(d.getQualification());
        b.setTelephoneContact(d.getTelephoneContact());
        b.setTelephonePaiement(d.getTelephonePaiement());
        b.setOperateurPaiement(d.getOperateurPaiement());
        return b;
    }

    @Override
    public void retirerBeneficiaireDuProjet(String idProjet, String idBeneficiaire) {
        Projet projet = projetDao.findById(idProjet)
                .orElseThrow(() -> new RuntimeException("Projet pa jwenn"));

        Beneficiaire beneficiaire = dao.findById(idBeneficiaire)
                .orElseThrow(() -> new RuntimeException("Beneficiaire pa jwenn"));

        if (!beneficiaire.getProjets().contains(projet)) {
            throw new RuntimeException("Beneficiaire pa fè pati de projet sa a.");
        }

        beneficiaire.getProjets().remove(projet);
        dao.save(beneficiaire); // persist change la
    }

}

