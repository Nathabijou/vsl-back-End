package com.natha.dev.Controller;

import com.natha.dev.Dto.AccountDto;
import com.natha.dev.Dto.DepositRequest;
import com.natha.dev.IService.AccountISercive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.math.BigDecimal;

@RestController
public class AccountController {
    
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountISercive accountISercive;

    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Manager')")
    @GetMapping("/user/{username}")
    public ResponseEntity<List<AccountDto>> getAccountsByUsername(@PathVariable String username) {
        logger.info("Fetching accounts for user: " + username);
        List<AccountDto> accounts = accountISercive.findByUserName(username);
        return ResponseEntity.ok(accounts);
    }

    //Create accout for user in group (Yes Verify)
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Manager')")
    @PostMapping("/deposit/{username}/{groupId}")
    public ResponseEntity<AccountDto> makeDeposit(
            @PathVariable String username,
            @PathVariable Long groupId,
            @RequestBody DepositRequest depositRequest) {
        logger.info("Processing deposit for user: " + username + " in group: " + groupId);
        AccountDto result = accountISercive.makeDeposit(username, groupId, depositRequest);
        return ResponseEntity.ok(result);
    }
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Manager')")
    @PostMapping("/create/{username}/{groupId}")
    public ResponseEntity<AccountDto> createAccountForUserInGroup(
            @PathVariable String username,
            @PathVariable Long groupId,
            @RequestBody AccountDto dto) {
        
        logger.info("Creating account for user: " + username + " in group: " + groupId);
        AccountDto createdAccount = accountISercive.createAccountForUserInGroup(username, groupId, dto);
        logger.info("Account created successfully with ID: " + createdAccount.getIdAccount());
        return ResponseEntity.ok(createdAccount);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Manager')")
    @GetMapping("/user/{username}/group/{groupId}")
    public ResponseEntity<AccountDto> getAccountForUserInGroup(
            @PathVariable String username,
            @PathVariable Long groupId) {

        logger.info("Fetching account for user: " + username + " in group: " + groupId);
        AccountDto account = accountISercive.findByUserNameAndGroupId(username, groupId);
        if (account == null) {
            logger.warn("Account not found for user: " + username + " in group: " + groupId);
        }
        return ResponseEntity.ok(account);
    }


    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Manager')")
    @PutMapping("/account/{accountId}/status")
    public ResponseEntity<AccountDto> updateAccountStatus(
            @PathVariable String accountId,
            @RequestParam boolean active) {
        
        logger.info((active ? "Activating" : "Deactivating") + " account with ID: " + accountId);
        AccountDto updatedAccount = accountISercive.toggleAccountStatus(accountId, active);
        logger.info("Account " + accountId + " status updated to " + (active ? "active" : "inactive"));
        return ResponseEntity.ok(updatedAccount);
    }




}
