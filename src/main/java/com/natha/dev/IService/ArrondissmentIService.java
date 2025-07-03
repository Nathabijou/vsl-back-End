package com.natha.dev.IService;

import com.natha.dev.Dto.ArrondissementDto;
import com.natha.dev.Dto.CommuneDto;
import com.natha.dev.Model.Arrondissement;

import java.util.List;
import java.util.Optional;

public interface ArrondissmentIService {

    ArrondissementDto save(ArrondissementDto arrondissementDto);
     Optional<ArrondissementDto> findById(Long id);
    List<ArrondissementDto> findAllDepartement();
    List<ArrondissementDto> findAll();
    void deleteById(Long id);
    List<ArrondissementDto> getAll();
    List<ArrondissementDto> getByDepartementId(Long departementId);

}
