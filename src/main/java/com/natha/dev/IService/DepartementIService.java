package com.natha.dev.IService;

import com.natha.dev.Dto.DepartementDto;
import com.natha.dev.Model.Departement;

import java.util.List;

public interface DepartementIService {
    DepartementDto save(DepartementDto dto);
    void delete(Long id);
    List<DepartementDto> getAll();
}