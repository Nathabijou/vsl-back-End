package com.natha.dev.Dao;

import com.natha.dev.Dto.CommuneDto;
import com.natha.dev.Model.Commune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommuneDao extends JpaRepository<Commune, Long> {

    CommuneDto findById(CommuneDto communeDto);
    @Query("SELECT c FROM Commune c WHERE c.arrondissement.id = :param")
    List<Commune> test(@Param("param") Long arrondissementId);


    List<Commune> findByArrondissementId(Long arrondissementId);

}
