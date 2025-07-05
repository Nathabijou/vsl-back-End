package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.BeneficiaireDao;
import com.natha.dev.Dao.PayrollDao;
import com.natha.dev.Dao.ProjetBeneficiaireDao;
import com.natha.dev.Dto.KpiResponse;
import com.natha.dev.IService.DashboardIService;
import com.natha.dev.Model.DashboardFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DashboardImpl implements DashboardIService {

    @Autowired
    private BeneficiaireDao beneficiaireDao;

    @Autowired
    private ProjetBeneficiaireDao projetBeneficiaireDao;

    @Autowired
    private PayrollDao payrollDao;

    @Override
    public KpiResponse getKpiData(DashboardFilter filter, String username) {
        Long composanteId = filter.getComposanteId();
        Long zoneId = filter.getZoneId();
        Long departementId = filter.getDepartementId();
        Long communeId = filter.getCommuneId();
        Long sectionId = filter.getSectionId();
        Long quartierId = filter.getQuartierId();
        Long arrondissementId = filter.getArrondissementId();

        String projetId = filter.getProjetId();
        LocalDate dateDebut = LocalDate.of(2019, 1, 1); // oswa pi bonè si ou gen done pi ansyen
        LocalDate dateFin = LocalDate.now(); // jiska jodi a


        long totalBeneficiaires = projetBeneficiaireDao.countBeneficiairesByFilters(
                composanteId, zoneId, departementId,arrondissementId, communeId, sectionId, quartierId, projetId);

        long totalFemmes = projetBeneficiaireDao.countBySexe(
                composanteId, zoneId, departementId, arrondissementId,communeId, sectionId, quartierId, projetId, "F");

        long totalHommes = projetBeneficiaireDao.countBySexe(
                composanteId, zoneId, departementId,arrondissementId, communeId, sectionId, quartierId, projetId, "M");


        long totalQualifier = projetBeneficiaireDao.countByQualification(
                composanteId, zoneId, departementId,arrondissementId, communeId, sectionId, quartierId, projetId, "Q");

        long totalNonQualifier = projetBeneficiaireDao.countByQualification(
                composanteId, zoneId, departementId,arrondissementId, communeId, sectionId, quartierId, projetId, "NQ");

        long totalFilleQualifier = projetBeneficiaireDao.countBySexeAndQualification(
                "F", "Q", composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId);

        long totalFilleNonQualifier = projetBeneficiaireDao.countBySexeAndQualification(
                "F", "NQ", composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId);

        long totalGarconQualifier = projetBeneficiaireDao.countBySexeAndQualification(
                "M", "Q", composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId);

        long totalGarconNonQualifier = projetBeneficiaireDao.countBySexeAndQualification(
                "M", "NQ", composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId);



        double totalMonCash = payrollDao.sumByMethodePaiement(
                "MonCash", composanteId, zoneId, departementId,arrondissementId, communeId, sectionId, quartierId, projetId);

        double totalLajanCash = payrollDao.sumByMethodePaiement(
                "LajanCash", composanteId, zoneId, departementId,arrondissementId, communeId, sectionId, quartierId, projetId);

        int totalFilleMoncash = (int) payrollDao.countBySexeAndMethodePaiement(
                composanteId, zoneId, departementId, arrondissementId,communeId, sectionId, quartierId, projetId,
                "F", "MonCash",
                dateDebut, dateFin
        );

        int totalFilleCash = (int) payrollDao.countBySexeAndMethodePaiement(
                composanteId, zoneId, departementId,arrondissementId, communeId, sectionId, quartierId, projetId,
                "F", "LajanCash", dateDebut, dateFin
        );

        int totalGarconMoncash = (int) payrollDao.countBySexeAndMethodePaiement(
                composanteId, zoneId, departementId,arrondissementId, communeId, sectionId, quartierId, projetId,
                "M", "MonCash", dateDebut, dateFin
        );

        int totalGarconCash = (int) payrollDao.countBySexeAndMethodePaiement(
                composanteId, zoneId, departementId,arrondissementId, communeId, sectionId, quartierId, projetId,
                "M", "LajanCash", dateDebut, dateFin
        );

        double totalFilleQualifierMoncash = payrollDao.sumBySexeQualificationAndMethodePaiement(
                composanteId, zoneId, departementId, arrondissementId,
                communeId, sectionId, quartierId, projetId,
                "F", "Q", "MonCash"
        );

        double totalFilleQualifierLajanCash = payrollDao.sumBySexeQualificationAndMethodePaiement(
                composanteId, zoneId, departementId, arrondissementId,
                communeId, sectionId, quartierId, projetId,
                "F", "Q", "LajanCash"
        );

        double totalFilleNonQualifierMoncash = payrollDao.sumBySexeQualificationAndMethodePaiement(
                composanteId, zoneId, departementId, arrondissementId,
                communeId, sectionId, quartierId, projetId,
                "F", "NQ", "MonCash"
        );

        double totalFilleNonQualifierLajanCash = payrollDao.sumBySexeQualificationAndMethodePaiement(
                composanteId, zoneId, departementId, arrondissementId,
                communeId, sectionId, quartierId, projetId,
                "F", "NQ", "LajanCash"
        );

        double totalGarconQualifierMoncash = payrollDao.sumBySexeQualificationAndMethodePaiement(
                composanteId, zoneId, departementId, arrondissementId,
                communeId, sectionId, quartierId, projetId,
                "M", "Q", "MonCash"
        );

        double totalGarconQualifierLajanCash = payrollDao.sumBySexeQualificationAndMethodePaiement(
                composanteId, zoneId, departementId, arrondissementId,
                communeId, sectionId, quartierId, projetId,
                "M", "Q", "LajanCash"
        );




        // **Ajoute sa yo tou ki manke nan vèsyon ou te bay la**

        double totalFilleMonCashMontant = payrollDao.sumBySexeAndMethodePaiement(
                composanteId, zoneId, departementId,arrondissementId, communeId, sectionId, quartierId, projetId,
                "F", "MonCash"
        );

        double totalFilleLajanCashMontant = payrollDao.sumBySexeAndMethodePaiement(
                composanteId, zoneId, departementId,arrondissementId, communeId, sectionId, quartierId, projetId,
                "F", "LajanCash"
        );

        double totalGarconMonCashMontant = payrollDao.sumBySexeAndMethodePaiement(
                composanteId, zoneId, departementId, arrondissementId,communeId, sectionId, quartierId, projetId,
                "M", "MonCash"
        );

        double totalGarconLajanCashMontant = payrollDao.sumBySexeAndMethodePaiement(
                composanteId, zoneId, departementId, arrondissementId,communeId, sectionId, quartierId, projetId,
                "M", "LajanCash"
        );




        return new KpiResponse(
                totalBeneficiaires,
                totalFemmes,
                totalHommes,
                totalQualifier,
                totalNonQualifier,
                totalMonCash,
                totalLajanCash,
                totalFilleQualifier,
                totalFilleNonQualifier,
                totalGarconQualifier,
                totalGarconNonQualifier,
                totalFilleMoncash,
                totalFilleCash,
                totalGarconMoncash,
                totalGarconCash,
                totalFilleQualifierMoncash,
                totalFilleQualifierLajanCash,
                totalFilleNonQualifierMoncash,
                totalFilleNonQualifierLajanCash,
                totalGarconQualifierMoncash,
                totalGarconQualifierLajanCash
        );
    }

}
