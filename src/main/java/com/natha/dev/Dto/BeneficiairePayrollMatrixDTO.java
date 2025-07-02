package com.natha.dev.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BeneficiairePayrollMatrixDTO {
    private String nom;
    private String prenom;
    private String sexe;
    private String qualification;

    private String identification;
    private String telephonePaiement;

    private List<PayrollColDTO> payrolls;
}
