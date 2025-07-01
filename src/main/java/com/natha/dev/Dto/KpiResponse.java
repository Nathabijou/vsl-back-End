package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
public class KpiResponse {
    private long totalBeneficiaires;
    private long totalFemmes;
    private long totalHommes;
    private long totalQualifier;
    private long totalNonQualifier;
    private double totalMonCash;
    private double totalLajanCash;

    private int totalFilleQualifier;
    private int totalFilleNonQualifier;
    private int totalGasonQualifier;
    private int totalGasonNonQualifier;

    private int totalFilleMoncash;
    private int totalFilleCash;
    private int totalGasonMoncash;
    private int totalGasonCash;

    private double totalFilleMonCashMontant;
    private double totalFilleLajanCashMontant;
    private double totalFilleNonQualifierMoncash;
    private double totalFilleNonQualifierLajanCash;
    private double totalGasonMonCashMontant;
    private double totalGasonLajanCashMontant;

    // ✅ Konstriktè manyèl ak tout 22 paramèt yo
    public KpiResponse(long totalBeneficiaires,
                       long totalFemmes,
                       long totalHommes,
                       long totalQualifier,
                       long totalNonQualifier,
                       double totalMonCash,
                       double totalLajanCash,
                       int totalFilleQualifier,
                       int totalFilleNonQualifier,
                       int totalGasonQualifier,
                       int totalGasonNonQualifier,
                       int totalFilleMoncash,
                       int totalFilleCash,
                       int totalGasonMoncash,
                       int totalGasonCash,
                       double totalFilleMonCashMontant,
                       double totalFilleLajanCashMontant,
                       double totalFilleNonQualifierMoncash,
                       double totalFilleNonQualifierLajanCash,
                       double totalGasonMonCashMontant,
                       double totalGasonLajanCashMontant) {
        this.totalBeneficiaires = totalBeneficiaires;
        this.totalFemmes = totalFemmes;
        this.totalHommes = totalHommes;
        this.totalQualifier = totalQualifier;
        this.totalNonQualifier = totalNonQualifier;
        this.totalMonCash = totalMonCash;
        this.totalLajanCash = totalLajanCash;
        this.totalFilleQualifier = totalFilleQualifier;
        this.totalFilleNonQualifier = totalFilleNonQualifier;
        this.totalGasonQualifier = totalGasonQualifier;
        this.totalGasonNonQualifier = totalGasonNonQualifier;
        this.totalFilleMoncash = totalFilleMoncash;
        this.totalFilleCash = totalFilleCash;
        this.totalGasonMoncash = totalGasonMoncash;
        this.totalGasonCash = totalGasonCash;
        this.totalFilleMonCashMontant = totalFilleMonCashMontant;
        this.totalFilleLajanCashMontant = totalFilleLajanCashMontant;
        this.totalFilleNonQualifierMoncash = totalFilleNonQualifierMoncash;
        this.totalFilleNonQualifierLajanCash = totalFilleNonQualifierLajanCash;
        this.totalGasonMonCashMontant = totalGasonMonCashMontant;
        this.totalGasonLajanCashMontant = totalGasonLajanCashMontant;
    }

    // (Opsyonèl) Ajoute constructor san paramèt si w ap itilize Jackson pou désérialization
    public KpiResponse() {}





    // ✅ Getters & Setters
    public long getTotalBeneficiaires() { return totalBeneficiaires; }
    public void setTotalBeneficiaires(long totalBeneficiaires) { this.totalBeneficiaires = totalBeneficiaires; }

    public long getTotalFemmes() { return totalFemmes; }
    public void setTotalFemmes(long totalFemmes) { this.totalFemmes = totalFemmes; }

    public long getTotalHommes() { return totalHommes; }
    public void setTotalHommes(long totalHommes) { this.totalHommes = totalHommes; }

    public long getTotalQualifier() { return totalQualifier; }
    public void setTotalQualifier(long totalQualifier) { this.totalQualifier = totalQualifier; }

    public long getTotalNonQualifier() { return totalNonQualifier; }
    public void setTotalNonQualifier(long totalNonQualifier) { this.totalNonQualifier = totalNonQualifier; }

    public double getTotalMonCash() { return totalMonCash; }
    public void setTotalMonCash(double totalMonCash) { this.totalMonCash = totalMonCash; }

    public double getTotalLajanCash() { return totalLajanCash; }
    public void setTotalLajanCash(double totalLajanCash) { this.totalLajanCash = totalLajanCash; }

    public int getTotalFilleQualifier() { return totalFilleQualifier; }
    public void setTotalFilleQualifier(int totalFilleQualifier) { this.totalFilleQualifier = totalFilleQualifier; }

    public int getTotalFilleNonQualifier() { return totalFilleNonQualifier; }
    public void setTotalFilleNonQualifier(int totalFilleNonQualifier) { this.totalFilleNonQualifier = totalFilleNonQualifier; }

    public int getTotalGasonQualifier() { return totalGasonQualifier; }
    public void setTotalGasonQualifier(int totalGasonQualifier) { this.totalGasonQualifier = totalGasonQualifier; }

    public int getTotalGasonNonQualifier() { return totalGasonNonQualifier; }
    public void setTotalGasonNonQualifier(int totalGasonNonQualifier) { this.totalGasonNonQualifier = totalGasonNonQualifier; }
}
