package com.natha.dev.IService;

import com.natha.dev.Dto.QuartierDto;

import java.util.List;

public interface QuartierIService {


        QuartierDto save(QuartierDto dto);
        List<QuartierDto> getBySectionCommunale(Long SectionCommunaleId);
        void delete(Long id);
        List<QuartierDto> getAll();

}
