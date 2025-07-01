package com.natha.dev.Model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter



public class DashboardFilter {
    private Long composanteId;
    private Long zoneId;
    private Long departementId;
    private Long communeId;
    private Long sectionId;
    private Long quartierId;
    private String projetId;

    // 🛠️ Add getters and setters
    public Long getComposanteId() { return composanteId; }
    public void setComposanteId(Long composanteId) { this.composanteId = composanteId; }

    public Long getZoneId() { return zoneId; }
    public void setZoneId(Long zoneId) { this.zoneId = zoneId; }

    public Long getDepartementId() { return departementId; }
    public void setDepartementId(Long departementId) { this.departementId = departementId; }

    public Long getCommuneId() { return communeId; }
    public void setCommuneId(Long communeId) { this.communeId = communeId; }

    public Long getSectionCommunaleId() { return sectionId; }
    public void setSectionCummualeId(Long sectionId) { this.sectionId = sectionId; }

    public Long getQuartierId() { return quartierId; }
    public void setQuartierId(Long quartierId) { this.quartierId = quartierId; }

    public String getProjetId() { return projetId; }
    public void setProjetId(String projetId) { this.projetId = projetId; }
}
