package com.natha.dev.IService;

import com.natha.dev.Model.Account;
import com.natha.dev.Dto.LoanDto;
import com.natha.dev.Model.Loan;
import com.natha.dev.Model.Refund;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface LoanIService {
    Loan createLoan(String accountId, LoanDto loanDto);

    BigDecimal calculateBalanceDue(Loan loan);

    List<Loan> getLoansByAccount(String accountId);
    Loan updateLoan(String idLoan, Loan loan);
    void deleteLoan(String idLoan);
    Loan save(Loan loan);
    List<Loan> findByAccountId(String accountId);
    Optional<Loan> findById(String loanId);

    Optional<Loan> findByIdWithRefunds(String idLoan);

    void processRefund(Refund refundRequest);

    Account updateAndSaveBalanceDue(String idLoan);

    List<Loan> findAllByStatus(String status);
}
