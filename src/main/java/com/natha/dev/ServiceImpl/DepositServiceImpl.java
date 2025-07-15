package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.AccountDao;
import com.natha.dev.Dao.DepositDao;
import com.natha.dev.Dto.DepositDto;
import com.natha.dev.IService.IDepositService;
import com.natha.dev.Mapper.DepositMapper;
import com.natha.dev.Model.Account;
import com.natha.dev.Model.Deposit;
import com.natha.dev.Model.Groupe;
import com.natha.dev.Model.Groupe_Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class DepositServiceImpl implements IDepositService {

    @Autowired
    private DepositDao depositDao;
    
    @Autowired
    private AccountDao accountDao;
    
    @Autowired
    private DepositMapper depositMapper;

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getDepositsByAccount(String accountId) {
        // Convert String accountId to Long
        Long accountIdLong = Long.parseLong(accountId);
        List<Deposit> deposits = depositDao.findByAccountId(accountIdLong);
        
        // Calculate total deposit amount
        BigDecimal totalAmount = deposits.stream()
                .map(Deposit::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Convert deposits to DTOs
        List<DepositDto> depositDtos = deposits.stream()
                .map(depositMapper::toDto)
                .collect(java.util.stream.Collectors.toList());
        
        // Create response map
        Map<String, Object> response = new HashMap<>();
        response.put("deposits", depositDtos);
        response.put("totalAmount", totalAmount);
        
        return response;
    }

    @Override
    @Transactional
    public DepositDto makeDeposit(String username, Long groupId, DepositDto depositDto) {
        // Verify number of shares is provided and valid
        if (depositDto.getNumberOfShares() == null || depositDto.getNumberOfShares() <= 0) {
            throw new IllegalArgumentException("Kantite aksyon an dwe pi gran pase zewo");
        }
        
        // Get the account with group information
        Account account = accountDao.findByGroupeUsers_Users_UserNameAndGroupeUsers_Groupe_Id(username, groupId)
                .orElseThrow(() -> new RuntimeException("Pa gen kont pou itilizatè " + username + " nan gwoup ID " + groupId));
                
        // Get the group to get the share price
        Groupe groupe = null;
        if (account != null && account.getGroupeUsers() != null) {
            groupe = account.getGroupeUsers().getGroupe();
        }
        if (groupe == null) {
            throw new RuntimeException("Groupe not found");
        }
        
        // Calculate amount based on number of shares and share price
        BigDecimal sharePrice = groupe.getPrixAction();
        BigDecimal amount = sharePrice.multiply(BigDecimal.valueOf(depositDto.getNumberOfShares()));
        
        // Update the deposit DTO with the calculated amount
        depositDto.setAmount(amount);
        
        // Get the deposit amount
        BigDecimal montanDepo = depositDto.getAmount();
        System.out.println("La balance n'est pas affectée par les dépôts");
        
        // Create and save deposit
        Deposit depo = new Deposit();
        depo.setAmount(montanDepo);
        depo.setDepositDate(LocalDateTime.now());
        depo.setAccount(account);
        depo.setNumberOfShares(depositDto.getNumberOfShares());
        depo.setDescription(depositDto.getDescription());

        depo.setCreateBy(username); // Anrejistre itilizatè ki fè depo a
        
        // Save the deposit
        Deposit depoSove = depositDao.save(depo);

        // Convert to DTO and return
        return depositMapper.toDto(depoSove);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getDepositsByUserAndGroup(String username, Long groupId) {
        // Find the account for the user in the specified group
        Account account = accountDao.findByGroupeUsers_Users_UserNameAndGroupeUsers_Groupe_Id(username, groupId)
                .orElseThrow(() -> new RuntimeException("Aucun compte trouvé pour l'utilisateur " + username + " dans le groupe ID " + groupId));
        
        // Get all deposits for this account
        List<Deposit> deposits = depositDao.findByAccountId(account.getId());
        
        // Calculate total deposit amount
        BigDecimal totalAmount = deposits.stream()
                .map(Deposit::getAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Convert deposits to DTOs
        List<DepositDto> depositDtos = deposits.stream()
                .map(depositMapper::toDto)
                .collect(java.util.stream.Collectors.toList());
        
        // Create response map
        Map<String, Object> response = new HashMap<>();
        response.put("deposits", depositDtos);
        response.put("totalAmount", totalAmount);
        response.put("accountId", account.getId());
        response.put("username", username);
        response.put("groupId", groupId);
        
        return response;
    }
    
    @Override
    @Transactional
    public DepositDto updateDeposit(String depositId, DepositDto depositDto, String modifiedBy) {
        // Jwenn depo ki egziste a
        Deposit existingDeposit = depositDao.findById(depositId)
                .orElseThrow(() -> new RuntimeException("Deposit not found with id: " + depositId));

        // Jwenn kont lan ak gwoup la pou pri aksyon an
        Account account = existingDeposit.getAccount();
        Groupe groupe = account.getGroupeUsers().getGroupe();
        BigDecimal sharePrice = groupe.getPrixAction();

        // Mete ajou kantite aksyon
        existingDeposit.setNumberOfShares(depositDto.getNumberOfShares());
        existingDeposit.setDescription(depositDto.getDescription());

        // Rekalkile montan depo a baze sou nouvo kantite aksyon an
        BigDecimal newAmount = sharePrice.multiply(BigDecimal.valueOf(depositDto.getNumberOfShares()));
        existingDeposit.setAmount(newAmount);

        // Anrejistre enfòmasyon sou modifikasyon an
        existingDeposit.setLastModifiedBy(modifiedBy);
        existingDeposit.setLastModifiedDate(LocalDateTime.now());

        // Sove chanjman yo san yo pa manyen balans kont lan
        Deposit updatedDeposit = depositDao.save(existingDeposit);

        return depositMapper.toDto(updatedDeposit);
    }

    @Override
    @Transactional
    public void deleteDeposit(String id) {
        // Find the deposit first to ensure it exists before deleting
        if (!depositDao.findById(id).isPresent()) {
            throw new RuntimeException("Dépôt non trouvé avec l'ID: " + id);
        }
        depositDao.deleteById(id);
    }
}
