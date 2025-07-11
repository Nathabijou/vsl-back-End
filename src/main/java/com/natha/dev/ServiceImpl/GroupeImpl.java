package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.CommuneDao;
import com.natha.dev.Dao.GroupeDao;
import com.natha.dev.Dao.Groupe_UserDao;
import com.natha.dev.Dao.UserDao;
import com.natha.dev.Dto.GroupeDto;
import com.natha.dev.Exeption.CommuneNotFoundException;
import com.natha.dev.IService.GroupeIService;
import com.natha.dev.Model.Commune;
import com.natha.dev.Model.Groupe;
import com.natha.dev.Model.Groupe_Users;
import com.natha.dev.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupeImpl implements GroupeIService {

    @Autowired
    private GroupeDao groupeDao;
    @Autowired
    private CommuneDao communeDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private Groupe_UserDao groupeUserDao;

    @Override
    public Optional<GroupeDto> findById(Long id) {
        Optional<Groupe> groupe = groupeDao.findById(id);
        return groupe.map(this::convertToDto);
    }


    @Override
    public List<GroupeDto> findByUsers(String userName) {
        Users user = userDao.findById(userName)
                .orElseThrow(() -> new RuntimeException("User not found: " + userName));

        List<Groupe_Users> groupeUsersList = groupeUserDao.findByUsersUserName(userName);

        return groupeUsersList.stream()
                .map(gu -> convertToDto(gu.getGroupe()))
                .collect(Collectors.toList());
    }

    private GroupeDto convertToDto(Groupe groupe) {
        GroupeDto dto = new GroupeDto();
        dto.setId(groupe.getId());
        dto.setNom(groupe.getNom());
        dto.setAdresse(groupe.getAdresse());
        dto.setDescription(groupe.getDescription());
        dto.setPrixAction(groupe.getPrixAction());
        dto.setResponsable(groupe.getResponsable());
        dto.setTauxInteret(groupe.getTauxInteret());
//        dto.setCommuneId(groupe.getCommune().getId());
        groupe.setDatecreation(LocalDateTime.now());
        dto.setDatecreation(groupe.getDatecreation());




        return dto;
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


    @Override
    public GroupeDto saveById(GroupeDto groupeDto, Long communeId) {
        // Recherche du programme parent
        Commune communeParent = communeDao.findById(communeId).orElse(null);

        if (communeParent != null) {
            // Créez une nouvelle instance de Composante à partir de composanteDto
            Groupe newGroupe = new Groupe();
            newGroupe.setId(groupeDto.getId());
            newGroupe.setNom(groupeDto.getNom());
            newGroupe.setDescription(groupeDto.getDescription());
            newGroupe.setAdresse(groupeDto.getAdresse());
            newGroupe.setPrixAction(groupeDto.getPrixAction());
            newGroupe.setDatecreation();
            newGroupe.setTauxInteret(groupeDto.getTauxInteret());
            newGroupe.setResponsable(groupeDto.getResponsable());
            newGroupe.setDatecreation();

            // Associez la nouvelle composante au programme
//            newGroupe.setCommune(communeParent);

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
        groupe.setNom(groupeDto.getNom());
        groupe.setAdresse(groupeDto.getAdresse());
        groupe.setDescription(groupeDto.getDescription());
        groupe.setResponsable(groupeDto.getResponsable());
        groupe.setTauxInteret(groupeDto.getTauxInteret());
        groupe.setPrixAction(groupeDto.getPrixAction());
        groupe.setDatecreation();

        return groupe;

    }
}
