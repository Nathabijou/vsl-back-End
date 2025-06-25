package com.natha.dev.Dao;

import com.natha.dev.Model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZoneDao extends JpaRepository<Zone, Long> {
    List<Zone> findByComposanteId(Long composanteId);
}
