package com.natha.dev.IService;

import com.natha.dev.Dto.ApplicationInstanceDto;


import java.util.List;
import java.util.Optional;

public interface ApplicationInstanceIService {


    Optional<ApplicationInstanceDto> findById(String idApp);
    List<ApplicationInstanceDto> findAll();
    void deleteById(String idApp);
    ApplicationInstanceDto save(ApplicationInstanceDto dto, String orgId);
    ApplicationInstanceDto save(ApplicationInstanceDto applicationInstanceDto);


    ApplicationInstanceDto saveByOrg(ApplicationInstanceDto dto, String orgId);
}
