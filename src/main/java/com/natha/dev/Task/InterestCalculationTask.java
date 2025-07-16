package com.natha.dev.Task;

import com.natha.dev.Dao.AccountDao;
import com.natha.dev.Dao.LoanDao; // Ajoute DAO pou prè
import com.natha.dev.IService.LoanIService;
import com.natha.dev.Model.Account;
import com.natha.dev.Model.Loan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Component
public class InterestCalculationTask {

    private static final Logger logger = LoggerFactory.getLogger(InterestCalculationTask.class);

    @Autowired
    private LoanIService loanIService;

    @Autowired
    private AccountDao accountDao; // Ajoute DAO pou kont lan

    @Autowired
    private LoanDao loanDao; // Ajoute DAO pou prè

    // Egzekite chak 2 minit pou fè tès (120,000 milisegonn)
    @Scheduled(fixedRate = 120000)
    @Transactional
    public void calculateMonthlyInterest() {
        logger.info("--- KÒMANSE TRAVAY OTOMATIK: Kalkil Enterè Mansyèl --- ");

        List<Loan> activeLoans = loanIService.findAllByStatus("EN_COURS");

        if (activeLoans.isEmpty()) {
            logger.info("Pa gen okenn prè 'EN_COURS' pou trete.");
        } else {
            logger.info("Jwenn {} prè 'EN_COURS' pou kalkile enterè.", activeLoans.size());
            for (Loan loan : activeLoans) {
                try {
                    // Etap 1: Jwenn kont ak gwoup ki asosye ak prè a
                    Account account = loan.getAccount();
                    if (account == null) {
                        logger.warn("Prè ID {} pa gen kont ki asosye avè l. Kalkil anile.", loan.getIdLoan());
                        continue; // Ale nan pwochen prè a
                    }

                    // Sipoze ke 'interetCumule' ak 'interestRate' yo estoke dirèkteman sou prè a lè li kreye.
                    // Sa evite chèche gwoup la chak fwa.
                    boolean isCumulative = loan.isInteretCumule();
                    BigDecimal interestRate = loan.getInterestRate().divide(BigDecimal.valueOf(100)); // Konvèti pousantaj an desimal
                    BigDecimal monthlyInterestAmount;

                    // Etap 2: Kalkile enterè a baze sou tip li (Senp ou Kimile)
                    if (loan.isInteretCumule()) {
                        // KALKIL KIMILE: Toujou baze sou dèt total ki rete a (balanceDue).
                        // Lè prè a fèk kòmanse, balanceDue = principalAmount, kidonk premye kalkil la ap kòrèk.
                        monthlyInterestAmount = account.getBalanceDue().multiply(interestRate);
                        logger.info("Kalkil enterè KIMILATIF pou prè ID {}: {} (Balans Dèt) * {} (To) = {}",
                                loan.getIdLoan(), account.getBalanceDue(), interestRate, monthlyInterestAmount);
                    } else {
                        // KALKIL SENP: Baze sou montan orijinal prè a (principalAmount)
                        monthlyInterestAmount = loan.getPrincipalAmount().multiply(interestRate);
                        logger.info("Kalkil enterè SENP pou prè ID {}: {} (Prensipal) * {} (To) = {}",
                                loan.getIdLoan(), loan.getPrincipalAmount(), interestRate, monthlyInterestAmount);
                    }

                    // Etap 3: Mete ajou tout chan ki nesesè yo
                    if (monthlyInterestAmount.compareTo(BigDecimal.ZERO) > 0) {
                        // Kalkile nouvo valè yo an premye
                        BigDecimal newAccumulatedInterest = loan.getAccumulatedInterest().add(monthlyInterestAmount);
                        BigDecimal newBalanceDue = account.getBalanceDue().add(monthlyInterestAmount);
                        BigDecimal newTotalInterest = account.getInteret().add(monthlyInterestAmount);

                        // Mete ajou kont lan ak nouvo valè yo
                        account.setInteret(newTotalInterest);
                        account.setBalanceDue(newBalanceDue);
                        accountDao.save(account);

                        // Mete ajou prè a pou l ka senkronize ak kont lan
                        loan.setAccumulatedInterest(newAccumulatedInterest);
                        loan.setBalance(newBalanceDue); // Balans prè a = nouvo dèt total la
                        loanDao.save(loan);

                        logger.info("MIZAJOU REYISI pou prè {}. Nouvo balans dèt: {}. Total enterè: {}.",
                                loan.getIdLoan(), newBalanceDue, newTotalInterest);
                    }

                } catch (Exception e) {
                    logger.error("Erè pandan kalkil enterè pou prè ID: {}", loan.getIdLoan(), e);
                }
            }
        }

        logger.info("--- FINI TRAVAY OTOMATIK: Kalkil Enterè Mansyèl --- ");
    }
}
