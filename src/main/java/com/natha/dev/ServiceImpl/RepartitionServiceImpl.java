package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.GroupeUsersDao;
import com.natha.dev.Dto.AccountDto;
import com.natha.dev.Dto.GroupeDto;
import com.natha.dev.Dto.RepartitionDto;
import com.natha.dev.IService.AccountISercive;
import com.natha.dev.IService.GroupeIService;
import com.natha.dev.IService.IRepartitionService;
import com.natha.dev.Model.Groupe_Users;
import com.natha.dev.Model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RepartitionServiceImpl implements IRepartitionService {

    private static final Logger logger = LoggerFactory.getLogger(RepartitionServiceImpl.class);

    @Autowired
    private GroupeUsersDao groupeUsersDao;

    @Autowired
    private AccountISercive accountService;

    @Autowired
    private GroupeIService groupeService; 

    @Override
    public List<RepartitionDto> getRepartitionByGroupe(Long groupeId) {
        GroupeDto groupeDto = groupeService.findById(groupeId)
                .orElse(null);

        if (groupeDto == null) {
            logger.warn("Pa jwenn gwoup la pou ID: {}", groupeId);
            return Collections.emptyList();
        }

        final BigDecimal interetGroupe = groupeDto.getInteret() != null ? groupeDto.getInteret() : BigDecimal.ZERO;

        List<Groupe_Users> groupMembers = groupeUsersDao.findByGroupeId(groupeId);
        if (groupMembers.isEmpty()) {
            logger.warn("Pa jwenn okenn manm pou gwoup ID: {}", groupeId);
            return Collections.emptyList();
        }

        return groupMembers.stream()
                .map(member -> {
                    Users user = member.getUsers();
                    if (user == null) {
                        return null;
                    }

                    try {
                        AccountDto accountDto = accountService.findByUserNameAndGroupId(user.getUserName(), groupeId);

                        if (accountDto == null) {
                            return null;
                        }

                        RepartitionDto repartitionDto = new RepartitionDto();
                        repartitionDto.setPrenom(user.getUserFirstName());
                        repartitionDto.setNom(user.getUserLastName());
                        repartitionDto.setSexe(user.getUserSexe());
                        repartitionDto.setTelephone(user.getUserTelephone());

                        BigDecimal solde = accountDto.getSolde() != null ? accountDto.getSolde() : BigDecimal.ZERO;
                        int totalActionManm = accountDto.getTotalAction();
                        BigDecimal balanceDue = accountDto.getBalanceDue() != null ? accountDto.getBalanceDue() : BigDecimal.ZERO;

                        repartitionDto.setTotalAction(totalActionManm);
                        repartitionDto.setSolde(solde);

                        // Kalkil la ap toujou fèt
                        BigDecimal montantAToucher = (new BigDecimal(totalActionManm).multiply(interetGroupe)).add(solde);
                        repartitionDto.setMontantAToucher(montantAToucher);

                        // Tcheke si gen dèt pou mete estati a
                        if (balanceDue.compareTo(BigDecimal.ZERO) > 0) {
                            repartitionDto.setStatutPaiement("Non-Complet");
                        } else {
                            repartitionDto.setStatutPaiement("Complet");
                        }

                        return repartitionDto;

                    } catch (Exception e) {
                        logger.error("Erè pandan y ap kalkile repartition pou itilizatè {} nan gwoup {}: {}",
                                user.getUserName(), groupeId, e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
