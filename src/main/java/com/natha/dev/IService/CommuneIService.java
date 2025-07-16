package com.natha.dev.IService;

import com.natha.dev.Dto.CommuneDto;
import com.natha.dev.Dto.DepartementDto;
import com.natha.dev.Dto.QuartierDto;

import java.util.List;
import java.util.Optional;

public interface CommuneIService {

    Optional<CommuneDto> findById(Long id); // Utilisation du DTO dans le retour
    List<CommuneDto> findAllZone(); // Utilisation du DTO dans le retour
    //DepartementDto saveCommuneInZone(Commune communeDto, Long zoneId);
    List<CommuneDto> findAll(); // Utilisation du DTO dans le retour
    void deleteById(Long id);
    void delete(Long id);
    List<CommuneDto> getAll();
    List<CommuneDto> getByArrondissmentId(Long arrondissmentId);

    List<CommuneDto> findByArrondissementId(Long arrondissementId);

    CommuneDto save(CommuneDto communeDto);

    CommuneDto update(Long id, CommuneDto communeDto);

}
