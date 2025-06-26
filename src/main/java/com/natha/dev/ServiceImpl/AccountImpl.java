package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.AccountDao;
import com.natha.dev.Dao.GroupeDao;
import com.natha.dev.Dao.Groupe_UserDao;
import com.natha.dev.Dao.UserDao;
import com.natha.dev.Dto.AccountDto;
import com.natha.dev.IService.AccountISercive;
import com.natha.dev.IService.GroupeIService;
import com.natha.dev.Model.Account;
import com.natha.dev.Model.Groupe;
import com.natha.dev.Model.Groupe_Users;
import com.natha.dev.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AccountImpl implements AccountISercive {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private GroupeIService groupeIService;

    @Autowired
    private Groupe_UserDao groupeUserDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private GroupeDao groupeDao;

    @Override
    public Optional<AccountDto> findById(String accountId) {
        Optional<Account> compte = accountDao.findById(accountId);
        return compte.map(this::convertToDto);
    }

    @Override
    public List<AccountDto> findAll() {
        List<Account> accounts = accountDao.findAll();
        return accounts.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteById(String accountId) {
        accountDao.deleteById(accountId);

    }

    @Override
    public AccountDto save(AccountDto accountDto) {
        Account account = convertToEntity(accountDto);
        Account savedAccount = accountDao.save(account);
        return convertToDto(savedAccount);
    }

    @Override
    public List<AccountDto> findByUserName(String username) {
        Optional<Account> compte = accountDao.findByUserName(username);
        return compte.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private String generateUniqueAccountNumber() {
        Random random = new Random();
        String accountNumber;

        do {
            int part1 = random.nextInt(10000); // 0000–9999
            int part2 = random.nextInt(10000); // 0000–9999
            int part3 = random.nextInt(100);   // 00–99

            accountNumber = String.format("%04d-%04d-%02d", part1, part2, part3);

        } while (accountDao.existsByNumeroCompte(accountNumber));

        return accountNumber;
    }
    public AccountDto createAccount(Long groupeUserId, AccountDto dto) {
        Groupe_Users groupeUsers = groupeUserDao.findById(groupeUserId)
                .orElseThrow(() -> new RuntimeException("GroupeUser not found"));

        Account account = new Account();
        account.setNom(dto.getNom());
        account.setBalance(dto.getBalance());
        account.setNombreDaction(dto.getNombreDaction());
        account.setBalanceDue(BigDecimal.ZERO);
        account.setInteret(BigDecimal.ZERO);
        account.setNumeroCompte(generateUniqueAccountNumber()); // 👈 mete li la
        account.setGroupe_users(groupeUsers);
        account.setUserName(groupeUsers.getUsers().getUserName());
        account.setGroupeId(groupeUsers.getGroupe().getId());
        account.setCreateDate(new Date());

        Account saved = accountDao.save(account);

        dto.setIdAccount(saved.getIdAccount());
        dto.setNumeroCompte(saved.getNumeroCompte());
        return dto;
    }

    public AccountDto toggleAccountStatus(String accountId, boolean status) {
        Account account = accountDao.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not Found"));

        account.setActive(status);
        Account saved = accountDao.save(account);
        return convertToDto(saved);
    }

    private AccountDto convertToDto(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setIdAccount(account.getIdAccount());
        accountDto.setBalance(account.getBalance());
        accountDto.setInteret(account.getInteret());
        accountDto.setBalanceDue(account.getBalanceDue());
        accountDto.setNumeroCompte(account.getNumeroCompte());
        accountDto.setActive(account.isActive());  // ajoute sa
        return accountDto;
    }



    public AccountDto createAccountForUserInGroup(String username, Long groupId, AccountDto dto) {
        // 1. Verifye si user egziste
        Users user = userDao.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Verifye si group egziste
        Groupe group = groupeDao.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Groupe not found"));

        // 3. Verifye si user fè pati group lan (Groupe_Users)
        Groupe_Users gu = groupeUserDao.findByUsersAndGroupe(user, group)
                .orElseThrow(() -> new RuntimeException("User is not on the group"));

        // 4. Verifye si kont deja egziste pou Groupe_Users sa a
        if (gu.getAccount() != null) {
            throw new RuntimeException("Account already exist on group");
        }

        // 5. Jenere nimewo kont inik
        String generatedAccountNumber = generateUniqueAccountNumber();

        // 6. Kreye kont lan
        Account account = new Account();
        account.setNom(dto.getNom());
        account.setBalance(dto.getBalance());
        account.setInteret(dto.getInteret());
        account.setBalanceDue(BigDecimal.ZERO);
        account.setNombreDaction(dto.getNombreDaction());
        account.setCreateDate(new Date());
        account.setNumeroCompte(generatedAccountNumber);
        account.setGroupe_users(gu);
        account.setUserName(user.getUserName());
        account.setGroupeId(group.getId());

        accountDao.save(account);

        dto.setNumeroCompte(generatedAccountNumber); // mete nimewo kont nan DTO a
        return dto;
    }





    private Account convertToEntity(AccountDto accountDto) {
        Account account = new Account();
        account.setIdAccount(accountDto.getIdAccount());
        account.setNumeroCompte(accountDto.getNumeroCompte());
        account.setBalance(accountDto.getBalance());
        account.setInteret(accountDto.getInteret());
        account.setBalanceDue(accountDto.getBalanceDue());

        return account;
    }


}
