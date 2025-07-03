package com.natha.dev.IService;


import com.natha.dev.Dto.ComposanteDto;
import com.natha.dev.Dto.UsersDto;
import com.natha.dev.Model.Users;

import java.util.List;

public interface ComposanteIService {
    ComposanteDto save(ComposanteDto dto, String applicationId);
    List<ComposanteDto> findByApplication(String applicationId);
    ComposanteDto update(Long id, ComposanteDto dto);
    void delete(Long id);

    void assignUserToComposante(String userName, Long composanteId);

    List<ComposanteDto> findAll();

    ComposanteDto updateByApplication(String applicationId, Long composanteId, ComposanteDto dto);

    void deleteByApplication(String applicationId, Long composanteId);

    List<Users> findUsersByComposante(Long composanteId);


    List<ComposanteDto> findByApplicationAndUser(String applicationId, String username);

    void removeUserFromComposante(String userName, Long composanteId);

    List<UsersDto> getUsersByComposanteId(Long composanteId);
    List<ComposanteDto> getComposantesByUserName(String userName);

}
