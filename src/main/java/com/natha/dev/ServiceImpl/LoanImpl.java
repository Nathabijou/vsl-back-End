package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.AccountDao;
import com.natha.dev.Dao.LoanDao;
import com.natha.dev.Dao.RefundDao;
import com.natha.dev.Dto.LoanDto;
import com.natha.dev.IService.LoanIService;
import com.natha.dev.Model.Account;
import com.natha.dev.Model.Groupe;
import com.natha.dev.Model.Loan;
import com.natha.dev.Model.Refund;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoanImpl implements LoanIService {

    private static final Logger logger = LoggerFactory.getLogger(LoanImpl.class);

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

    @Override
    public Loan createLoan(String accountId, LoanDto loanDto) {
        Account account = accountDao.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Kont pa jwenn"));

        // Fòse valè sa yo pou garanti yo kòrèk
        Loan loan = new Loan();
        loan.setAccount(account);
        loan.setStartDate(LocalDate.now()); // Mete dat jounen an otomatikman
        loan.setCreationDateTime(LocalDateTime.now()); // Mete lè egzak la otomatikman
        loan.setStatus("EN_COURS");
        loan.setIdLoan(UUID.randomUUID().toString());

        // Sèvi ak done ki soti nan DTO a
        loan.setPrincipalAmount(loanDto.getPrincipalAmount());
        loan.setInterestRate(loanDto.getInterestRate());
        loan.setInteretCumule(account.isInteretCumule()); // Kopye kalite enterè a depi nan kont lan

        // Kalkile enterè pou premye mwa a imedyatman
        BigDecimal interestRate = loan.getInterestRate().divide(BigDecimal.valueOf(100));
        BigDecimal firstMonthInterest = loan.getPrincipalAmount().multiply(interestRate);

        // Mete ajou chan prè a ak enterè premye mwa a
        loan.setBalance(loan.getPrincipalAmount().add(firstMonthInterest)); // Balans kòmanse ak prensipal + enterè
        loan.setAccumulatedInterest(firstMonthInterest); // Premye enterè a akimile

        // Mete ajou dèt total manm nan. NOUVO: Nou ajoute balans prè a sou dèt ki te la anvan an.
        account.setBalanceDue(account.getBalanceDue().add(loan.getBalance()));

        // Mete ajou enterè total manm nan
        account.setInteret(account.getInteret().add(firstMonthInterest));

        // Sove chanjman yo
        accountDao.save(account); // Sove chanjman ki fèt sou kont lan

        // Inisyalize lòt chan ki enpòtan yo
        logger.info("KREYE NOUVO PRÈ: ID={}, Montan={}, Enterè Premye Mwa={}, Balans Inisyal={}", 
            loan.getIdLoan(), loan.getPrincipalAmount(), firstMonthInterest, loan.getBalance());

        return loanDao.save(loan);
    }

    @Override
    public BigDecimal calculateBalanceDue(Loan loan) {
        // Ansyen lojik kalkil la te konn lakòz erè pou enterè kimilatif.
        // Balans lan ap mete ajou kòrèkteman pa InterestCalculationTask ak processRefund.
        // Nou senpleman retounen valè ki deja la a, ki toujou ajou.
        logger.info("Resipere balans aktyèl pou prè {}: {}", loan.getIdLoan(), loan.getBalance());
        return loan.getBalance();
    }

    @Override
    public Account updateAndSaveBalanceDue(String idLoan) {
        Loan loan = loanDao.findById(idLoan)
                .orElseThrow(() -> new RuntimeException("Prè pa jwenn pou mete balans ajou: " + idLoan));

        // Kalkile balans lan ak lojik ki deja egziste a
        BigDecimal newBalance = calculateBalanceDue(loan);

        // Sove nouvo balans lan nan kont ki asosye a
        Account account = loan.getAccount();
        account.setBalanceDue(newBalance);
        accountDao.save(account);

        logger.info("Balans pou prè {} mete ajou nan baz done a. Nouvo balans: {}", idLoan, newBalance);

        return account;
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

    @Override
    public void processRefund(Refund refund) {
        // 1. Chèche prè ki asosye a
        Loan loan = loanDao.findById(refund.getLoan().getIdLoan())
                .orElseThrow(() -> new RuntimeException("Prè ki asosye ak ranbousman sa a pa jwenn."));

        BigDecimal currentBalance = loan.getBalance();
        BigDecimal refundAmount = refund.getAmountRefunded();

        // 2. VALIDASYON: Tcheke si montan ranbousman an pa depase sa moun nan dwe a
        if (refundAmount.compareTo(currentBalance) > 0) {
            throw new IllegalArgumentException(
                "Ou pa ka remèt plis kòb pase sa w dwe a. Ou dwe " + currentBalance + " epi w ap eseye remèt " + refundAmount);
        }

        // 3. Fè soustraksyon sou balans prè a
        BigDecimal newLoanBalance = currentBalance.subtract(refundAmount);
        loan.setBalance(newLoanBalance);
        logger.info("Ranbousman {} aplike sou prè {}. Nouvo balans prè: {}", refundAmount, loan.getIdLoan(), newLoanBalance);

        // 4. Jwenn kont manm nan epi fè soustraksyon sou dèt total li
        Account account = loan.getAccount();
        if (account != null) {
            BigDecimal newAccountBalanceDue = account.getBalanceDue().subtract(refundAmount);
            account.setBalanceDue(newAccountBalanceDue);
            accountDao.save(account);
            logger.info("Dèt total pou kont {} mete ajou. Nouvo dèt total: {}", account.getId(), newAccountBalanceDue);
        } else {
            logger.warn("Pa ka mete dèt total kont lan ajou paske prè {} pa gen kont asosye.", loan.getIdLoan());
        }

        // 5. Sove chanjman sou prè a ak ranbousman an li menm
        loanDao.save(loan);
        refundDao.save(refund);
    }

    @Override
    public List<Loan> findAllByStatus(String status) {
        return loanDao.findAllByStatus(status);
    }

    @Override
    public List<Loan> getLoansByAccount(String accountId) {
        return loanDao.findByAccount_Id(String.valueOf(Long.valueOf(accountId)));
    }

}
