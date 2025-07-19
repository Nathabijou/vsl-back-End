package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.GroupeUsersDao;
import com.natha.dev.Dto.AccountDto;
import com.natha.dev.Dto.RepartitionDto;
import com.natha.dev.IService.AccountISercive;
import com.natha.dev.IService.IRepartitionService;
import com.natha.dev.Model.Groupe_Users;
import com.natha.dev.Model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RepartitionServiceImpl implements IRepartitionService {

    private static final Logger logger = LoggerFactory.getLogger(RepartitionServiceImpl.class);

    @Autowired
    private GroupeUsersDao groupeUsersDao;

    @Autowired
    private AccountISercive accountService; // Sèvi ak sèvis kont lan dirèkteman

    @Override
    public List<RepartitionDto> getRepartitionByGroupe(Long groupeId) {
        logger.info("Kòmanse kalkil repartition pou gwoup ID: {}", groupeId);
        List<Groupe_Users> groupMembers = groupeUsersDao.findByGroupeId(groupeId);

        if (groupMembers.isEmpty()) {
            logger.warn("Pa jwenn okenn manm pou gwoup ID: {}", groupeId);
        }

        return groupMembers.stream()
                .map(member -> {
                    Users user = member.getUsers();
                    if (user == null) {
//                        logger.error("Itilizatè se null pou yon manm nan gwoup ID: {}", groupeId);
                        return null;
                    }

                    try {
                        // Jwenn AccountDto ki gen tout kalkil yo ki deja fèt
                        AccountDto accountDto = accountService.findByUserNameAndGroupId(user.getUserName(), groupeId);

                        if (accountDto == null) {
//                            logger.warn("Pa jwenn AccountDto pou itilizatè {} nan gwoup {}", user.getUserName(), groupeId);
                            return null;
                        }

                        RepartitionDto repartitionDto = new RepartitionDto();
                        repartitionDto.setPrenom(user.getUserFirstName());
                        repartitionDto.setNom(user.getUserLastName()); // Sèvi ak non fanmi itilizatè a
                        repartitionDto.setSexe(user.getUserSexe());
                        repartitionDto.setTelephone(user.getUserTelephone());

                        // Pran valè ki deja kalkile yo
                        BigDecimal solde = accountDto.getSolde() != null ? accountDto.getSolde() : BigDecimal.ZERO;
                        BigDecimal monInteret = BigDecimal.valueOf(accountDto.getMonInteret());

                        // Ranpli nouvo chan yo
                        repartitionDto.setTotalAction(accountDto.getTotalAction());
                        repartitionDto.setSolde(solde);

                        BigDecimal montantAToucher = solde.add(monInteret);

                        logger.info("",
                                user.getUserFirstName(), user.getUserLastName(), solde, monInteret, montantAToucher);

                        repartitionDto.setMontantAToucher(montantAToucher);
                        return repartitionDto;

                    } catch (Exception e) {
                        logger.error(" nan gwoup {}: {}",
                                user.getUserName(), groupeId, e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
