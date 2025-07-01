package com.natha.dev.Dao;

import com.natha.dev.Model.Payroll;
import com.natha.dev.Model.ProjetBeneficiaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PayrollDao extends JpaRepository<Payroll, String> {
    List<Payroll> findByProjetBeneficiaire(ProjetBeneficiaire projetBeneficiaire);
    Optional<Payroll> findById(String id);

    @Query("""
SELECT COALESCE(SUM(p.montantPayer), 0) FROM Payroll p
WHERE p.methodePaiement = :methode
  AND (:composanteId IS NULL OR p.projetBeneficiaire.projet.composante.id = :composanteId)
  AND (:zoneId IS NULL OR p.projetBeneficiaire.projet.quartier.sectionCommunale.commune.departement.zone.id = :zoneId)
  AND (:departementId IS NULL OR p.projetBeneficiaire.projet.quartier.sectionCommunale.commune.departement.id = :departementId)
  AND (:communeId IS NULL OR p.projetBeneficiaire.projet.quartier.sectionCommunale.commune.id = :communeId)
  AND (:sectionId IS NULL OR p.projetBeneficiaire.projet.quartier.sectionCommunale.id = :sectionId)
  AND (:quartierId IS NULL OR p.projetBeneficiaire.projet.quartier.id = :quartierId)
  AND (:projetId IS NULL OR p.projetBeneficiaire.projet.id = :projetId)
""")
    double sumByMethodePaiement(
            @Param("methode") String methode,
            @Param("composanteId") Long composanteId,
            @Param("zoneId") Long zoneId,
            @Param("departementId") Long departementId,
            @Param("communeId") Long communeId,
            @Param("sectionId") Long sectionId,
            @Param("quartierId") Long quartierId,
            @Param("projetId") String projetId
    );

    @Query("""
    SELECT COUNT(p) FROM Payroll p
    JOIN p.projetBeneficiaire pb
    JOIN pb.beneficiaire b
    WHERE (:sexe IS NULL OR b.sexe = :sexe)
      AND (:qualification IS NULL OR b.qualification = :qualification)
      AND (:methodePaiement IS NULL OR p.methodePaiement = :methodePaiement)
      AND (:composanteId IS NULL OR pb.projet.composante.id = :composanteId)
      AND (:zoneId IS NULL OR pb.projet.quartier.sectionCommunale.commune.departement.zone.id = :zoneId)
      AND (:departementId IS NULL OR pb.projet.quartier.sectionCommunale.commune.departement.id = :departementId)
      AND (:communeId IS NULL OR pb.projet.quartier.sectionCommunale.commune.id = :communeId)
      AND (:sectionId IS NULL OR pb.projet.quartier.sectionCommunale.id = :sectionId)
      AND (:quartierId IS NULL OR pb.projet.quartier.id = :quartierId)
      AND (:projetId IS NULL OR pb.projet.id = :projetId)
""")
    long countBySexeQualificationAndMethodePaiement(
            @Param("composanteId") Long composanteId,
            @Param("zoneId") Long zoneId,
            @Param("departementId") Long departementId,
            @Param("communeId") Long communeId,
            @Param("sectionId") Long sectionId,
            @Param("quartierId") Long quartierId,
            @Param("projetId") String projetId,
            @Param("sexe") String sexe,
            @Param("qualification") String qualification,
            @Param("methodePaiement") String methodePaiement
    );



}