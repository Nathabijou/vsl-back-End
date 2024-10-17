package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.CommuneDao;
import com.natha.dev.Dao.GroupeDao;
import com.natha.dev.Dto.GroupeDto;
import com.natha.dev.Exeption.CommuneNotFoundException;
import com.natha.dev.Exeption.CommuneNotFoundException;
import com.natha.dev.IService.GroupeIService;
import com.natha.dev.Model.Commune;
import com.natha.dev.Model.Groupe;
import com.natha.dev.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupeImpl implements GroupeIService {

    @Autowired
    private GroupeDao groupeDao;
    @Autowired
    private CommuneDao communeDao;

    @Override
    public Optional<GroupeDto> findById(Long id) {
        Optional<Groupe> groupe = groupeDao.findById(id);
        return groupe.map(this::convertToDto);
    }

    private GroupeDto convertToDto(Groupe groupe) {
        GroupeDto dto = new GroupeDto();
        dto.setId(groupe.getId());
        dto.setNom(groupe.getNom());
        dto.setAdresse(groupe.getAdresse());
        dto.setDescription(groupe.getDescription());
        dto.setResponsable(groupe.getResponsable());
//        dto.setCommuneId(groupe.getCommune().getId());
        return dto;
    }

    @Override
    public List<GroupeDto> findByUsers(Long users_id) {
        List<Groupe> groupes = groupeDao.findByUserGroup(users_id);
        return groupes.stream().map(groupe -> convertToDto(groupe)).collect(Collectors.toList());
    }

    @Override
    public List<GroupeDto> findAll() {
        List<Groupe> groupes = groupeDao.findAll();
        return convertToDtoList(groupes);
    }

    private List<GroupeDto> convertToDtoList(List<Groupe> groupes) {
        return groupes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        groupeDao.deleteById(id);

    }

    @Override
    public GroupeDto save(GroupeDto groupeDto) {
        Groupe groupe = convertToEntity(groupeDto);
        Groupe savedGroupe = groupeDao.save(groupe);
        return convertToDto(savedGroupe);
    }

    @Override
    public List<Users> findUsersByGroupeId(Long groupeId) {
        return List.of();
    }


    @Override
    public List<GroupeDto> findByCommuneId(Long communeId) {
        List<Groupe> groupes = groupeDao.findByCommuneId(communeId);
        return groupes.stream().map(this::convertToDto).collect(Collectors.toList());
    }

//    @Override
//    public GroupeDto saveById(GroupeDto groupeDto, Long communeId) {
//        return null;
//    }

    @Override
    public GroupeDto saveById(GroupeDto groupeDto, Long communeId) {
        // Recherche du programme parent
        Commune communeParent = communeDao.findById(communeId).orElse(null);

        if (communeParent != null) {
            // Créez une nouvelle instance de Composante à partir de composanteDto
            Groupe newGroupe = new Groupe();
            newGroupe.setNom(groupeDto.getNom());
            newGroupe.setDescription(groupeDto.getDescription());

            // Associez la nouvelle composante au programme
            newGroupe.setCommune(communeParent);

            // Enregistrez la nouvelle composante dans la base de données
            Groupe savedGroupe = groupeDao.save(newGroupe);

            // Convertissez la composante en DTO et retournez-la
            return convertToDto(savedGroupe);
        } else {
            // Gérez le cas où le programme parent n'existe pas
            throw new CommuneNotFoundException("commune introuvable avec l'ID : " + communeId);
        }
    }

    private Groupe convertToEntity(GroupeDto groupeDto) {
        Groupe groupe =  new Groupe();
        groupe.setId(groupeDto.getId());
        groupe.setAdresse(groupeDto.getAdresse());
        groupe.setDescription(groupeDto.getDescription());
        groupe.setNom(groupeDto.getNom());
        groupe.setResponsable(groupeDto.getResponsable());

        return groupe;

    }
}
