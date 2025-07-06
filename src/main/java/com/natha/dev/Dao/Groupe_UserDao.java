package com.natha.dev.Dao;

import com.natha.dev.Model.Groupe;
import com.natha.dev.Model.Groupe_Users;
import com.natha.dev.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Groupe_UserDao extends JpaRepository<Groupe_Users, Long> {
    List<Groupe_Users> findByGroupeId(Long groupe_id);

    Optional<Groupe_Users> findByUsersAndGroupe(Users users, Groupe groupe);
    List<Groupe_Users> findByUsersUserName(String userName);

}
