package com.natha.dev.Controller;

import com.natha.dev.Dto.ProjetPayrollMatrixDTO;
import com.natha.dev.ServiceImpl.PayrollMatrixServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payroll")
public class PayrollMatrixController {

    @Autowired
    private PayrollMatrixServiceImpl payrollMatrixService;

    @GetMapping("/matrix/{projetId}")
    public ProjetPayrollMatrixDTO getPayrollMatrix(@PathVariable String projetId) {
        return payrollMatrixService.generatePayrollMatrix(projetId);
    }
}
