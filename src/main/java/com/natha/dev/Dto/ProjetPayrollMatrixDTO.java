package com.natha.dev.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProjetPayrollMatrixDTO {
    private String nomProjet;
    private List<PeriodeDTO> periodes;
    private List<BeneficiairePayrollMatrixDTO> beneficiaires;

    public String getNomProjet() {
        return nomProjet;
    }

    public void setNomProjet(String nomProjet) {
        this.nomProjet = nomProjet;
    }



    public List<BeneficiairePayrollMatrixDTO> getBeneficiaires() {
        return beneficiaires;
    }

    public void setBeneficiaires(List<BeneficiairePayrollMatrixDTO> beneficiaires) {
        this.beneficiaires = beneficiaires;
    }
}
