package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayrollDto {

    private String idPayroll;
    private String idTransaction;
    private String methodePaiement;
    private LocalDate debutPeriode;
    private LocalDate finPeriode;
    private Double montantPayer;
    private String statut;
    private LocalDate datePaiement;
    private int nbrejourTravail;
    private Double montantParJour;
}
