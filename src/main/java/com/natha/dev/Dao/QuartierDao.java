package com.natha.dev.Dao;

import com.natha.dev.Model.Quartier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuartierDao extends JpaRepository<Quartier, Long> {
    List<Quartier> findBySectionCommunaleId(Long sectionCommunaleId);
}
