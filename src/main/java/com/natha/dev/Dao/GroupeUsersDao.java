package com.natha.dev.Dao;

import com.natha.dev.Model.Groupe_Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupeUsersDao extends JpaRepository<Groupe_Users, Long> {
    List<Groupe_Users> findByGroupeId(Long groupeId);
}
