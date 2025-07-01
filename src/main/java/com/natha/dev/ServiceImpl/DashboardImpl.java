// DashboardImpl.java

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
        Long sectionId = filter.getSectionCommunaleId();
        Long quartierId = filter.getQuartierId();
        String projetId = filter.getProjetId();
        LocalDate dateDebut = LocalDate.of(2025, 11, 1);
        LocalDate dateFin = LocalDate.of(2025, 12, 31);

        Double totalMontantFilleQMonCash = projetBeneficiaireDao.sumMontantPayeAvecFiltres(
                "F", "Q", "MonCash", dateDebut, dateFin
        );

        long totalBeneficiaires = projetBeneficiaireDao.countBeneficiairesByFilters(
                composanteId, zoneId, departementId, communeId, sectionId, quartierId, projetId);

        long totalFemmes = projetBeneficiaireDao.countBySexe(
                composanteId, zoneId, departementId, communeId, sectionId, quartierId, projetId, "F");

        long totalHommes = projetBeneficiaireDao.countBySexe(
                composanteId, zoneId, departementId, communeId, sectionId, quartierId, projetId, "M");

        long totalQualifier = projetBeneficiaireDao.countByQualification(
                composanteId, zoneId, departementId, communeId, sectionId, quartierId, projetId, "Q");

        long totalNonQualifier = projetBeneficiaireDao.countByQualification(
                composanteId, zoneId, departementId, communeId, sectionId, quartierId, projetId, "NQ");

        // Nouvo done yo (count pa sexe & qualification)
        int totalFilleQualifier = projetBeneficiaireDao.countBySexeAndQualification(
                composanteId, zoneId, departementId, communeId, sectionId, quartierId, projetId, "F", "Q");

        int totalFilleNonQualifier = projetBeneficiaireDao.countBySexeAndQualification(
                composanteId, zoneId, departementId, communeId, sectionId, quartierId, projetId, "F", "NQ");

        int totalGasonQualifier = projetBeneficiaireDao.countBySexeAndQualification(
                composanteId, zoneId, departementId, communeId, sectionId, quartierId, projetId, "M", "Q");

        int totalGasonNonQualifier = projetBeneficiaireDao.countBySexeAndQualification(
                composanteId, zoneId, departementId, communeId, sectionId, quartierId, projetId, "M", "NQ");

        double totalMonCash = payrollDao.sumByMethodePaiement(
                "MonCash", composanteId, zoneId, departementId, communeId, sectionId, quartierId, projetId);

        double totalLajanCash = payrollDao.sumByMethodePaiement(
                "LajanCash", composanteId, zoneId, departementId, communeId, sectionId, quartierId, projetId);

        // Egzanp itilize countBySexeQualificationAndMethodePaiement nan PayrollDao:
        int totalFilleQualifierMoncash = (int) payrollDao.countBySexeQualificationAndMethodePaiement(
                composanteId, zoneId, departementId, communeId, sectionId, quartierId, projetId,
                "F", "Q", "MonCash"
        );

        int totalFilleQualifierLajanCash = (int) payrollDao.countBySexeQualificationAndMethodePaiement(
                composanteId, zoneId, departementId, communeId, sectionId, quartierId, projetId,
                "F", "Q", "LajanCash"
        );

        // Ou ka kontinye ajoute lòt varyasyon menm jan si bezwen

        // Retounen tout sa nan KpiResponse (modifye KpiResponse si ou vle ajoute chan sa yo)
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
                totalGasonQualifier,
                totalGasonNonQualifier
                // Ajoute lòt done nan KpiResponse si ou modifye li
        );
    }
}
