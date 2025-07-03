package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.ArrondissementDao;
import com.natha.dev.Dao.CommuneDao;
import com.natha.dev.Dto.CommuneDto;
import com.natha.dev.IService.CommuneIService;
import com.natha.dev.Model.Arrondissement;
import com.natha.dev.Model.Commune;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class CommuneImpl implements CommuneIService {

    @Autowired
    private CommuneDao communeDao;
    @Autowired
    private ArrondissementDao arrondissmentDao;


    @Override
    public List<CommuneDto> findAllZone() {
        return List.of();
    }


    @Override
    public Optional<CommuneDto> findById(Long id) {
        Optional<Commune> commune = communeDao.findById(id);
        return commune.map(this::convertToDto);
    }


    @Override
    public List<CommuneDto> findAll() {
        List<Commune> communes = communeDao.findAll();
        return convertToDtoList(communes);
    }



    @Override
    public void deleteById(Long id) {
        communeDao.deleteById(id);
    }

    @Override
    public void delete(Long id) {
        communeDao.deleteById(id);
    }

    @Override
    public List<CommuneDto> getAll() {
        return communeDao.findAll().stream().map(this::convertToDto).toList();
    }

    @Override
    public List<CommuneDto> getByArrondissmentId(Long arrondissmentId) {
        return communeDao.findByArrondissementId(arrondissmentId).stream().map(this::convertToDto).toList();
    }


    @Override
    public List<CommuneDto> findByArrondissementId(Long arrondissementId) {
        return communeDao.findByArrondissementId(arrondissementId).stream().map(this::convertToDto).toList();
    }



    @Override
    public CommuneDto save(CommuneDto communeDto) {
        Commune commune = convertToEntity(communeDto);
        Commune savedCommune = communeDao.save(commune);
        return convertToDto(savedCommune);
    }


    // Méthodes de conversion
    private CommuneDto convertToDto(Commune commune) {
        CommuneDto dto = new CommuneDto();
        dto.setId(commune.getId());
        dto.setNom(commune.getNom());

        return dto;
    }

    private Commune convertToEntity(CommuneDto dto) {
        Commune commune = new Commune();
        commune.setId(dto.getId());
        commune.setNom(dto.getNom());
        // Vous pouvez ajouter d'autres attributs ici

        if (dto.getDepartementId() != null) {
            Arrondissement arrondissement = arrondissmentDao.findById(dto.getDepartementId())
                    .orElseThrow(() -> new RuntimeException("Département non trouvé avec l'id: " + dto.getDepartementId()));
            commune.setArrondissement(arrondissement);
        } else {
            throw new RuntimeException("departementId est requis pour créer une commune.");
        }

        return commune;
    }

    private List<CommuneDto> convertToDtoList(List<Commune> communes) {
        return communes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}





