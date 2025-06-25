package com.natha.dev.Dao;

import com.natha.dev.Model.Payroll;
import com.natha.dev.Model.ProjetBeneficiaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PayrollDao extends JpaRepository<Payroll, String> {
    List<Payroll> findByProjetBeneficiaire(ProjetBeneficiaire projetBeneficiaire);
    Optional<Payroll> findById(String id);



}