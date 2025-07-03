package com.natha.dev.Dto;

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

    private long totalFilleQualifier;
    private long totalFilleNonQualifier;
    private long totalGarconQualifier;
    private long totalGarconNonQualifier;

    private long totalFilleMoncash;
    private long totalFilleCash;
    private long totalGarconMoncash;
    private long totalGarconCash;

    private double totalFilleMonCashMontant;
    private double totalFilleLajanCashMontant;
    private double totalFilleNonQualifierMoncash;
    private double totalFilleNonQualifierLajanCash;
    private double totalGarconMonCashMontant;
    private double totalGarconLajanCashMontant;
    private long totalGasonQualifier;
    private long totalGasonNonQualifier;

    // Konstriktè ak tout chan yo
    public KpiResponse(long totalBeneficiaires,
                       long totalFemmes,
                       long totalHommes,
                       long totalQualifier,
                       long totalNonQualifier,
                       double totalMonCash,
                       double totalLajanCash,
                       long totalFilleQualifier,
                       long totalFilleNonQualifier,
                       long totalGarconQualifier,
                       long totalGarconNonQualifier,
                       long totalFilleMoncash,
                       long totalFilleCash,
                       long totalGarconMoncash,
                       long totalGarconCash,
                       double totalFilleMonCashMontant,
                       double totalFilleLajanCashMontant,
                       double totalFilleNonQualifierMoncash,
                       double totalFilleNonQualifierLajanCash,
                       double totalGarconMonCashMontant,
                       double totalGarconLajanCashMontant) {
        this.totalBeneficiaires = totalBeneficiaires;
        this.totalFemmes = totalFemmes;
        this.totalHommes = totalHommes;
        this.totalQualifier = totalQualifier;
        this.totalNonQualifier = totalNonQualifier;
        this.totalMonCash = totalMonCash;
        this.totalLajanCash = totalLajanCash;
        this.totalFilleQualifier = totalFilleQualifier;
        this.totalFilleNonQualifier = totalFilleNonQualifier;
        this.totalGarconQualifier = totalGarconQualifier;
        this.totalGarconNonQualifier = totalGarconNonQualifier;
        this.totalFilleMoncash = totalFilleMoncash;
        this.totalFilleCash = totalFilleCash;
        this.totalGarconMoncash = totalGarconMoncash;
        this.totalGarconCash = totalGarconCash;
        this.totalFilleMonCashMontant = totalFilleMonCashMontant;
        this.totalFilleLajanCashMontant = totalFilleLajanCashMontant;
        this.totalFilleNonQualifierMoncash = totalFilleNonQualifierMoncash;
        this.totalFilleNonQualifierLajanCash = totalFilleNonQualifierLajanCash;
        this.totalGarconMonCashMontant = totalGarconMonCashMontant;
        this.totalGarconLajanCashMontant = totalGarconLajanCashMontant;
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

    public long getTotalFilleQualifier() { return totalFilleQualifier; }
    public void setTotalFilleQualifier(int totalFilleQualifier) { this.totalFilleQualifier = totalFilleQualifier; }

    public long getTotalFilleNonQualifier() { return totalFilleNonQualifier; }
    public void setTotalFilleNonQualifier(int totalFilleNonQualifier) { this.totalFilleNonQualifier = totalFilleNonQualifier; }

    public long getTotalGasonQualifier() {
        return totalGasonQualifier;
    }
    public void setTotalGasonQualifier(long totalGasonQualifier) {
        this.totalGasonQualifier = totalGasonQualifier;
    }

    public long getTotalGasonNonQualifier() {
        return totalGasonNonQualifier;
    }
    public void setTotalGasonNonQualifier(long totalGasonNonQualifier) {
        this.totalGasonNonQualifier = totalGasonNonQualifier;
    }

}
