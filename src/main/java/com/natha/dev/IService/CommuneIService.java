package com.natha.dev.IService;

import com.natha.dev.Dto.CommuneDto;

import java.util.List;
import java.util.Optional;

public interface CommuneIService {

    Optional<CommuneDto> findById(Long id); // Utilisation du DTO dans le retour
    List<CommuneDto> findAllZone(); // Utilisation du DTO dans le retour
    //DepartementDto saveCommuneInZone(Commune communeDto, Long zoneId);
    List<CommuneDto> findAll(); // Utilisation du DTO dans le retour
    void deleteById(Long id);

    CommuneDto save(CommuneDto communeDto);
}
