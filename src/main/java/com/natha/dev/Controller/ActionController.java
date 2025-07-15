package com.natha.dev.Controller;

import com.natha.dev.Dao.AccountDao;
import com.natha.dev.Dao.ActionDao;
import com.natha.dev.Dto.ActionDto;
import com.natha.dev.IService.ActionIService;
import com.natha.dev.Model.Account;
import com.natha.dev.Model.Action;  // asire sa la
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController

public class ActionController {

    @Autowired
    private ActionIService actionIService;
    @Autowired
    private ActionDao actionDao;

    @Autowired
    private AccountDao accountDao;

    //Create Action For user in account
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Manager')")
    @PostMapping("/actions/byAction")
    public ResponseEntity<Action> acheterAction(@RequestBody ActionDto actionDto) {
        // 1. Rechèch kont lan
        Account account = accountDao.findById(actionDto.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // 2. Kalkile kantite ak montan pou nouvo aksyon
        int nombre = actionDto.getNombre();
        BigDecimal prixAction = account.getGroupeUsers().getGroupe().getPrixAction();
        BigDecimal montant = prixAction.multiply(BigDecimal.valueOf(nombre));

        // 3. Kreye Aksyon an
        Action action = new Action();
        action.setNombre(nombre);
        action.setMontant(montant);
        action.setAccount(account);

        // 4. Ajoute aksyon sa a nan total account la
        Integer totalAncien = account.getNombreDaction();
        BigDecimal balanceAncien = account.getBalance() != null ? account.getBalance() : BigDecimal.ZERO;

        int nouveauTotal = (totalAncien != null ? totalAncien : 0) + nombre;
        account.setNombreDaction(nouveauTotal);
        account.setBalance(balanceAncien.add(montant));

        // 5. Sove nouvo valè yo
        accountDao.save(account); // mete ajou kont lan
        Action saved = actionIService.save(action); // mete aksyon an

        return ResponseEntity.ok(saved);
    }


    //Get all action with account (ok)
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Manager','ROLE_User')")
    @GetMapping("/actions/par-compte/{accountId}")
    public ResponseEntity<List<Action>> getActionsByAccount(@PathVariable Long accountId) {
        List<Action> actions = actionDao.findByAccount_Id(accountId);
        return ResponseEntity.ok(actions);
    }

    //Update Action
    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Manager')")
    @PutMapping("/actions/update/{id}")
    public ResponseEntity<Action> updateNombreAction(@PathVariable Long id, @RequestBody ActionDto actionDto) {
        Optional<Action> existingAction = actionIService.findById(id);
        if (existingAction.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Action action = existingAction.get();
        action.setNombre(actionDto.getNombre());

        // Re-kalkile montant lan si gen prixAction
        BigDecimal prixAction = action.getAccount().getGroupeUsers().getGroupe().getPrixAction();
        action.setMontant(prixAction.multiply(BigDecimal.valueOf(actionDto.getNombre())));

        Action updated = actionIService.update(action);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_Admin', 'ROLE_Manager')")
    @DeleteMapping("/actions/delete/{id}")
    public ResponseEntity<Void> deleteAction(@PathVariable Long id) {
        try {
            actionIService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }



}
