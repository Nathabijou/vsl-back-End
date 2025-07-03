package com.natha.dev.Dao;

import com.natha.dev.Model.ProjetBeneficiaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProjetBeneficiaireDao extends JpaRepository<ProjetBeneficiaire, String> {
    List<ProjetBeneficiaire> findByProjetIdProjet(String projetId);

    Optional<ProjetBeneficiaire> findByProjetIdProjetAndBeneficiaireIdBeneficiaire(String projetId, String beneficiaireId);

    @Query("""
SELECT COUNT(pb) FROM ProjetBeneficiaire pb
JOIN pb.projet.quartier.sectionCommunale.commune c
JOIN c.arrondissement a
JOIN a.departement d
JOIN d.zones z
WHERE pb.beneficiaire.sexe = :sexe
  AND (:composanteId IS NULL OR pb.projet.composante.id = :composanteId)
  AND (:zoneId IS NULL OR z.id = :zoneId)
  AND (:departementId IS NULL OR d.id = :departementId)
  AND (:arrondissementId IS NULL OR a.id = :arrondissementId)
  AND (:communeId IS NULL OR c.id = :communeId)
  AND (:sectionId IS NULL OR pb.projet.quartier.sectionCommunale.id = :sectionId)
  AND (:quartierId IS NULL OR pb.projet.quartier.id = :quartierId)
  AND (:projetId IS NULL OR pb.projet.id = :projetId)
""")
    long countBySexe(
            @Param("composanteId") Long composanteId,
            @Param("zoneId") Long zoneId,
            @Param("departementId") Long departementId,
            @Param("arrondissementId") Long arrondissementId,
            @Param("communeId") Long communeId,
            @Param("sectionId") Long sectionId,
            @Param("quartierId") Long quartierId,
            @Param("projetId") String projetId,
            @Param("sexe") String sexe
    );



    // Dao


        @Query("""
        SELECT COUNT(pb) FROM ProjetBeneficiaire pb
        JOIN pb.projet.quartier.sectionCommunale.commune c
        JOIN c.arrondissement a
        JOIN a.departement d
        JOIN d.zones z
        WHERE pb.beneficiaire.qualification = :qualification
          AND (:composanteId IS NULL OR pb.projet.composante.id = :composanteId)
          AND (:zoneId IS NULL OR z.id = :zoneId)
          AND (:departementId IS NULL OR d.id = :departementId)
          AND (:arrondissementId IS NULL OR a.id = :arrondissementId)
          AND (:communeId IS NULL OR c.id = :communeId)
          AND (:sectionId IS NULL OR pb.projet.quartier.sectionCommunale.id = :sectionId)
          AND (:quartierId IS NULL OR pb.projet.quartier.id = :quartierId)
          AND (:projetId IS NULL OR pb.projet.id = :projetId)
    """)
        long countByQualification(
                @Param("composanteId") Long composanteId,
                @Param("zoneId") Long zoneId,
                @Param("departementId") Long departementId,
                @Param("arrondissementId") Long arrondissementId,
                @Param("communeId") Long communeId,
                @Param("sectionId") Long sectionId,
                @Param("quartierId") Long quartierId,
                @Param("projetId") String projetId,
                @Param("qualification") String qualification
        );





    @Query("""
    SELECT COUNT(pb) FROM ProjetBeneficiaire pb
    JOIN pb.projet.quartier.sectionCommunale.commune c
    JOIN c.arrondissement a
    JOIN a.departement d
    JOIN d.zones z
    WHERE (:composanteId IS NULL OR pb.projet.composante.id = :composanteId)
      AND (:zoneId IS NULL OR z.id = :zoneId)
      AND (:departementId IS NULL OR d.id = :departementId)
      AND (:arrondissementId IS NULL OR a.id = :arrondissementId)
      AND (:communeId IS NULL OR c.id = :communeId)
      AND (:sectionId IS NULL OR pb.projet.quartier.sectionCommunale.id = :sectionId)
      AND (:quartierId IS NULL OR pb.projet.quartier.id = :quartierId)
      AND (:projetId IS NULL OR pb.projet.id = :projetId)
""")
    long countBeneficiairesByFilters(
            @Param("composanteId") Long composanteId,
            @Param("zoneId") Long zoneId,
            @Param("departementId") Long departementId,
            @Param("arrondissementId") Long arrondissementId,
            @Param("communeId") Long communeId,
            @Param("sectionId") Long sectionId,
            @Param("quartierId") Long quartierId,
            @Param("projetId") String projetId
    );




    // Dao

    @Query("SELECT COUNT(DISTINCT pb) " +
            "FROM ProjetBeneficiaire pb " +
            "JOIN pb.projet.quartier q " +
            "JOIN q.sectionCommunale sc " +
            "JOIN sc.commune c " +
            "JOIN c.arrondissement a " +
            "JOIN a.departement d " +
            "JOIN d.zones z " +
            "JOIN pb.projet.composante comp " +
            "WHERE (:sexe IS NULL OR pb.beneficiaire.sexe = :sexe) " +
            "AND (:qualification IS NULL OR pb.beneficiaire.qualification = :qualification) " +
            "AND (:composanteId IS NULL OR comp.id = :composanteId) " +
            "AND (:zoneId IS NULL OR z.id = :zoneId) " +
            "AND (:departementId IS NULL OR d.id = :departementId) " +
            "AND (:arrondissementId IS NULL OR a.id = :arrondissementId) " +
            "AND (:communeId IS NULL OR c.id = :communeId) " +
            "AND (:sectionId IS NULL OR sc.id = :sectionId) " +
            "AND (:quartierId IS NULL OR q.id = :quartierId) " +
            "AND (:projetId IS NULL OR pb.projet.id = :projetId)")
    int countBySexeAndQualification(
            @Param("sexe") String sexe,
            @Param("qualification") String qualification,
            @Param("composanteId") Long composanteId,
            @Param("zoneId") Long zoneId,
            @Param("departementId") Long departementId,
            @Param("arrondissementId") Long arrondissementId,
            @Param("communeId") Long communeId,
            @Param("sectionId") Long sectionId,
            @Param("quartierId") Long quartierId,
            @Param("projetId") String projetId
    );









    @Query("SELECT COALESCE(SUM(p.montantPayer), 0) " +
            "FROM Payroll p " +
            "JOIN p.projetBeneficiaire pb " +
            "JOIN pb.beneficiaire b " +
            "WHERE b.sexe = :sexe " +
            "  AND p.methodePaiement = :methodePaiement " +
            "  AND (:composanteId IS NULL OR pb.projet.composante.id = :composanteId) " +
            "  AND (:zoneId IS NULL OR pb.projet.quartier.sectionCommunale.commune.arrondissement.departement.zone.id = :zoneId) " +
            "  AND (:departementId IS NULL OR pb.projet.quartier.sectionCommunale.commune.arrondissement.departement.id = :departementId) " +
            "  AND (:arrondissementId IS NULL OR pb.projet.quartier.sectionCommunale.commune.arrondissement.id = :arrondissementId) " +
            "  AND (:communeId IS NULL OR pb.projet.quartier.sectionCommunale.commune.id = :communeId) " +
            "  AND (:sectionId IS NULL OR pb.projet.quartier.sectionCommunale.id = :sectionId) " +
            "  AND (:quartierId IS NULL OR pb.projet.quartier.id = :quartierId) " +
            "  AND (:projetId IS NULL OR pb.projet.id = :projetId)")
    double sumMontantPayerByFilters(
            @Param("sexe") String sexe,
            @Param("methodePaiement") String methodePaiement,
            @Param("composanteId") Long composanteId,
            @Param("zoneId") Long zoneId,
            @Param("departementId") Long departementId,
            @Param("arrondissementId") Long arrondissementId,
            @Param("communeId") Long communeId,
            @Param("sectionId") Long sectionId,
            @Param("quartierId") Long quartierId,
            @Param("projetId") Long projetId);





}
