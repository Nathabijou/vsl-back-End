package com.natha.dev.IService;

import com.natha.dev.Dto.UserApplicationPrivilegeDto;

import java.util.List;
import java.util.Optional;

public interface UserApplicationPrivilegeIService {

    UserApplicationPrivilegeDto save(UserApplicationPrivilegeDto dto);

    Optional<UserApplicationPrivilegeDto> findById(String userName, String applicationId, String privilegeName);

    List<UserApplicationPrivilegeDto> findAll();

    void delete(String userName, String applicationId, String privilegeName);
}
