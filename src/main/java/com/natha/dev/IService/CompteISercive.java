package com.natha.dev.IService;

import com.natha.dev.Dto.CompteDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface CompteISercive {

    Optional<CompteDto> findById(Long id);
    List<CompteDto> findAll();
    void deleteById(Long id);
    CompteDto save(CompteDto compteDto);
    List<CompteDto> findByUserName(String username);

//    CompteDto createCompteForUserInGroupe(String userName, Long groupeId, CompteDto compteDto);
//    void acheterActions(String userName, Long groupeId, int nombreActions);
//    void acheterActions(String userName, int nombreActions, Long groupeId);


}
