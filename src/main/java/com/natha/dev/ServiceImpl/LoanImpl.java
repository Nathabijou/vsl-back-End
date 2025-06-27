package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.AccountDao;
import com.natha.dev.Dao.LoanDao;
import com.natha.dev.Dao.RefundDao;
import com.natha.dev.IService.LoanIService;
import com.natha.dev.Model.Account;
import com.natha.dev.Model.Loan;
import com.natha.dev.Model.Refund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoanImpl implements LoanIService {

    @Autowired
    private LoanDao loanDao;

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private RefundDao refundDao;

    @Override
    public Loan save(Loan loan) {
        return loanDao.save(loan);
    }


    @Override
    public List<Loan> findByAccountId(String accountId) {
        return loanDao.findByAccount_Id(accountId);
    }

    @Override
    public Optional<Loan> findByIdWithRefunds(String idLoan) {
        return loanDao.findByIdWithRefunds(idLoan);
    }


    @Override
    public Optional<Loan> findById(String idLoan) {
        return loanDao.findById(idLoan);
    }

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
    public BigDecimal calculateBalanceDue(Loan loan) {
        // Asire nou gen tout refunds an chajman
        loan = findByIdWithRefunds(loan.getIdLoan())
                .orElseThrow(() -> new RuntimeException("Loan not found with refunds"));

        BigDecimal principal = loan.getPrincipalAmount(); // Egzanp: 2000
        BigDecimal interestRate = loan.getInterestRate().divide(BigDecimal.valueOf(100)); // 5% = 0.05

        LocalDate start = loan.getStartDate();
        LocalDate now = LocalDate.now();

        // Kalkile kantite mwa depi kòmansman prè a
        long monthsElapsed = ChronoUnit.MONTHS.between(start.withDayOfMonth(1), now.withDayOfMonth(1));
        if (monthsElapsed < 1) monthsElapsed = 1;

        BigDecimal monthlyInterest = principal.multiply(interestRate);
        BigDecimal totalInterest = monthlyInterest.multiply(BigDecimal.valueOf(monthsElapsed));

        BigDecimal totalDebt = principal.add(totalInterest);

        // Kalkile total remboursement (refunds)
        BigDecimal totalRefund = loan.getRefunds() == null ? BigDecimal.ZERO :
                loan.getRefunds()
                        .stream()
                        .map(Refund::getAmountRefunded)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal balanceDue = totalDebt.subtract(totalRefund);

        return balanceDue.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : balanceDue;
    }








    @Override
    public List<Loan> getLoansByAccount(String accountId) {
        return loanDao.findByAccount_Id(String.valueOf(Long.valueOf(accountId)));
    }

    @Override
    public Loan updateLoan(String idLoan, Loan loan) {
        Loan existingLoan = loanDao.findById(idLoan)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
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


    public void processRefund(Refund refund) {
        // Sove Refund lan
        refundDao.save(refund);

        // Resevwa Loan ki asosye a
        Loan loan = refund.getLoan();
        Account account = loan.getAccount();

        // Rekalkile balance due a
        BigDecimal updatedBalanceDue = calculateBalanceDue(loan);

        // Mete balanceDue a sou Account lan epi sove li
        account.setBalanceDue(updatedBalanceDue);
        accountDao.save(account);
    }

}
