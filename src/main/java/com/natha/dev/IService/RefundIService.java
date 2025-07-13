package com.natha.dev.IService;

import com.natha.dev.Model.Loan;
import com.natha.dev.Model.Refund;

import java.util.List;

public interface RefundIService {
    Refund save(Refund refund);
    List<Refund> findByLoanId(String loanId);

    void flush(); // <<< AJOUTE SA

    Object findByLoan(Loan loan);
}
