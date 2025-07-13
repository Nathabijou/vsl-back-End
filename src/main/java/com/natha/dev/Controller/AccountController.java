package com.natha.dev.Controller;

import com.natha.dev.Dto.AccountDto;
import com.natha.dev.Dto.DepositRequest;
import com.natha.dev.IService.AccountISercive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.math.BigDecimal;

@RestController
public class AccountController {

    @Autowired
    private AccountISercive accountISercive;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/user/{username}")
    List<AccountDto> compteDtos(@PathVariable String username) {
        return accountISercive.findByUserName(username);
    }

    //Create accout for user in group (Yes Verify)
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping("/deposit/{username}/{groupId}")
    public ResponseEntity<AccountDto> makeDeposit(
            @PathVariable String username,
            @PathVariable Long groupId,
            @RequestBody DepositRequest depositRequest) {
        return ResponseEntity.ok(accountISercive.makeDeposit(username, groupId, depositRequest));
    }
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping("/create/{username}/{groupId}")
    public ResponseEntity<AccountDto> createAccountForUserInGroup(
            @PathVariable String username,
            @PathVariable Long groupId,
            @RequestBody AccountDto dto) {

        AccountDto createdAccount = accountISercive.createAccountForUserInGroup(username, groupId, dto);
        return ResponseEntity.ok(createdAccount);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/user/{username}/group/{groupId}")
    public ResponseEntity<AccountDto> getAccountForUserInGroup(
            @PathVariable String username,
            @PathVariable Long groupId) {

        AccountDto account = accountISercive.findByUserNameAndGroupId(username, groupId);
        return ResponseEntity.ok(account);
    }


    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping("/account/{accountId}/status")
    public ResponseEntity<AccountDto> updateAccountStatus(
            @PathVariable String accountId,
            @RequestParam boolean active) {

        AccountDto updatedAccount = accountISercive.toggleAccountStatus(accountId, active);
        return ResponseEntity.ok(updatedAccount);
    }




}
