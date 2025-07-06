package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.AccountDao;
import com.natha.dev.Dao.ActionDao;
import com.natha.dev.IService.ActionIService;
import com.natha.dev.Model.Account;
import com.natha.dev.Model.Action;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ActionImpl implements ActionIService {
    @Autowired
    private ActionDao actionDao;

    @Autowired
    private AccountDao accountDao;

    @Override
    public Action save(Action action) {
        return actionDao.save(action);
    }

    @Override
    public List<Action> findAllByAccountId(String accountId) {
        return actionDao.findByAccount_Id(Long.valueOf(accountId));
    }

    @Override
    @Transactional
    public Action update(Action action) {
        Optional<Action> existingOpt = actionDao.findById(action.getId());
        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Action not found with ID: " + action.getId());
        }

        Action existing = existingOpt.get();

        // Modifye aksyon an (nombre ak montant)
        existing.setNombre(action.getNombre());
        BigDecimal prixAction = existing.getAccount().getGroupeUsers().getGroupe().getPrixAction();
        existing.setMontant(prixAction.multiply(BigDecimal.valueOf(action.getNombre())));

        // Save aksyon modifye an premye
        Action updatedAction = actionDao.save(existing);

        // Rekipere kont lan
        Account account = updatedAction.getAccount();

        // Rekipere tout aksyon pou kont lan
        List<Action> actionsOfAccount = actionDao.findByAccount_Id(account.getId());

        // Kalkile total nombre ak balans soti nan aksyon sa yo
        int totalNombre = actionsOfAccount.stream()
                .mapToInt(Action::getNombre)
                .sum();

        BigDecimal totalBalance = actionsOfAccount.stream()
                .map(Action::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Mete ajou account lan
        account.setNombreDaction(totalNombre);
        account.setBalance(totalBalance);

        // Save kont lan
        accountDao.save(account);

        return updatedAction;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<Action> actionOpt = actionDao.findById(id);
        if (actionOpt.isEmpty()) {
            throw new RuntimeException("Action not found with ID: " + id);
        }

        Action action = actionOpt.get();
        Account account = action.getAccount();

        BigDecimal prixAction = account.getGroupeUsers().getGroupe().getPrixAction();
        int nombre = action.getNombre();

        // Retire kantite aksyon ak montan nan kont lan
        int oldTotalNombre = account.getNombreDaction() != null ? account.getNombreDaction() : 0;
        BigDecimal oldBalance = account.getBalance() != null ? account.getBalance() : BigDecimal.ZERO;

        account.setNombreDaction(oldTotalNombre - nombre);
        account.setBalance(oldBalance.subtract(prixAction.multiply(BigDecimal.valueOf(nombre))));

        // Mete ajou kont lan
        accountDao.save(account);

        // Efase action an
        actionDao.deleteById(id);
    }





    @Override
    public Optional<Action> findById(Long id) {
        return actionDao.findById(id);
    }
}
