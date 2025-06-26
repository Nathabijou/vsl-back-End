package com.natha.dev.Dao;

import com.natha.dev.Model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanDao extends JpaRepository<Loan, String > {

    List<Loan> findByAccountIdAccount(String accountId);
}
