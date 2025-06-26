package com.natha.dev.IService;

import com.natha.dev.Model.Loan;

import java.util.List;

public interface LoanIService {
    Loan createLoan(String accountId, Loan loan);
    List<Loan> getLoansByAccount(String accountId);
    Loan updateLoan(String idLoan, Loan loan);
    void deleteLoan(String idLoan);
}
