package com.natha.dev.IService;

import com.natha.dev.Dto.Groupe_UsersDto;
import com.natha.dev.Model.Groupe;
import com.natha.dev.Model.Groupe_Users;
import com.natha.dev.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Groupe_UsersIService extends JpaRepository<Groupe_Users, Long> {
//    Optional<Groupe_UsersDto> findByUsersAndGroupe(Users user, Groupe groupe);
}
