package com.natha.dev.Dao;

import com.natha.dev.Model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, String> {
    List<Deposit> findByAccountId(Long accountId);
}
