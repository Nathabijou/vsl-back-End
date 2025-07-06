package com.natha.dev.Dao;

import com.natha.dev.Model.Formation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormationDao extends JpaRepository<Formation, String> {
}
