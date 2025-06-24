package com.natha.dev.IService;


import com.natha.dev.Dto.ComposanteDto;

import java.util.List;

public interface ComposanteIService {
    ComposanteDto save(ComposanteDto dto, String applicationId);
    List<ComposanteDto> findByApplication(String applicationId);
    ComposanteDto update(Long id, ComposanteDto dto);
    void delete(Long id);

    List<ComposanteDto> findAll();

    ComposanteDto updateByApplication(String applicationId, Long composanteId, ComposanteDto dto);

    void deleteByApplication(String applicationId, Long composanteId);
}
