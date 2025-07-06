package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.PayrollDao;
import com.natha.dev.Dto.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Service
public class PayrollMatrixServiceImpl {

    @Autowired
    private PayrollDao payrollDao;

    public ProjetPayrollMatrixDTO generatePayrollMatrix(String projetId) {
        List<PayrollFlatData> rawData = payrollDao.findPayrollDataByProjet(projetId);

        ProjetPayrollMatrixDTO dto = new ProjetPayrollMatrixDTO();
        Map<PeriodeDTO, Integer> periodeIndex = new LinkedHashMap<>();
        Map<String, BeneficiairePayrollMatrixDTO> benefMap = new LinkedHashMap<>();

        // === 1. Kreye peryòd yo ak index yo ===
        int idx = 0;
        for (PayrollFlatData row : rawData) {
            PeriodeDTO periode = new PeriodeDTO(row.getDateDebut(), row.getDateFin());
            if (!periodeIndex.containsKey(periode)) {
                periodeIndex.put(periode, idx++);
            }
        }

        int totalPeriodes = periodeIndex.size();

        // === 2. Mete chak benefisyè ak done payroll yo ===
        for (PayrollFlatData row : rawData) {
            String key = row.getNom() + "-" + row.getPrenom();

            // Chak benefisyè dwe gen lis payroll ak longè egal ak kantite peryòd
            BeneficiairePayrollMatrixDTO benef = benefMap.computeIfAbsent(key, k -> {
                BeneficiairePayrollMatrixDTO b = new BeneficiairePayrollMatrixDTO();
                b.setNom(row.getNom());
                b.setPrenom(row.getPrenom());
                b.setSexe(row.getSexe());
                b.setQualification(row.getQualification());
                b.setIdentification(row.getIdentification());
                b.setTelephonePaiement(row.getTelephonePaiement());
                b.setPayrolls(new ArrayList<>(Collections.nCopies(totalPeriodes, null)));
                return b;
            });

            PeriodeDTO periode = new PeriodeDTO(row.getDateDebut(), row.getDateFin());
            int pos = periodeIndex.get(periode);

            PayrollColDTO col = new PayrollColDTO();
            col.setNbJours(row.getNbJours());
            col.setMontantParJour(row.getMontantParJour());
            col.setTotal(row.getNbJours() * row.getMontantParJour());

            benef.getPayrolls().set(pos, col);
        }

        dto.setNomProjet(rawData.isEmpty() ? "" : rawData.get(0).getNomProjet());
        dto.setPeriodes(new ArrayList<>(periodeIndex.keySet()));
        dto.setBeneficiaires(new ArrayList<>(benefMap.values()));

        return dto;
    }
}
