package com.natha.dev.Dao;


import com.natha.dev.Model.Beneficiaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeneficiaireDao extends JpaRepository<Beneficiaire, String> {
}
