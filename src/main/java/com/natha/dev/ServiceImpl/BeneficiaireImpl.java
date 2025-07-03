package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.*;
import com.natha.dev.Dto.BeneficiaireDto;
import com.natha.dev.IService.BeneficiaireIService;
import com.natha.dev.IService.Model.*;
import com.natha.dev.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BeneficiaireImpl implements BeneficiaireIService {

    @Autowired
    private BeneficiaireDao dao;
    @Autowired
    private ProjetDao projetDao;
    @Autowired
    private ProjetBeneficiaireDao projetBeneficiaireDao;
    @Autowired
    private ProjetBeneficiaireFormationDao projetBeneficiaireFormationDao;
    @Autowired
    private FormationDao formationDao;


    @Override
    public Optional<BeneficiaireDto> findById(String beneficiaireId) {
        // Itilize dao a pou jwenn Beneficiaire nan bazdone
        Beneficiaire beneficiaire = dao.findById(beneficiaireId)
                .orElseThrow(() -> new RuntimeException("Beneficiaire not found with ID: " + beneficiaireId));
        return Optional.of(convertToDto(beneficiaire)); // Retounen DTO Beneficiaire a
    }



    @Override
    public void ajouterBeneficiaireDansFormation(String idBeneficiaire, String idProjet, String idFormation) {
        // 1. Récupérer ProjetBeneficiaire
        ProjetBeneficiaire projetBeneficiaire = projetBeneficiaireDao
                .findByProjetIdProjetAndBeneficiaireIdBeneficiaire(idProjet, idBeneficiaire)
                .orElseThrow(() -> new RuntimeException("Bénéficiaire pa nan pwojè sa!"));

        // 2. Récupérer Formation
        Formation formation = formationDao.findById(idFormation)
                .orElseThrow(() -> new RuntimeException("Formation pa jwenn"));

        // 3. Vérifier si deja ajoute
        boolean dejaAjoute = projetBeneficiaireFormationDao
                .existsByProjetBeneficiaireAndFormation(projetBeneficiaire, formation);

        if (dejaAjoute) {
            throw new RuntimeException("Bénéficiaire deja nan formation sa a.");
        }

        // 4. Créer nouvel enregistrement ProjetBeneficiaireFormation
        ProjetBeneficiaireFormation relation = new ProjetBeneficiaireFormation();
        relation.setProjetBeneficiaire(projetBeneficiaire);
        relation.setFormation(formation);

        projetBeneficiaireFormationDao.save(relation);
    }


    @Override
    public Optional<BeneficiaireDto> findBeneficiaireInProjet(String projetId, String beneficiaireId) {
        Optional<ProjetBeneficiaire> relation = projetBeneficiaireDao
                .findByProjetIdProjetAndBeneficiaireIdBeneficiaire(projetId, beneficiaireId);

        return relation.map(pb -> convertToDto(pb.getBeneficiaire()));
    }

    public void transfererBeneficiaireDansProjet(String beneficiaireId, String ancienProjetId, String nouveauProjetId) {
        // Chèche relasyon benefisyè nan ansyen pwojè
        ProjetBeneficiaire relation = projetBeneficiaireDao
                .findByProjetIdProjetAndBeneficiaireIdBeneficiaire(ancienProjetId, beneficiaireId)
                .orElseThrow(() -> new RuntimeException("Beneficiaire pa jwenn nan pwojè aktyèl la"));

        // Rekipere nouvo pwojè kote n ap transfere benefisyè a
        Projet nouveauProjet = projetDao.findById(nouveauProjetId)
                .orElseThrow(() -> new RuntimeException("Nouvo pwojè pa egziste"));

        // Mete nouvo pwojè a nan relasyon an (chanje pwojè)
        relation.setProjet(nouveauProjet);

        // Sove modifikasyon an nan baz done
        projetBeneficiaireDao.save(relation);
    }


    public void deleteBeneficiaireFromProjet(String beneficiaireId, String projetId) {
        ProjetBeneficiaire relation = projetBeneficiaireDao
                .findByProjetIdProjetAndBeneficiaireIdBeneficiaire(projetId, beneficiaireId)
                .orElseThrow(() -> new RuntimeException("Relasyon Beneficiaire-Pwojè pa jwenn"));

        projetBeneficiaireDao.delete(relation);
    }

    @Override
    public BeneficiaireDto creerBeneficiaireEtAssocierAuProjet(BeneficiaireDto dto, String idProjet) {
        // 1. Konvèti DTO an entity
        Beneficiaire beneficiaire = convertToEntity(dto);

        // 2. Sove beneficyè a
        Beneficiaire beneficiaireCree = dao.save(beneficiaire);

        // 3. Rekipere projet la
        Projet projet = projetDao.findById(idProjet)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé avec id: " + idProjet));

        // 4. Kreye lyen nan antite ProjetBeneficiaire
        ProjetBeneficiaire pb = new ProjetBeneficiaire();
        pb.setProjet(projet);
        pb.setBeneficiaire(beneficiaireCree);

        // 5. Sove lyen nan baz done
        projetBeneficiaireDao.save(pb);

        // 6. Retounen DTO beneficyè a
        return convertToDto(beneficiaireCree);
    }

    @Override
    public BeneficiaireDto updateBeneficiaireDansProjet(String projetId, String beneficiaireId, BeneficiaireDto dto) {
        // 1. Verifye relasyon an egziste
        ProjetBeneficiaire relation = (ProjetBeneficiaire) projetBeneficiaireDao
                .findByProjetIdProjetAndBeneficiaireIdBeneficiaire(projetId, beneficiaireId)
                .orElseThrow(() -> new RuntimeException("Relasyon Projet-Beneficiaire pa egziste."));

        // 2. Pran beneficyè a
        Beneficiaire beneficiaire = relation.getBeneficiaire();

        // 3. Mete ajou tout champs ki soti nan DTO a
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

        // 4. Sove li
        dao.save(beneficiaire);

        // 5. Retounen BeneficiaireDto a mete ajou
        return convertToDto(beneficiaire);
    }



    @Override
    public BeneficiaireDto save(BeneficiaireDto dto) {
        Beneficiaire b = convertToEntity(dto);
        return convertToDto(dao.save(b));
    }


    public static BeneficiaireDto convertToDto(Beneficiaire b) {
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


}

