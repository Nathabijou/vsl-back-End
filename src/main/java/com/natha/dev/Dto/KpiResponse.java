package com.natha.dev.Dto;

public class KpiResponse {
    private long totalBeneficiaires;
    private long totalFemmes;
    private long totalHommes;
    private long totalQualifier;       // Q
    private long totalNonQualifier;    // NQ
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
    private int totalFilleQualifierMoncash;
    private int totalFilleQualifierLajanCash;
// etc.


    // ✅ Constructor san paramèt (obligatwa si w ap itilize Jackson, etc.)
    public KpiResponse() {}

    // ✅ Constructor pou tout 11 done yo
    public KpiResponse(
            long totalBeneficiaires,
            long totalFemmes,
            long totalHommes,
            long totalQualifier,
            long totalNonQualifier,
            double totalMonCash,
            double totalLajanCash,
            int totalFilleQualifier,
            int totalFilleNonQualifier,
            int totalGasonQualifier,
            int totalGasonNonQualifier
    ) {
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
    }

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
