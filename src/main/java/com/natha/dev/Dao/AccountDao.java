package com.natha.dev.Dao;

import com.natha.dev.Model.Account;
import com.natha.dev.Model.Groupe_Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountDao extends JpaRepository<Account, String> {

    Optional<Account> findByUserName(String username);
    Account findByUserNameAndGroupeId(String userName, Long groupeId);

    boolean existsByNumeroCompte(String numeroCompte);
    boolean existsByGroupeUsers(Groupe_Users groupeUsers);




}
