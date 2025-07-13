package com.natha.dev.Controller;

import com.natha.dev.Dto.DepositDto;
import com.natha.dev.IService.IDepositService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DepositController {

    private static final Logger logger = LoggerFactory.getLogger(DepositController.class);
    
    @Autowired
    private IDepositService depositService;

    @PreAuthorize("hasAnyRole('ROLE_Admin', 'ROLE_MANAGER')")
    @PostMapping("/deposits/{username}/group/{groupId}")
    public ResponseEntity<DepositDto> makeDeposit(
            @PathVariable String username,
            @PathVariable Long groupId,
            @RequestBody DepositDto depositDto,
            Authentication authentication) {
        
        logger.info("User: " + authentication.getName() + " is making a deposit");
        logger.info("User roles: " + authentication.getAuthorities());
        
        return ResponseEntity.ok(depositService.makeDeposit(username, groupId, depositDto));
    }

    @PreAuthorize("hasAnyRole('ROLE_Admin', 'ROLE_MANAGER')")
    @GetMapping("/deposits/account/{accountId}")
    public ResponseEntity<List<DepositDto>> getDepositsByAccount(
            @PathVariable String accountId,
            Authentication authentication) {
        
        logger.info("Fetching deposits for account: " + accountId);
        logger.info("Requested by user: " + authentication.getName());
        logger.info("User roles: " + authentication.getAuthorities());
        
        return ResponseEntity.ok(depositService.getDepositsByAccount(accountId));
    }
}
