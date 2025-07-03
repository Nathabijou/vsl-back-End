package com.natha.dev.IService;

import com.natha.dev.Dto.ZoneDto;
import java.util.List;

public interface ZoneIService {
    ZoneDto save(ZoneDto dto);
    List<ZoneDto> getAll();
    List<ZoneDto> getByComposanteId(Long composanteId);
    void delete(Long id);

}
