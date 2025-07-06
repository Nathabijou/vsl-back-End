package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idPayroll;

    private String idTransaction;

    private String methodePaiement; // eg: MonCash, BanKom, Espès, etc.

    private LocalDate debutPeriode;

    private LocalDate finPeriode;

    private Double montantPayer;

    @Column(nullable = false)
    private Integer nbreJourTravail = 0; // default value


    private String statut; // eg: payé, en attente, rejeté

    private LocalDate datePaiement;

    @ManyToOne
    @JoinColumn(name = "projet_beneficiaire_id", nullable = false)
    private ProjetBeneficiaire projetBeneficiaire;
}
