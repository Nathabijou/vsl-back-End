package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.AccountDao;
import com.natha.dev.Dao.DepositDao;
import com.natha.dev.Dto.DepositDto;
import com.natha.dev.IService.IDepositService;
import com.natha.dev.Mapper.DepositMapper;
import com.natha.dev.Model.Account;
import com.natha.dev.Model.Deposit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

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
    public List<DepositDto> getDepositsByAccount(String accountId) {
        List<Deposit> deposits = depositDao.findByAccountId(accountId);
        return deposits.stream()
                .map(depositMapper::toDto)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    @Transactional
    public DepositDto makeDeposit(String username, Long groupId, DepositDto depositDto) {
        if (depositDto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant du dépôt doit être supérieur à zéro");
        }
        
        // Find the account
        Account account = accountDao.findByGroupeUsers_Users_UserNameAndGroupeUsers_Groupe_Id(username, groupId)
                .orElseThrow(() -> new RuntimeException("Aucun compte trouvé pour l'utilisateur " + username + " dans le groupe ID " + groupId));
        
        // Update account balance
        account.setBalance(account.getBalance() != null ? account.getBalance().add(depositDto.getAmount()) : depositDto.getAmount());
        // Initialize depot if null
        if (account.getDepot() == null) {
            account.setDepot(BigDecimal.ZERO);
        }
        account.setDepot(account.getDepot().add(depositDto.getAmount()));
        
        // Create and save deposit
        Deposit deposit = depositMapper.toEntity(depositDto);
        deposit.setAccount(account);
        Deposit savedDeposit = depositDao.save(deposit);
        
        // Save the updated account
        accountDao.save(account);
        
        return depositMapper.toDto(savedDeposit);
    }
}
