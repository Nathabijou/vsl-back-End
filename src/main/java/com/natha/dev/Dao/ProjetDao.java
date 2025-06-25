package com.natha.dev.Dao;

import com.natha.dev.Model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjetDao extends JpaRepository<Projet, String> {
    List<Projet> findByComposanteId(Long composanteId);
    List<Projet> findByComposanteIdAndQuartierId(Long composanteId, String quartierId);
    List<Projet> findAllByActiveTrue();
}
