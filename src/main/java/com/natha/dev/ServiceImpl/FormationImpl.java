package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.FormationDao;
import com.natha.dev.Dao.ProjetBeneficiaireDao;
import com.natha.dev.Dao.ProjetBeneficiaireFormationDao;
import com.natha.dev.Dto.FormationDto;
import com.natha.dev.IService.FormationIService;
import com.natha.dev.Model.Formation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FormationImpl implements FormationIService {

    @Autowired
    private FormationDao dao;

    @Autowired
    private ProjetBeneficiaireDao projetBeneficiaireDao;
    @Autowired
    private FormationDao formationDao;

    @Autowired
    private ProjetBeneficiaireFormationDao projetBeneficiaireFormationDao;

    @Override
    public FormationDto save(FormationDto dto) {
        Formation formation = new Formation();
        formation.setIdFormation(UUID.randomUUID().toString()); // ID generate
        formation.setTitre(dto.getTitre());
        formation.setDescription(dto.getDescription());
        formation.setDateDebut(dto.getDateDebut());
        formation.setDateFin(dto.getDateFin());
        formation.setTypeFormation(dto.getTypeFormation());

        formationDao.save(formation);

        dto.setIdFormation(formation.getIdFormation());
        return dto;
    }




    @Override
    public FormationDto update(String idFormation, FormationDto dto) {
        Formation f = dao.findById(idFormation)
                .orElseThrow(() -> new RuntimeException("Formation pa jwenn"));
        f.setTitre(dto.getTitre());
        f.setDescription(dto.getDescription());
        f.setDateDebut(dto.getDateDebut());
        f.setDateFin(dto.getDateFin());
        f.setTypeFormation(dto.getTypeFormation());  // ajoute sa tou
        return convertToDto(dao.save(f));
    }

    @Override
    public void deleteById(String idFormation) {
        dao.deleteById(idFormation);
    }

    @Override
    public FormationDto getById(String idFormation) {
        return dao.findById(idFormation)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Formation pa jwenn"));
    }

    @Override
    public List<FormationDto> getAll() {
        return dao.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private FormationDto convertToDto(Formation f) {
        return new FormationDto(
                f.getIdFormation(),
                f.getTitre(),
                f.getDescription(),
                f.getDateDebut(),
                f.getDateFin(),
                f.getTypeFormation()  // ajoute sa
        );
    }

    private Formation convertToEntity(FormationDto d) {
        Formation f = new Formation();
        f.setIdFormation(d.getIdFormation());
        f.setTitre(d.getTitre());
        f.setDescription(d.getDescription());
        f.setDateDebut(d.getDateDebut());
        f.setDateFin(d.getDateFin());
        f.setTypeFormation(d.getTypeFormation());  // ajoute sa tou
        return f;
    }

}
