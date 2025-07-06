package com.natha.dev.IService;

import com.natha.dev.Dto.FormationDto;

import java.util.List;

public interface FormationIService {

    FormationDto save(FormationDto dto);
    FormationDto update(String idFormation, FormationDto dto);
    void deleteById(String idFormation);
    FormationDto getById(String idFormation);
    List<FormationDto> getAll();

}
