package com.natha.dev.Dao;

import com.natha.dev.Model.Deposit;

import java.util.List;
import java.util.Optional;

public interface DepositDao {
    List<Deposit> findAll();
    Optional<Deposit> findById(String id);
    Deposit save(Deposit deposit);
    void deleteById(String id);
    List<Deposit> findByAccountId(String accountId);
    // Add other necessary methods here
}
