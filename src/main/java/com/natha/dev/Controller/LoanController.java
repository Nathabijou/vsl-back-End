package com.natha.dev.Controller;

import com.natha.dev.Dao.AccountDao;
import com.natha.dev.Dao.LoanDao;
import com.natha.dev.Dto.LoanDto;
import com.natha.dev.IService.LoanIService;
import com.natha.dev.Model.Account;
import com.natha.dev.Model.Loan;
import com.natha.dev.Model.Refund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    /**
     * Kreye yon nouvo prè pou yon kont espesifik.
     * Wout sa a asire ke tout valè (tankou balans) byen inisyalize anvan yo anrejistre prè a.
     * @param accountId ID kont kote prè a pral asosye a.
     * @param loanDto Enfòmasyon sou prè a (montan, enterè) ki soti nan kò demann lan.
     * @return Yon repons HTTP ki gen prè ki fèk kreye a.
     */
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Manager')")
    @PostMapping("/accounts/{accountId}/loans/create")
    public ResponseEntity<Loan> createLoan(@PathVariable String accountId, @RequestBody LoanDto loanDto) {
        try {
            Loan newLoan = loanIService.createLoan(accountId, loanDto);
            return new ResponseEntity<>(newLoan, HttpStatus.CREATED);
        } catch (Exception e) {
            // Loge erè a pou ka fè dyagnostik pi fasil
            // logger.error("Erè pandan kreyasyon prè pou kont {}: ", accountId, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Manager')")
    @GetMapping("/{idLoan}")
    public ResponseEntity<Loan> getLoanById(@PathVariable String idLoan) {
        Loan loan = loanIService.findById(idLoan)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        return ResponseEntity.ok(loan);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Manager')")
    @GetMapping("/accounts/{accountId}/loans")
    public List<Loan> getLoansByAccount(@PathVariable String accountId) {
        return loanIService.findByAccountId(accountId);
    }

    //Get refund with loan
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Manager')")
    @GetMapping("/loan/{loanId}/list")
    public ResponseEntity<List<Refund>> getRefundsForLoan(@PathVariable String loanId) {
        Loan loan = loanIService.findByIdWithRefunds(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        List<Refund> refunds = loan.getRefunds();
        return ResponseEntity.ok(refunds);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Manager')")
    @PutMapping("/{loanId}")
    public ResponseEntity<Loan> updateLoan(@PathVariable String loanId, @RequestBody Loan loan) {
        Loan updatedLoan = loanIService.updateLoan(loanId, loan);
        return ResponseEntity.ok(updatedLoan);
    }
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Manager')")
    @DeleteMapping("/{loanId}")
    public ResponseEntity<String> deleteLoan(@PathVariable String loanId) {
        loanIService.deleteLoan(loanId);
        return ResponseEntity.ok("Loan deleted successfully");
    }
}
