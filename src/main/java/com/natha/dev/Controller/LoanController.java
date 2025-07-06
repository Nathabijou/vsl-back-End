package com.natha.dev.Controller;

import com.natha.dev.Dao.AccountDao;
import com.natha.dev.Dao.LoanDao;
import com.natha.dev.Dto.LoanDto;
import com.natha.dev.IService.LoanIService;
import com.natha.dev.Model.Account;
import com.natha.dev.Model.Loan;
import com.natha.dev.Model.Refund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
public class LoanController {

    @Autowired
    private LoanDao loanDao;
    @Autowired
    private LoanIService loanIService;
    @Autowired
    private AccountDao accountDao;

    //Creat Loan with Account
    @PostMapping("/accounts/{accountId}/loans/create")
    public ResponseEntity<?> createLoan(@PathVariable String accountId, @RequestBody LoanDto loanDto) {
        Account account = accountDao.findById(String.valueOf(Long.parseLong(accountId))) // Ou te itilize String pou findById, men id se Long
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Verifye si account la gen balansDue pozitif
        if (account.getBalanceDue() != null && account.getBalanceDue().compareTo(BigDecimal.ZERO) > 0) {
            return ResponseEntity.badRequest().body("Cannot create loan: account has outstanding balance due.");
        }

        BigDecimal interestRate = account.getGroupeUsers().getGroupe().getTauxInteret();

        Loan loan = new Loan();
        loan.setIdLoan(UUID.randomUUID().toString());
        loan.setAccount(account);
        loan.setPrincipalAmount(loanDto.getPrincipalAmount());
        loan.setInterestRate(interestRate);
        loan.setStartDate(loanDto.getStartDate());
        loan.setDueDate(loanDto.getDueDate());
        loan.setStatus("EN_COURS");

        // Kalkile balansDue: principal + enterè 1 mwa
        BigDecimal principal = loanDto.getPrincipalAmount();
        BigDecimal monthlyInterest = principal.multiply(interestRate.divide(BigDecimal.valueOf(100))); // enterè pou 1 mwa
        BigDecimal initialBalanceDue = principal.add(monthlyInterest);

        // Mete balanceDue sou kont lan
        account.setBalanceDue(initialBalanceDue);
        accountDao.save(account);

        Loan savedLoan = loanIService.save(loan);
        return ResponseEntity.ok(savedLoan);
    }

    @GetMapping("/accounts/{accountId}/loans")
    public List<Loan> getLoansByAccount(@PathVariable String accountId) {
        return loanIService.findByAccountId(accountId);
    }


    //Get refund with loan
    @GetMapping("/loan/{loanId}/list")
    public ResponseEntity<List<Refund>> getRefundsForLoan(@PathVariable String loanId) {
        Loan loan = loanIService.findByIdWithRefunds(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        List<Refund> refunds = loan.getRefunds();
        return ResponseEntity.ok(refunds);
    }


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
