package com.natha.dev.Dao;


import com.natha.dev.Model.Beneficiaire;
import com.natha.dev.Model.DashboardFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface BeneficiaireDao extends JpaRepository<Beneficiaire, String> {
    Optional<Beneficiaire> findById(String beneficiaireId);

    // Total Beneficiaires filtre selon chak id (ki ka nullable)





}
