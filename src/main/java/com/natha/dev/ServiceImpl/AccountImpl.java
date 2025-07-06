package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.AccountDao;
import com.natha.dev.Dao.GroupeDao;
import com.natha.dev.Dao.Groupe_UserDao;
import com.natha.dev.Dao.UserDao;
import com.natha.dev.Dto.AccountDto;
import com.natha.dev.IService.AccountISercive;
import com.natha.dev.IService.GroupeIService;

import com.natha.dev.Model.*;
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
            int part1 = random.nextInt(10000);
            int part2 = random.nextInt(10000);
            int part3 = random.nextInt(100);

            accountNumber = String.format("%04d-%04d-%02d", part1, part2, part3);
        } while (accountDao.existsByNumeroCompte(accountNumber));

        return accountNumber;
    }

    public AccountDto createAccount(Long groupeUserId, AccountDto dto) {
        Groupe_Users groupeUsers = groupeUserDao.findById(groupeUserId)
                .orElseThrow(() -> new RuntimeException("GroupeUser not found"));

        Account account = new Account();
        account.setNom(dto.getNom());
        account.setBalance(BigDecimal.ZERO); // pa mete balans la lè w ap kreye
        account.setNombreDaction(0); // menm jan
        account.setBalanceDue(BigDecimal.ZERO);
        account.setInteret(BigDecimal.ZERO);
        account.setNumeroCompte(generateUniqueAccountNumber());
        account.setGroupe_users(groupeUsers);
        account.setUserName(groupeUsers.getUsers().getUserName());
        account.setGroupeId(groupeUsers.getGroupe().getId());
        account.setCreateDate(new Date());

        Account saved = accountDao.save(account);

        dto.setIdAccount(saved.getId());
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

    public AccountDto createAccountForUserInGroup(String username, Long groupId, AccountDto dto) {
        Users user = userDao.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Groupe group = groupeDao.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Groupe not found"));

        Groupe_Users gu = groupeUserDao.findByUsersAndGroupe(user, group)
                .orElseThrow(() -> new RuntimeException("User is not on the group"));

        if (gu.getAccount() != null) {
            throw new RuntimeException("Account already exists on group");
        }

        String generatedAccountNumber = generateUniqueAccountNumber();

        Account account = new Account();
        account.setNom(dto.getNom());
        account.setBalance(BigDecimal.ZERO);
        account.setInteret(BigDecimal.ZERO);
        account.setBalanceDue(BigDecimal.ZERO);
        account.setNombreDaction(0);
        account.setCreateDate(new Date());
        account.setNumeroCompte(generatedAccountNumber);
        account.setGroupe_users(gu);
        account.setUserName(user.getUserName());
        account.setGroupeId(group.getId());

        accountDao.save(account);

        dto.setNumeroCompte(generatedAccountNumber);
        return dto;
    }

    private Account convertToEntity(AccountDto accountDto) {
        Account account = new Account();
        account.setId(accountDto.getIdAccount());
        account.setNumeroCompte(accountDto.getNumeroCompte());
        account.setBalance(accountDto.getBalance());
        account.setInteret(accountDto.getInteret());
        account.setBalanceDue(accountDto.getBalanceDue());
        return account;
    }

    @Override
    public BigDecimal calculerBalanceTotaleParUserEtGroupe(String username, Long groupId) {
        Optional<Account> optionalAccount = accountDao.findByUserNameAndGroupeUsers_Groupe_Id(username, groupId);

        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            int nombreDaction = getTotalNombreAksyonParAccount(account);
            BigDecimal prixAction = account.getGroupeUsers().getGroupe().getPrixAction();
            return prixAction.multiply(BigDecimal.valueOf(nombreDaction));
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal getMontantTotalParAccount(Account account) {
        return account.getActions().stream()
                .map(Action::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getTotalNombreAksyonParAccount(Account account) {
        return account.getActions().stream()
                .mapToInt(Action::getNombre)
                .sum();
    }

    private AccountDto convertToDto(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setIdAccount(account.getId());
        accountDto.setBalance(getMontantTotalParAccount(account));
        accountDto.setNombreDaction(getTotalNombreAksyonParAccount(account));
        accountDto.setInteret(account.getInteret());
        accountDto.setBalanceDue(account.getBalanceDue());
        accountDto.setNumeroCompte(account.getNumeroCompte());
        accountDto.setActive(account.isActive());
        return accountDto;
    }

    @Override
    public AccountDto getAccountDetails(String username, Long groupId) {
        Optional<Account> optionalAccount = accountDao.findByUserNameAndGroupeUsers_Groupe_Id(username, groupId);
        return optionalAccount.map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Account not found for user in group"));
    }
}
