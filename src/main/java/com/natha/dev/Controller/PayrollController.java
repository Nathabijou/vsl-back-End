package com.natha.dev.Controller;

import com.natha.dev.Dto.PayrollDto;
import com.natha.dev.IService.PayrollIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payrolls")
public class PayrollController {

    @Autowired
    private PayrollIService payrollIService;

    @PostMapping("/projets/{projetId}/beneficiaires/{beneficiaireId}")
    public ResponseEntity<PayrollDto> createPayroll(
            @PathVariable String projetId,
            @PathVariable String beneficiaireId,
            @RequestBody PayrollDto dto) {
        PayrollDto result = payrollIService.createPayroll(projetId, beneficiaireId, dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/projets/{projetId}/beneficiaire/{beneficiaireId}")
    public ResponseEntity<List<PayrollDto>> getAllPayrolls(
            @PathVariable String projetId,
            @PathVariable String beneficiaireId) {
        return ResponseEntity.ok(
                payrollIService.getPayrollsByProjetBeneficiaire(projetId, beneficiaireId)
        );
    }

    @PutMapping("/payrolls/{payrollId}")
    public ResponseEntity<PayrollDto> updatePayroll(
            @PathVariable String payrollId,
            @RequestBody PayrollDto dto) {

        PayrollDto updated = payrollIService.updatePayroll(payrollId, dto);
        return ResponseEntity.ok(updated);
    }

}
