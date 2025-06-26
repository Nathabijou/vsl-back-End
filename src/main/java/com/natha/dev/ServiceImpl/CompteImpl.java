package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.CompteDao;
import com.natha.dev.Dao.GroupeDao;
import com.natha.dev.Dao.Groupe_UserDao;
import com.natha.dev.Dao.UserDao;
import com.natha.dev.Dto.CompteDto;
import com.natha.dev.IService.CompteISercive;
import com.natha.dev.IService.GroupeIService;
import com.natha.dev.Model.Compte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        compteDto.setNumeroCompte(compte.getNumeroCompte());

        return compteDto;
    }

    private Compte convertToEntity(CompteDto compteDto) {
        Compte compte = new Compte();
        compte.setId(compteDto.getId());
        compte.setNumeroCompte(compteDto.getNumeroCompte());
        compte.setBalance(compteDto.getBalance());
        compte.setInteret(compteDto.getInteret());
        compte.setBalanceDue(compteDto.getBalanceDue());

        return compte;
    }
}
