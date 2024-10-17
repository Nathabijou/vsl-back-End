package com.natha.dev.Dao;

import com.natha.dev.Model.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupeDao extends JpaRepository<Groupe, Long> {
    //    List<Groupe> findByUserGroupByGroupeId(Long groupeId);
    List<Groupe> findByUserGroup(Long id);

    List<Groupe> findByCommuneId(Long communeId);
}
