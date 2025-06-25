package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.MySystemDao;
import com.natha.dev.Dto.MySystemDto;
import com.natha.dev.IService.MySystemIService;
import com.natha.dev.Model.MySystem;
import com.natha.dev.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MySystemImpl implements MySystemIService {

    @Autowired
    private MySystemDao mySystemDao;


    @Override
    public Optional<MySystemDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<MySystemDto> findByUsers(String userName) {
        return null;
    }

    @Override
    public List<MySystemDto> findAll() {
        return null;
    }


    @Override
    public void deleteById(Long id) {

    }

    @Override
    public MySystemDto save(MySystemDto mySystemDto) {
        return null;
    }

    @Override
    public List<Users> findUsersByMySysteId(Long mySystemId) {
        return null;
    }

    private MySystemDto convertToDto(MySystem mySystem) {
        MySystemDto dto = new MySystemDto();
        dto.setId(mySystem.getId());
        dto.setActive(mySystem.getActive());
        dto.setCode(mySystem.getCode());
        dto.setDescription(mySystem.getDescription());
        dto.setCreatedBy(mySystem.getCreatedBy());
        dto.setCreatedAt(mySystem.getCreatedAt());


        return dto;
    }

    private MySystem convertToEntity(MySystemDto dto) {
        MySystem mySystem = new MySystem();
        mySystem.setId(dto.getId());
        mySystem.setActive(dto.getActive());
        mySystem.setDescription(dto.getDescription());
        mySystem.setCode(dto.getCode());
        mySystem.setCreatedAt(dto.getCreatedAt());
        mySystem.setCreatedBy(dto.getCreatedBy());
        // Vous pouvez ajouter d'autres attributs ici

        return mySystem;
    }

    private List<MySystemDto> convertToDtoList(List<MySystem> mySystems) {
        return mySystems.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
