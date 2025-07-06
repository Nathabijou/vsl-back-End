package com.natha.dev.Dao;

import com.natha.dev.Model.Arrondissement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArrondissementDao extends JpaRepository<Arrondissement, Long> {
    List<Arrondissement> findByDepartementId(Long departementId);
}
