package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.*;
import com.natha.dev.Dto.AccountDto;
import com.natha.dev.Dto.DepositDto;
import com.natha.dev.Dto.DepositRequest;
import com.natha.dev.IService.AccountISercive;
import com.natha.dev.IService.GroupeIService;

import com.natha.dev.Model.*;
import com.natha.dev.Dao.DepositRepository;
import com.natha.dev.Model.Deposit;
import com.natha.dev.Model.Groupe_Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;
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
    private Groupe_UserDao groupe_UserDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private GroupeDao groupeDao;
    
    @Autowired
    private DepositRepository depositRepository;

    @Override
    public AccountDto findByUserNameAndGroupId(String username, Long groupId) {
        Optional<Account> optional = accountDao.findByGroupeUsers_Users_UserNameAndGroupeUsers_Groupe_Id(username, groupId);

        if (optional.isPresent()) {
            Account account = optional.get();
            // Konvèti an DTO kote tout kalkil kòrèk yo fèt
            return convertToDto(account);
        } else {
            throw new RuntimeException("Aucun compte trouvé pour l'utilisateur " + username + " dans le groupe ID " + groupId);
        }
    }

    @Override
    @Transactional
    public AccountDto makeDeposit(String username, Long groupId, DepositRequest depositRequest) {

        if (depositRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Montan an dwe pi gran pase zewo");
        }
        
        // Jwenn kont lan
        Account account = accountDao.findByGroupeUsers_Users_UserNameAndGroupeUsers_Groupe_Id(username, groupId)
                .orElseThrow(() -> new RuntimeException("Pa gen kont pou itilizatè " + username + " nan gwoup ID " + groupId));


        // Kalkile nouvo valè yo
        BigDecimal nouvoDepo = account.getDepot() != null ?
                             account.getDepot().add(depositRequest.getAmount()) :
                             depositRequest.getAmount();

        // Mete ajou kont lan
        // Balans lan PA dwe mete ajou isit la. Se sèlman depo a ki dwe anrejistre.
        account.setDepot(nouvoDepo);

        // Sove modifikasyon yo
        Account updatedAccount = accountDao.save(account);

        return convertToDto(updatedAccount);
    }


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
        Groupe_Users groupeUser = groupe_UserDao.findById(groupeUserId)
                .orElseThrow(() -> new RuntimeException("GroupeUser not found"));

        Account account = new Account();
        account.setNumeroCompte(generateUniqueAccountNumber());
        account.setGroupeUsers(groupeUser);
        account.setNombreDaction(dto.getNombreDaction());
        account.setInteret(BigDecimal.valueOf(dto.getInteret()));
        account.setBalance(dto.getBalance());
        account.setBalanceDue(dto.getBalanceDue());
        account.setActive(true);

        accountDao.save(account);
        dto.setIdAccount(account.getId());
        dto.setNumeroCompte(account.getNumeroCompte());
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

        Groupe_Users gu = groupe_UserDao.findByUsersAndGroupe(user, group)
                .orElseThrow(() -> new RuntimeException("User is not on the group"));

        if (gu.getAccount() != null) {
            throw new RuntimeException("Account already exists on group");
        }

        String generatedAccountNumber = generateUniqueAccountNumber();

        Account account = new Account();
        account.setNom(dto.getNom());
        account.setBalance(BigDecimal.ZERO);
        account.setBalanceDue(BigDecimal.ZERO);
        account.setInteret(BigDecimal.valueOf(dto.getInteret())); 
        account.setNombreDaction(0);
        account.setCreateDate(new Date());
        account.setNumeroCompte(generatedAccountNumber);
        account.setGroupeUsers(gu);
        account.setUserName(user.getUserName());
        account.setGroupeId(group.getId());

        Account savedAccount = accountDao.save(account);
        gu.setAccount(savedAccount);
        groupe_UserDao.save(gu);

        return convertToDto(savedAccount);
    }

    private Account convertToEntity(AccountDto accountDto) {
        Account account = new Account();
        account.setId(accountDto.getIdAccount());
        account.setNumeroCompte(accountDto.getNumeroCompte());
        account.setBalance(accountDto.getBalance());
        account.setInteret(BigDecimal.valueOf(accountDto.getInteret()));
        account.setBalanceDue(accountDto.getBalanceDue());
        account.setDepot(accountDto.getDepot() != null ? accountDto.getDepot() : BigDecimal.ZERO);
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
        // Si gen aksyon nan kont lan, nou kalkile yo
        if (account.getActions() != null && !account.getActions().isEmpty()) {
            return account.getActions().stream()
                    .mapToInt(Action::getNombre)
                    .sum();
        }
        
        // Si pa gen aksyon, nou retounen 0
        return 0;
    }
    
    // Metòd pou kalkile total lajan nan tout depo yo
    private BigDecimal getTotalDepositsAmount(Account account) {
        if (account.getDeposits() != null && !account.getDeposits().isEmpty()) {
            return account.getDeposits().stream()
                    .map(Deposit::getAmount)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        return BigDecimal.ZERO;
    }
    
    // Metòd pou konte kantite depo total yo
    private int countTotalDeposits(Account account) {
        if (account.getDeposits() != null) {
            return account.getDeposits().size();
        }
        return 0;
    }

    private AccountDto convertToDto(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setIdAccount(account.getId());

        // 1. Kalkile kantite aksyon reyèl yon sèl fwa
        int nombreActions = getTotalNombreAksyonParAccount(account);
        accountDto.setNombreDaction(nombreActions);

        // 2. Kalkile valè aksyon yo (sa se "balance" reyèl la)
        BigDecimal calculatedBalance = BigDecimal.ZERO;
        if (account.getGroupeUsers() != null && account.getGroupeUsers().getGroupe() != null) {
            BigDecimal prixAction = account.getGroupeUsers().getGroupe().getPrixAction();
            if (prixAction != null) {
                calculatedBalance = prixAction.multiply(BigDecimal.valueOf(nombreActions));
            }
        }
        accountDto.setBalance(calculatedBalance); // BALANCE = Valè aksyon sèlman

        // 3. Kalkile total lajan ki te depoze
        BigDecimal totalDepositsAmount = getTotalDepositsAmount(account);
        accountDto.setDepot(totalDepositsAmount); // DEPOT = Total depo sèlman

        // 4. Nouvo fòmil pou SOLDE TOTAL: Balans + Depo
        BigDecimal finalSolde = calculatedBalance.add(totalDepositsAmount);
        accountDto.setSolde(finalSolde); // SOLDE = Sòm de lòt yo

        // 5. Sèvi ak metòd ki kòrèk la pou kalkile kantite total aksyon yo
        accountDto.setTotalAction(account.getTotalAction());

        // 6. Kalkile enterè pèsonèl manm nan (MonInteret)
        int monInteret = 0;
        if (account.getGroupeUsers() != null && account.getGroupeUsers().getGroupe() != null) {
            int interetDuGroupe = account.getGroupeUsers().getGroupe().getInteret();
            monInteret = account.getTotalAction() * interetDuGroupe;
        }
        accountDto.setMonInteret(monInteret);

        // Lòt enfòmasyon
        if (account.getInteret() != null) {
            accountDto.setInteret(account.getInteret().intValue());
        }
        accountDto.setBalanceDue(account.getBalanceDue());
        accountDto.setNumeroCompte(account.getNumeroCompte());
        accountDto.setActive(account.isActive());

        // 5. Kalkile sòm total aksyon ki soti nan depo yo
        int totalSharesFromDeposits = 0;
        if (account.getDeposits() != null) {
            totalSharesFromDeposits = account.getDeposits().stream()
                    .map(com.natha.dev.Model.Deposit::getNumberOfShares)
                    .filter(java.util.Objects::nonNull)
                    .mapToInt(Integer::intValue)
                    .sum();
        }
        accountDto.setTotalNumberOfSharesFromDeposits(totalSharesFromDeposits);

        return accountDto;
    }

    @Override
    public AccountDto getAccountDetails(String username, Long groupId) {
        Optional<Account> optionalAccount = accountDao.findByGroupeUsers_Users_UserNameAndGroupeUsers_Groupe_Id(username, groupId);
        
        // Fòse itilizasyon convertToDto pou asire ke tout kalkil yo kòrèk
        return optionalAccount.map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Account not found for user in group"));
    }
}
