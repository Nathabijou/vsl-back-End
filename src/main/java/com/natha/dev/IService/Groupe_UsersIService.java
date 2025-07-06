package com.natha.dev.IService;

import com.natha.dev.Model.Groupe_Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Groupe_UsersIService extends JpaRepository<Groupe_Users, Long> {
//    Optional<Groupe_UsersDto> findByUsersAndGroupe(Users user, Groupe groupe);
}
