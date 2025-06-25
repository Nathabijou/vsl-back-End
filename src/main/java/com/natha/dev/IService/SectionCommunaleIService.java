package com.natha.dev.IService;

import com.natha.dev.Dto.SectionCommunaleDto;

import java.util.List;

public interface SectionCommunaleIService {
    SectionCommunaleDto save(SectionCommunaleDto dto);
    List<SectionCommunaleDto> getByCommune(Long communeId);
    void delete(Long id);
    List<SectionCommunaleDto> getAll();
}
