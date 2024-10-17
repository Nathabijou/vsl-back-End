package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.CompteDao;
import com.natha.dev.Dao.GroupeDao;
import com.natha.dev.Dao.Groupe_UserDao;
import com.natha.dev.Dao.UserDao;
import com.natha.dev.Dto.CompteDto;
import com.natha.dev.IService.CompteISercive;
import com.natha.dev.IService.GroupeIService;
import com.natha.dev.Model.Compte;
import com.natha.dev.Model.Groupe;
import com.natha.dev.Model.Groupe_Users;
import com.natha.dev.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompteImpl implements CompteISercive {

    @Autowired
    private CompteDao compteDao;

    @Autowired
    private GroupeIService groupeIService;

    @Autowired
    private Groupe_UserDao groupeUserDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private GroupeDao groupeDao;

//    @Override
//    public CompteDto createCompteForUserInGroupe(String userName, Long groupeId, CompteDto compteDto) {
//        // Vérifier si l'utilisateur existe
//        Users users = userDao.findByUserName(userName);
//        if (users == null) {
//            throw new RuntimeException("Utilisateur non trouvé");
//        }
//
//        // Vérifier si le groupe existe
//        Groupe groupe = groupeDao.findById(groupeId)
//                .orElseThrow(() -> new RuntimeException("Groupe non trouvé"));
//
//        // Vérifier que l'utilisateur appartient au groupe
//        Optional<Groupe_Users> groupeUsersOpt = groupeUserDao.findByUsersAndGroupe(users, groupe);
//        if (groupeUsersOpt.isEmpty()) {
//            throw new RuntimeException("L'utilisateur n'appartient pas à ce groupe");
//        }
//
//        Groupe_Users groupeUsers = groupeUsersOpt.get();
//
//        // Vérifier si un compte existe déjà
//        if (groupeUsers.getCompte() != null) {
//            throw new RuntimeException("Un compte existe déjà pour cet utilisateur dans ce groupe");
//        }
//
//        // Créer le compte
//        Compte compte = convertToEntity(compteDto);
//
//        // Associer le compte à groupeUsers (relation bidirectionnelle)
//        compte.setGroupeUsers(groupeUsers);
//        groupeUsers.setCompte(compte); // Important : synchroniser des deux côtés de la relation
//
//        // Sauvegarder le compte
//        Compte savedCompte = compteDao.save(compte);
//
//        // Sauvegarder l'association dans groupe_users
//        groupeUserDao.save(groupeUsers);
//
//        return convertToDto(savedCompte);
//    }

    @Override
    public Optional<CompteDto> findById(Long id) {
        Optional<Compte> compte = compteDao.findById(id);
        return compte.map(this::convertToDto);
    }

    @Override
    public List<CompteDto> findAll() {
        List<Compte> comptes = compteDao.findAll();
        return comptes.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        compteDao.deleteById(id);
    }

    @Override
    public CompteDto save(CompteDto compteDto) {
        Compte compte = convertToEntity(compteDto);
        Compte savedCompte = compteDao.save(compte);
        return convertToDto(savedCompte);
    }

    @Override
    public List<CompteDto> findByUserName(String username) {
        Optional<Compte> compte = compteDao.findByUserName(username);
        return compte.stream().map(this::convertToDto).collect(Collectors.toList());
    }



    private CompteDto convertToDto(Compte compte) {
        CompteDto compteDto = new CompteDto();
        compteDto.setId(compte.getId());
        compteDto.setBalance(compte.getBalance());
        compteDto.setInteret(compte.getInteret());
        compteDto.setBalanceDue(compte.getBalanceDue());
        compteDto.setDatePret(compte.getDatePret());
        compteDto.setPrixAction(compte.getPrixAction());
        compteDto.setNumeroCompte(compte.getNumeroCompte());

//        compteDto.setGroupeuserId(compte.getGroupeUsers().getId());



        return compteDto;
    }

//   if (compte.getGroupeUsers() != null) {
//        compteDto.setGroupeuserId(compte.getGroupeUsers().getId());
//    }


    private Compte convertToEntity(CompteDto compteDto) {
        Compte compte = new Compte();
        compte.setId(compteDto.getId());
        compte.setNumeroCompte(compteDto.getNumeroCompte());
        compte.setBalance(compteDto.getBalance());
        compte.setInteret(compteDto.getInteret());
        compte.setBalanceDue(compteDto.getBalanceDue());
        compte.setRemboursement(compteDto.getRemboursement());

        return compte;
    }
}
