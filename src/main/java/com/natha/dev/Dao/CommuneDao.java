package com.natha.dev.Dao;

import com.natha.dev.Dto.CommuneDto;
import com.natha.dev.Model.Commune;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommuneDao extends JpaRepository<Commune, Long> {

    CommuneDto findById(CommuneDto communeDto);
}
