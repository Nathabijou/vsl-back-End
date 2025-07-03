package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.*;
import com.natha.dev.Dto.UserApplicationPrivilegeDto;

import com.natha.dev.IService.UserApplicationPrivilegeIService;
import com.natha.dev.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserApplicationPrivilegeServiceImpl implements UserApplicationPrivilegeIService {

    @Autowired
    private UserApplicationPrivilegeDao userApplicationPrivilegeDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ApplicationInstanceDao applicationInstanceDao;

    @Autowired
    private PrivilegeDao privilegeDao;

    @Override
    public UserApplicationPrivilegeDto save(UserApplicationPrivilegeDto dto) {
        Users user = userDao.findById(dto.getUserName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Pas itilize Long.parseLong paske idApp se String alfanumerik
        ApplicationInstance app = applicationInstanceDao.findById(dto.getApplicationId())
                .orElseThrow(() -> new RuntimeException("Application not found"));

        Privilege privilege = privilegeDao.findById(dto.getPrivilegeName())
                .orElseThrow(() -> new RuntimeException("Privilege not found"));

        UserAppPrivKey key = new UserAppPrivKey(
                user.getUserName(),
                app.getIdApp(),
                privilege.getPrivilegeName()
        );

        UserApplicationPrivilege uap = new UserApplicationPrivilege();
        uap.setId(key);
        uap.setUser(user);
        uap.setApplication(app);
        uap.setPrivilege(privilege);

        UserApplicationPrivilege saved = userApplicationPrivilegeDao.save(uap);
        return toDto(saved);
    }


    @Override
    public Optional<UserApplicationPrivilegeDto> findById(String userName, String applicationId, String privilegeName) {
        UserAppPrivKey key = new UserAppPrivKey(userName, Long.parseLong(applicationId), privilegeName);
        Optional<UserApplicationPrivilege> entityOpt = userApplicationPrivilegeDao.findById(key);
        return entityOpt.map(this::toDto);
    }

    @Override
    public List<UserApplicationPrivilegeDto> findAll() {
        return userApplicationPrivilegeDao.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String userName, String applicationId, String privilegeName) {
        UserAppPrivKey key = new UserAppPrivKey(userName, Long.parseLong(applicationId), privilegeName);
        userApplicationPrivilegeDao.deleteById(key);
    }

    private UserApplicationPrivilegeDto toDto(UserApplicationPrivilege entity) {
        UserApplicationPrivilegeDto dto = new UserApplicationPrivilegeDto();
        dto.setUserName(entity.getUser().getUserName());
        dto.setApplicationId(String.valueOf(entity.getApplication().getId()));
        dto.setPrivilegeName(entity.getPrivilege().getPrivilegeName());
        return dto;
    }

    private UserApplicationPrivilege toEntity(UserApplicationPrivilegeDto dto) {
        UserApplicationPrivilege entity = new UserApplicationPrivilege();
        UserAppPrivKey key = new UserAppPrivKey(dto.getUserName(), Long.parseLong(dto.getApplicationId()), dto.getPrivilegeName());
        entity.setId(key);
        return entity;
    }
}
