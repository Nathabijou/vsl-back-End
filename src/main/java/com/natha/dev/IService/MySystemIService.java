package com.natha.dev.IService;

import com.natha.dev.Dto.MySystemDto;
import com.natha.dev.Model.Users;

import java.util.List;
import java.util.Optional;

public interface MySystemIService {

    abstract Optional<MySystemDto> findById(Long id);


    List<MySystemDto> findByUsers(String userName);

    List<MySystemDto> findAll();
    void deleteById(Long id);
    MySystemDto save(MySystemDto mySystemDto);

    List<Users> findUsersByMySysteId(Long mySystemId);

//    List<GroupeDto> findByCommuneId(Long communeId);
//    GroupeDto saveById(GroupeDto groupeDto, Long communeId);
}
