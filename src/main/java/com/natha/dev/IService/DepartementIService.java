package com.natha.dev.IService;

import com.natha.dev.Dto.DepartementDto;

import java.util.List;

public interface DepartementIService {
    DepartementDto save(DepartementDto dto);
    List<DepartementDto> getByZone(Long zoneId);
    void delete(Long id);
    List<DepartementDto> getAll();
}