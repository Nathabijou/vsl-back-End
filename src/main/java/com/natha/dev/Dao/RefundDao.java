package com.natha.dev.Dao;

import com.natha.dev.Model.Loan;
import com.natha.dev.Model.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefundDao extends JpaRepository<Refund,Long> {
    List<Refund> findByLoan_IdLoan(String idLoan);

    List<Refund> findByLoan(Loan loan);
}
