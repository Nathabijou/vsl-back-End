package com.natha.dev.IService;

import com.natha.dev.Dto.RepartitionDto;

import java.util.List;

public interface IRepartitionService {
    List<RepartitionDto> getRepartitionByGroupe(Long groupeId);
}
