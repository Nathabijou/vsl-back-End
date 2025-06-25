package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.PayrollDao;
import com.natha.dev.Dao.ProjetBeneficiaireDao;
import com.natha.dev.Dto.PayrollDto;
import com.natha.dev.IService.PayrollIService;
import com.natha.dev.Model.Payroll;
import com.natha.dev.Model.ProjetBeneficiaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PayrollImpl implements PayrollIService {

    @Autowired
    private PayrollDao payrollDao;

    @Autowired
    private ProjetBeneficiaireDao projetBeneficiaireDao;

    @Override
    public PayrollDto createPayroll(String projetId, String beneficiaireId, PayrollDto dto) {
        ProjetBeneficiaire pb = projetBeneficiaireDao
                .findByProjetIdProjetAndBeneficiaireIdBeneficiaire(projetId, beneficiaireId)
                .orElseThrow(() -> new RuntimeException("Relasyon Beneficiaire-Projet pa jwenn"));

        Payroll payroll = new Payroll();
        payroll.setIdTransaction(dto.getIdTransaction());
        payroll.setMethodePaiement(dto.getMethodePaiement());
        payroll.setDebutPeriode(dto.getDebutPeriode());
        payroll.setFinPeriode(dto.getFinPeriode());
        payroll.setMontantPayer(dto.getMontantPayer());
        payroll.setStatut(dto.getStatut());
        payroll.setDatePaiement(dto.getDatePaiement());
        payroll.setProjetBeneficiaire(pb);

        Payroll saved = payrollDao.save(payroll);

        return convertToDto(saved);
    }

    @Override
    public List<PayrollDto> getPayrollsByProjetBeneficiaire(String projetId, String beneficiaireId) {
        ProjetBeneficiaire pb = projetBeneficiaireDao
                .findByProjetIdProjetAndBeneficiaireIdBeneficiaire(projetId, beneficiaireId)
                .orElseThrow(() -> new RuntimeException("Relasyon Beneficiaire-Projet pa jwenn"));

        return payrollDao.findByProjetBeneficiaire(pb) // ← VOYE objè a, pa id li
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }



    private PayrollDto convertToDto(Payroll p) {
        return new PayrollDto(
                p.getIdPayroll(),
                p.getIdTransaction(),
                p.getMethodePaiement(),
                p.getDebutPeriode(),
                p.getFinPeriode(),
                p.getMontantPayer(),
                p.getStatut(),
                p.getDatePaiement()
        );
    }

    @Override
    public PayrollDto updatePayroll(String payrollId, PayrollDto dto) {
        Payroll payroll = payrollDao.findById(payrollId)
                .orElseThrow(() -> new RuntimeException("Payroll pa jwenn"));

        // Mete ajou tout champs
        payroll.setIdTransaction(dto.getIdTransaction());
        payroll.setMethodePaiement(dto.getMethodePaiement());
        payroll.setDebutPeriode(dto.getDebutPeriode());
        payroll.setFinPeriode(dto.getFinPeriode());
        payroll.setMontantPayer(dto.getMontantPayer());
        payroll.setStatut(dto.getStatut());
        payroll.setDatePaiement(dto.getDatePaiement());

        // Sove li
        Payroll saved = payrollDao.save(payroll);

        // Retounen DTO a
        return convertToDto(saved);
    }

}
