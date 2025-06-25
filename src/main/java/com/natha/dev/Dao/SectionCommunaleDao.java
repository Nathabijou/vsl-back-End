package com.natha.dev.Dao;

import com.natha.dev.Model.SectionCommunale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectionCommunaleDao extends JpaRepository<SectionCommunale, Long> {
    List<SectionCommunale> findByCommuneId(Long communeId);
}
