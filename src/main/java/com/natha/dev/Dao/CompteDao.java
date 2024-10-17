package com.natha.dev.Dao;

import com.natha.dev.Model.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompteDao extends JpaRepository<Compte, Long> {

    Optional<Compte> findByUserName(String username);
    Compte findByUserNameAndGroupeId(String userName, Long groupeId);
}
