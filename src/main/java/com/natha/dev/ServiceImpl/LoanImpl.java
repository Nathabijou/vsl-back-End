package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.AccountDao;
import com.natha.dev.Dao.LoanDao;
import com.natha.dev.IService.LoanIService;
import com.natha.dev.Model.Account;
import com.natha.dev.Model.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LoanImpl implements LoanIService {

    @Autowired
    private LoanDao loanDao;

    @Autowired
    private AccountDao accountDao;

    public Loan createLoan(String accountId, Loan loan) {
        Long id = Long.parseLong(accountId); // konvèti String pou Long
        Account account = accountDao.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account pa jwenn"));

        loan.setIdLoan(UUID.randomUUID().toString());
        loan.setAccount(account);
        loan.setStatus("ACTIVE");

        return loanDao.save(loan);
    }


    @Override
    public List<Loan> getLoansByAccount(String accountId) {
        return loanDao.findByAccountIdAccount(accountId);
    }

    @Override
    public Loan updateLoan(String idLoan, Loan loan) {
        Loan existingLoan = loanDao.findById(idLoan)
                .orElseThrow(() -> new RuntimeException("Loan pa jwenn"));
        existingLoan.setPrincipalAmount(loan.getPrincipalAmount());
        existingLoan.setInterestRate(loan.getInterestRate());
        existingLoan.setStartDate(loan.getStartDate());
        existingLoan.setDueDate(loan.getDueDate());
        existingLoan.setStatus(loan.getStatus());
        return loanDao.save(existingLoan);
    }

    @Override
    public void deleteLoan(String idLoan) {
        loanDao.deleteById(idLoan);
    }
}
