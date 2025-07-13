package com.natha.dev.Controller;

import com.natha.dev.Dao.AccountDao;
import com.natha.dev.IService.LoanIService;
import com.natha.dev.IService.RefundIService;
import com.natha.dev.Model.Loan;
import com.natha.dev.Model.Refund;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/refunds")
public class RefundController {

    @Autowired
    private RefundIService refundIService;

    @Autowired
    private LoanIService loanIService;

    @Autowired
    private AccountDao accountDao;
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Manager')")
    @PostMapping("/loan/{loanId}")
    @Transactional
    public ResponseEntity<Refund> createRefund(@PathVariable String loanId, @RequestBody Refund refundRequest) {
        Loan loan = loanIService.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        refundRequest.setLoan(loan);

        if (refundRequest.getRefundDate() == null) {
            refundRequest.setRefundDate(LocalDate.now());
        }

        // Mete refund
        refundIService.save(refundRequest);

        // 👉 Rekalkile total ak loan chaje
        loanIService.processRefund(refundRequest); // li mete balanceDue sou account

        // 👉 Si loan fin peye
        BigDecimal newBalanceDue = loanIService.calculateBalanceDue(loan);
        if (newBalanceDue.compareTo(BigDecimal.ZERO) <= 0) {
            loan.setStatus("PAYE");
            loanIService.save(loan);
        }

        return ResponseEntity.ok(refundRequest);
    }
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Manager')")
    @GetMapping("/loan/{loanId}")
    public ResponseEntity<?> getRefundsByLoan(@PathVariable String loanId) {
        Loan loan = loanIService.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        return ResponseEntity.ok(refundIService.findByLoan(loan));
    }





}
