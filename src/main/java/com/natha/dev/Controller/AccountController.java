package com.natha.dev.Controller;

import com.natha.dev.Dto.AccountDto;
import com.natha.dev.IService.AccountISercive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountISercive accountISercive;

    @GetMapping("/user/{username}")
    List<AccountDto> compteDtos(@PathVariable String username) {
        return accountISercive.findByUserName(username);
    }

    //Create accout for user in group (Yes Verify)
    @PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','MANAGER')")
    @PostMapping("/create/{username}/{groupId}")
    public ResponseEntity<AccountDto> createAccountForUserInGroup(
            @PathVariable String username,
            @PathVariable Long groupId,
            @RequestBody AccountDto dto) {

        AccountDto createdAccount = accountISercive.createAccountForUserInGroup(username, groupId, dto);
        return ResponseEntity.ok(createdAccount);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','MANAGER')")
    @PutMapping("/account/{accountId}/status")
    public ResponseEntity<AccountDto> updateAccountStatus(
            @PathVariable String accountId,
            @RequestParam boolean active) {

        AccountDto updatedAccount = accountISercive.toggleAccountStatus(accountId, active);
        return ResponseEntity.ok(updatedAccount);
    }




}
