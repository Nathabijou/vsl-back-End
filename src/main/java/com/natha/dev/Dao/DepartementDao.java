package com.natha.dev.Dao;

import com.natha.dev.Model.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartementDao extends JpaRepository<Departement, Long> {
    List<Departement> findByZoneId(Long zoneId);
}
