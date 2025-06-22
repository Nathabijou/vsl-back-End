package com.natha.dev.Dao;

import com.natha.dev.Dto.CommuneDto;
import com.natha.dev.Dto.MySystemDto;
import com.natha.dev.Model.MySystem;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MySystemDao extends JpaRepository<Long, MySystem> {

    CommuneDto findById(CommuneDto communeDto);
}

