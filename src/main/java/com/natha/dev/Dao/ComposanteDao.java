package com.natha.dev.Dao;

import com.natha.dev.Model.Composante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface ComposanteDao extends JpaRepository<Composante, Long> {
    List<Composante> findByApplicationInstanceId(String applicationId);

}
