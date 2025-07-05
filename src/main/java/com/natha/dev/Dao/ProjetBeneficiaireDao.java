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
LEFT JOIN c.arrondissement a
LEFT JOIN a.departement d
LEFT JOIN d.zones z
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
LEFT JOIN c.arrondissement a
LEFT JOIN a.departement d
LEFT JOIN d.zones z
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
    LEFT JOIN c.arrondissement a
    LEFT JOIN a.departement d
    LEFT JOIN d.zones z
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

    @Query(value = """
    SELECT COUNT(DISTINCT pb.id_projet_beneficiaire)
    FROM projet_beneficiaire pb
    JOIN beneficiaire b ON pb.beneficiaire_id = b.id_beneficiaire
    JOIN projet p ON pb.projet_id = p.id_projet
    JOIN quartier q ON p.quartier_id = q.id
    JOIN section_communale sc ON q.section_communale_id = sc.id
    JOIN commune c ON sc.commune_id = c.id
    JOIN arrondissement a ON c.arrondissement_id = a.id
    JOIN departement d ON a.departement_id = d.id
    JOIN zone_departement z ON d.id = z.departement_id
    JOIN composante comp ON p.composante_id = comp.id
    WHERE (:sexe IS NULL OR TRIM(b.sexe) = :sexe)
      AND (:qualification IS NULL OR TRIM(b.qualification) = :qualification)
      AND (:composanteId IS NULL OR comp.id = :composanteId)
      AND (:zoneId IS NULL OR z.zone_id = :zoneId)
      AND (:departementId IS NULL OR d.id = :departementId)
      AND (:arrondissementId IS NULL OR a.id = :arrondissementId)
      AND (:communeId IS NULL OR c.id = :communeId)
      AND (:sectionId IS NULL OR sc.id = :sectionId)
      AND (:quartierId IS NULL OR q.id = :quartierId)
      AND (:projetId IS NULL OR p.id_projet = :projetId)
""", nativeQuery = true)
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



    @Query(value = """
    SELECT 
      TRIM(b.sexe) AS sexe,
      TRIM(b.qualification) AS qualification,
      COUNT(DISTINCT pb.id_projet_beneficiaire) AS total
    FROM projet_beneficiaire pb
    JOIN beneficiaire b ON pb.beneficiaire_id = b.id_beneficiaire
    JOIN projet p ON pb.projet_id = p.id_projet
    JOIN quartier q ON p.quartier_id = q.id
    JOIN section_communale sc ON q.section_communale_id = sc.id
    JOIN commune c ON sc.commune_id = c.id
    JOIN arrondissement a ON c.arrondissement_id = a.id
    JOIN departement d ON a.departement_id = d.id
    JOIN zone_departement z ON d.id = z.departement_id
    JOIN composante comp ON p.composante_id = comp.id
    WHERE (:composanteId IS NULL OR comp.id = :composanteId)
      AND (:zoneId IS NULL OR z.zone_id = :zoneId)
      AND (:departementId IS NULL OR d.id = :departementId)
      AND (:arrondissementId IS NULL OR a.id = :arrondissementId)
      AND (:communeId IS NULL OR c.id = :communeId)
      AND (:sectionId IS NULL OR sc.id = :sectionId)
      AND (:quartierId IS NULL OR q.id = :quartierId)
      AND (:projetId IS NULL OR p.id_projet = :projetId)
    GROUP BY TRIM(b.sexe), TRIM(b.qualification)
    """, nativeQuery = true)
    <SexeQualificationStat>
    List<SexeQualificationStat> countBySexeAndQualificationGrouped(
            @Param("composanteId") Long composanteId,
            @Param("zoneId") Long zoneId,
            @Param("departementId") Long departementId,
            @Param("arrondissementId") Long arrondissementId,
            @Param("communeId") Long communeId,
            @Param("sectionId") Long sectionId,
            @Param("quartierId") Long quartierId,
            @Param("projetId") String projetId
    );


}
