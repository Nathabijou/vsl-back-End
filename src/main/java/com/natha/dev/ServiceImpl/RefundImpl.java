package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.RefundDao;
import com.natha.dev.IService.RefundIService;

import com.natha.dev.Model.Loan;
import com.natha.dev.Model.Refund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefundImpl implements RefundIService {

    @Autowired
    private RefundDao refundDao;

    @Override
    public Refund save(Refund refund) {
        return refundDao.save(refund);
    }

    @Override
    public List<Refund> findByLoanId(String loanId) {
        return refundDao.findByLoan_IdLoan(loanId);
    }

    @Override
    public void flush() {
        refundDao.flush(); // <<< sa a p forse DB mete done yo
    }

    @Override
    public List<Refund> findByLoan(Loan loan) {
        return refundDao.findByLoan(loan);
    }
}



