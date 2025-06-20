package com.natha.dev.IService;

import com.natha.dev.Dto.GroupeDto;
import com.natha.dev.Model.Groupe;
import com.natha.dev.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupeIService  {
    Optional<GroupeDto> findById(Long id);


    List<GroupeDto> findByUsers(String userName);

    List<GroupeDto> findAll();
    void deleteById(Long id);
    GroupeDto save(GroupeDto groupeDto);

    List<Users> findUsersByGroupeId(Long groupeId);

    List<GroupeDto> findByCommuneId(Long communeId);
    GroupeDto saveById(GroupeDto groupeDto, Long communeId);
}
