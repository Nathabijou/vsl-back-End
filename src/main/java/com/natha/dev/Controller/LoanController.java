package com.natha.dev.Controller;

import com.natha.dev.IService.LoanIService;
import com.natha.dev.Model.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts/{accountId}/loans")
public class LoanController {

    @Autowired
    private LoanIService loanIService;

    @PostMapping
    public ResponseEntity<Loan> createLoan(@PathVariable String accountId, @RequestBody Loan loan) {
        Loan savedLoan = loanIService.createLoan(accountId, loan);
        return ResponseEntity.ok(savedLoan);
    }

    @GetMapping
    public ResponseEntity<List<Loan>> getLoans(@PathVariable String accountId) {
        return ResponseEntity.ok(loanIService.getLoansByAccount(accountId));
    }

    @PutMapping("/{loanId}")
    public ResponseEntity<Loan> updateLoan(@PathVariable String loanId, @RequestBody Loan loan) {
        Loan updatedLoan = loanIService.updateLoan(loanId, loan);
        return ResponseEntity.ok(updatedLoan);
    }

    @DeleteMapping("/{loanId}")
    public ResponseEntity<String> deleteLoan(@PathVariable String loanId) {
        loanIService.deleteLoan(loanId);
        return ResponseEntity.ok("Loan deleted successfully");
    }
}
