package com.natha.dev.Dao;

import com.natha.dev.Model.Presence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PresenceDao extends JpaRepository<Presence, String> {
    List<Presence> findByProjetBeneficiaireIdProjetBeneficiaire(String idProjetBeneficiaire);
}
