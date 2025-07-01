package com.natha.dev.Dao;

import com.natha.dev.Model.ProjetBeneficiaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProjetBeneficiaireDao extends JpaRepository<ProjetBeneficiaire, String> {
    List<ProjetBeneficiaire> findByProjetIdProjet(String projetId);

    Optional<ProjetBeneficiaire> findByProjetIdProjetAndBeneficiaireIdBeneficiaire(String projetId, String beneficiaireId);

    @Query("""
    SELECT COUNT(pb) FROM ProjetBeneficiaire pb
    WHERE pb.beneficiaire.sexe = :sexe
      AND (:composanteId IS NULL OR pb.projet.composante.id = :composanteId)
      AND (:zoneId IS NULL OR pb.projet.quartier.sectionCommunale.commune.departement.zone.id = :zoneId)
      AND (:departementId IS NULL OR pb.projet.quartier.sectionCommunale.commune.departement.id = :departementId)
      AND (:communeId IS NULL OR pb.projet.quartier.sectionCommunale.commune.id = :communeId)
      AND (:sectionId IS NULL OR pb.projet.quartier.sectionCommunale.id = :sectionId)
      AND (:quartierId IS NULL OR pb.projet.quartier.id = :quartierId)
      AND (:projetId IS NULL OR pb.projet.id = :projetId)
""")
    long countBySexe(
            @Param("composanteId") Long composanteId,
            @Param("zoneId") Long zoneId,
            @Param("departementId") Long departementId,
            @Param("communeId") Long communeId,
            @Param("sectionId") Long sectionId,
            @Param("quartierId") Long quartierId,
            @Param("projetId") String projetId,
            @Param("sexe") String sexe
    );

    // Dao
    @Query("""
    SELECT COUNT(pb) FROM ProjetBeneficiaire pb
    WHERE pb.beneficiaire.qualification = :qualification
      AND (:composanteId IS NULL OR pb.projet.composante.id = :composanteId)
      AND (:zoneId IS NULL OR pb.projet.quartier.sectionCommunale.commune.departement.zone.id = :zoneId)
      AND (:departementId IS NULL OR pb.projet.quartier.sectionCommunale.commune.departement.id = :departementId)
      AND (:communeId IS NULL OR pb.projet.quartier.sectionCommunale.commune.id = :communeId)
      AND (:sectionId IS NULL OR pb.projet.quartier.sectionCommunale.id = :sectionId)
      AND (:quartierId IS NULL OR pb.projet.quartier.id = :quartierId)
      AND (:projetId IS NULL OR pb.projet.id = :projetId)
""")
    long countByQualification(
            @Param("composanteId") Long composanteId,
            @Param("zoneId") Long zoneId,
            @Param("departementId") Long departementId,
            @Param("communeId") Long communeId,
            @Param("sectionId") Long sectionId,
            @Param("quartierId") Long quartierId,
            @Param("projetId") String projetId,
            @Param("qualification") String qualification
    );


    @Query("""
    SELECT COUNT(pb) FROM ProjetBeneficiaire pb
    WHERE (:composanteId IS NULL OR pb.projet.composante.id = :composanteId)
      AND (:zoneId IS NULL OR pb.projet.quartier.sectionCommunale.commune.departement.zone.id = :zoneId)
      AND (:departementId IS NULL OR pb.projet.quartier.sectionCommunale.commune.departement.id = :departementId)
      AND (:communeId IS NULL OR pb.projet.quartier.sectionCommunale.commune.id = :communeId)
      AND (:sectionId IS NULL OR pb.projet.quartier.sectionCommunale.id = :sectionId)
      AND (:quartierId IS NULL OR pb.projet.quartier.id = :quartierId)
      AND (:projetId IS NULL OR pb.projet.id = :projetId)
""")
    long countBeneficiairesByFilters(
            @Param("composanteId") Long composanteId,
            @Param("zoneId") Long zoneId,
            @Param("departementId") Long departementId,
            @Param("communeId") Long communeId,
            @Param("sectionId") Long sectionId,
            @Param("quartierId") Long quartierId,
            @Param("projetId") String projetId
    );

    // Dao

    @Query("SELECT COUNT(pb) FROM ProjetBeneficiaire pb " +
            "WHERE (:sexe IS NULL OR pb.beneficiaire.sexe = :sexe) " +
            "AND (:qualification IS NULL OR pb.beneficiaire.qualification = :qualification) " +
            "AND (:composanteId IS NULL OR pb.projet.composante.id = :composanteId) " +
            "AND (:zoneId IS NULL OR pb.projet.quartier.sectionCommunale.commune.departement.zone.id = :zoneId) " +
            "AND (:departementId IS NULL OR pb.projet.quartier.sectionCommunale.commune.departement.id = :departementId) " +
            "AND (:communeId IS NULL OR pb.projet.quartier.sectionCommunale.commune.id = :communeId) " +
            "AND (:sectionId IS NULL OR pb.projet.quartier.sectionCommunale.id = :sectionId) " +
            "AND (:quartierId IS NULL OR pb.projet.quartier.id = :quartierId) " +
            "AND (:projetId IS NULL OR pb.projet.id = :projetId)")
    int countBySexeAndQualification(
            @Param("composanteId") Long composanteId,
            @Param("zoneId") Long zoneId,
            @Param("departementId") Long departementId,
            @Param("communeId") Long communeId,
            @Param("sectionId") Long sectionId,
            @Param("quartierId") Long quartierId,
            @Param("projetId") String projetId,
            @Param("sexe") String sexe,
            @Param("qualification") String qualification
    );




    @Query("""
    SELECT SUM(p.montantPayer) FROM Payroll p
    JOIN p.projetBeneficiaire pb
    JOIN pb.beneficiaire b
    WHERE (:sexe IS NULL OR b.sexe = :sexe)
      AND (:qualification IS NULL OR b.qualification = :qualification)
      AND (:methodePaiement IS NULL OR p.methodePaiement = :methodePaiement)
      AND p.debutPeriode >= :startDate
      AND p.finPeriode <= :endDate
""")
    Double sumMontantPayeAvecFiltres(
            @Param("sexe") String sexe,
            @Param("qualification") String qualification,
            @Param("methodePaiement") String methodePaiement,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );





}
