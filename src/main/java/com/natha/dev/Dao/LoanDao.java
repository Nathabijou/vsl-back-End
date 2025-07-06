package com.natha.dev.Dao;

import com.natha.dev.Model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LoanDao extends JpaRepository<Loan, String > {

    List<Loan> findByAccount_Id(String accountId);

    boolean existsByAccount_IdAndStatus(String accountId, String status);

    @Query("SELECT l FROM Loan l LEFT JOIN FETCH l.refunds WHERE l.idLoan = :idLoan")
    Optional<Loan> findByIdWithRefunds(@Param("idLoan") String idLoan);


}
