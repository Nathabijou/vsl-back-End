package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Groupe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long Id;
    private String nom;
    private String description;
    private String adresse;
    private String responsable;
    private LocalDateTime datecreation;

    @Column(nullable = false)
    private BigDecimal prixAction;

    @Column(nullable = false)
    private BigDecimal tauxInteret;

    @Column(name = "is_interet_cumule", nullable = false, columnDefinition = "bit default 0")
    private boolean interetCumule;



    @Column(name = "frequence_remboursement")
    private String frequenceRemboursement;

    @Transient
    private BigDecimal solde;

    @Transient
    private BigDecimal montant;

    @Transient
    private Integer totalAction;

    @Transient
    private BigDecimal totalInteret;

    @Transient
    private BigDecimal capital;

    @Transient
    private int interet;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupeId", referencedColumnName = "id")
    private List<Account> accounts;

    public BigDecimal getMontant() {
        if (accounts == null || accounts.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal totalBalance = accounts.stream()
                .map(Account::getBalance)
                .filter(java.util.Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDeposits = accounts.stream()
                .map(Account::getTotalDeposit)
                .filter(java.util.Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalBalance.add(totalDeposits);
    }

    public Integer getTotalAction() {
        if (accounts == null || accounts.isEmpty()) {
            return 0;
        }
        return accounts.stream()
                .map(Account::getTotalAction)
                .filter(java.util.Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public BigDecimal getTotalInteret() {
        if (accounts == null || accounts.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return accounts.stream()
                .flatMap(account -> account.getLoans().stream())
                .map(Loan::getAccumulatedInterest)
                .filter(java.util.Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getCapital() {
        BigDecimal montantValue = getMontant() != null ? getMontant() : BigDecimal.ZERO;
        BigDecimal interetValue = getTotalInteret() != null ? getTotalInteret() : BigDecimal.ZERO;
        return montantValue.add(interetValue);
    }

    @Transient
    public int getInteret() {
        BigDecimal totalInteretValue = getTotalInteret() != null ? getTotalInteret() : BigDecimal.ZERO;
        BigDecimal totalAction = BigDecimal.valueOf(getTotalAction());

        if (totalAction.compareTo(BigDecimal.ZERO) == 0) {
            return 0; // Evite divizyon pa zewo
        }

        // Nouvo fòmil: Total Enterè / Total Aksyon
        return totalInteretValue.divide(totalAction, 2, RoundingMode.HALF_UP).intValue();
    }

    @ManyToOne
    @JoinColumn(name = "commune_id")
    private Commune commune;

    @PrePersist
    public void setDatecreation() {
        this.datecreation = LocalDateTime.now();
    }
}
