package com.natha.dev.Dao;

import com.natha.dev.Model.Formation;
import com.natha.dev.Model.ProjetBeneficiaire;
import com.natha.dev.Model.ProjetBeneficiaireFormation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjetBeneficiaireFormationDao extends JpaRepository<ProjetBeneficiaireFormation, String> {

    boolean existsByProjetBeneficiaireAndFormation(ProjetBeneficiaire projetBeneficiaire, Formation formation);

    Optional<Object> findByProjetBeneficiaireAndFormationIdFormation(ProjetBeneficiaire pb, String idFormation);
}
