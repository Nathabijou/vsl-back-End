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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DepositController {

    private static final Logger logger = LoggerFactory.getLogger(DepositController.class);
    
    @Autowired
    private IDepositService depositService;

    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Manager')")
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

    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Manager')")
    @GetMapping("/deposits/account/{accountId}")
    public ResponseEntity<Map<String, Object>> getDepositsByAccount(
            @PathVariable String accountId,
            Authentication authentication) {
        
        logger.info("Fetching deposits for account: " + accountId);
        logger.info("Requested by user: " + authentication.getName());
        logger.info("User roles: " + authentication.getAuthorities());
        
        return ResponseEntity.ok(depositService.getDepositsByAccount(accountId));
    }
    
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Manager')")
    @GetMapping("/deposits/{username}/group/{groupId}")
    public ResponseEntity<Map<String, Object>> getDepositsByUserAndGroup(
            @PathVariable String username,
            @PathVariable Long groupId,
            Authentication authentication) {
        
        // Log detay otantifikasyon an
        logger.info("=== Détails de l'authentification ===");
        logger.info("Nom d'utilisateur: {}", authentication.getName());
        logger.info("Rôles: {}", authentication.getAuthorities());
        logger.info("Authentifié: {}", authentication.isAuthenticated());
        
        // Log detay demann lan
        logger.info("Récupération des dépôts pour l'utilisateur: {} dans le groupe: {}", username, groupId);
        
        try {
            Map<String, Object> result = depositService.getDepositsByUserAndGroup(username, groupId);
            logger.info("Dépôts récupérés avec succès");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des dépôts", e);
            throw e;
        }
    }
    
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Manager')")
    @PutMapping("/deposits/{depositId}")
    public ResponseEntity<DepositDto> updateDeposit(
            @PathVariable String depositId,
            @RequestBody DepositDto depositDto,
            Authentication authentication) {
        
        logger.info("Updating deposit with ID: {}", depositId);
        logger.info("Requested by: {}", authentication.getName());
        
        return ResponseEntity.ok(depositService.updateDeposit(depositId, depositDto, authentication.getName()));
    }
    
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Manager')")
    @DeleteMapping("/deposits/{depositId}")
    public ResponseEntity<Void> deleteDeposit(
            @PathVariable String depositId,
            Authentication authentication) {
        
        logger.info("Deleting deposit with ID: {}", depositId);
        logger.info("Requested by: {}", authentication.getName());
        
        depositService.deleteDeposit(depositId);
        return ResponseEntity.noContent().build();
    }
    
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Manager')")
    @GetMapping("/accounts/{username}/group/{groupId}/summary")
    public ResponseEntity<Map<String, Object>> getAccountSummary(
            @PathVariable String username,
            @PathVariable Long groupId,
            Authentication authentication) {
        
        logger.info("Récupération du récapitulatif du compte pour l'utilisateur: {} dans le groupe: {}", username, groupId);
        logger.info("Demandé par: {}", authentication.getName());
        
        // Récupérer les informations des dépôts
        Map<String, Object> depositsInfo = depositService.getDepositsByUserAndGroup(username, groupId);
        
        // Créer le récapitulatif
        Map<String, Object> summary = new HashMap<>();
        
        // Total des dépôts
        BigDecimal totalDeposits = (BigDecimal) depositsInfo.get("totalAmount");
        summary.put("totalDeposits", totalDeposits);
        
        // Compter les dépôts
        @SuppressWarnings("unchecked")
        List<DepositDto> deposits = (List<DepositDto>) depositsInfo.get("deposits");
        int numberOfDeposits = deposits != null ? deposits.size() : 0;
        summary.put("numberOfDeposits", numberOfDeposits);
        
        // Ajouter d'autres informations utiles
        summary.put("username", username);
        summary.put("groupId", groupId);
        
        return ResponseEntity.ok(summary);
    }
}
