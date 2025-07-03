package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.ArrondissementDao;
import com.natha.dev.Dao.DepartementDao;
import com.natha.dev.Dto.ArrondissementDto;
import com.natha.dev.Model.Arrondissement;
import com.natha.dev.Model.Departement;
import com.natha.dev.IService.ArrondissmentIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArrondissmentImpl implements ArrondissmentIService {


    @Autowired
    private DepartementDao departementDao;
    @Autowired
    private ArrondissementDao arrondissementDao;

    @Override
    public ArrondissementDto save(ArrondissementDto arrondissementDto) {
        Arrondissement arrondissement = convertToEntity(arrondissementDto);
        Arrondissement saveArrondissement = arrondissementDao.save(arrondissement);
        return convertToDto(saveArrondissement);
    }



    @Override
    public Optional<ArrondissementDto> findById(Long id) {
       Optional<Arrondissement> arrondissement =arrondissementDao.findById(id);
       return arrondissement.map(this::convertToDto);
    }




    @Override
    public List<ArrondissementDto> findAllDepartement() {
        return null;
    }

    private ArrondissementDto convertToDto(Arrondissement arrondissement) {
        ArrondissementDto dto = new ArrondissementDto();
        dto.setId(arrondissement.getId());
        dto.setName(arrondissement.getName());

        return dto;
    }


    private Arrondissement convertToEntity(ArrondissementDto dto) {
        Arrondissement arrondissement = new Arrondissement();
        arrondissement.setId(dto.getId());
        arrondissement.setName(dto.getName());

        if (dto.getDepartementId() != null) {
            Departement departement = departementDao.findById(dto.getDepartementId())
                    .orElseThrow(() -> new RuntimeException("Département non trouvé avec l'id: " + dto.getDepartementId()));
            arrondissement.setDepartement(departement);
        } else {
            throw new RuntimeException("Département est requis pour créer un arrondissement.");
        }

        return arrondissement;
    }



    private List<ArrondissementDto> convertToDtoList(List<Arrondissement> arrondissements) {
        return arrondissements.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArrondissementDto> findAll() {
        List<Arrondissement> arrondissements = arrondissementDao.findAll();
        return convertToDtoList(arrondissements);
    }



    @Override
    public void deleteById(Long id) {
        arrondissementDao.deleteById(id);

    }


    @Override
    public List<ArrondissementDto> getAll() {
        return arrondissementDao.findAll().stream().map(this::convertToDto).toList();
    }


    @Override
    public List<ArrondissementDto> getByDepartementId(Long departementId) {
        return arrondissementDao.findByDepartementId(departementId).stream().map(this::convertToDto).toList();
    }


}
