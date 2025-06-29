package com.natha.dev.IService;

import com.natha.dev.Dto.ApplicationInstanceDto;
import com.natha.dev.Model.Users;


import java.util.List;
import java.util.Optional;

public interface ApplicationInstanceIService {


    Optional<ApplicationInstanceDto> findById(String idApp);
    List<ApplicationInstanceDto> findAll();
    void deleteById(String idApp);
    ApplicationInstanceDto save(ApplicationInstanceDto dto, String orgId);
    ApplicationInstanceDto save(ApplicationInstanceDto applicationInstanceDto);


    ApplicationInstanceDto saveByOrg(ApplicationInstanceDto dto, String orgId);

    List<Users> getUsersByApplication(String idApp);
    ApplicationInstanceDto addUserToApplication(String userName, String appId);
    ApplicationInstanceDto createStandaloneApp(ApplicationInstanceDto dto);

    List<ApplicationInstanceDto> getApplicationsByUser(String userName);




}
