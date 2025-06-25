package com.natha.dev.Dto;

import jakarta.persistence.Column;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjetDto {
    private String idProjet;
    private String name;
    private String description;
    private String address;
    private String domaineDeFormation;
    private String numeroDePatente;
    private String numeroDeReconnaissanceLegale;
    private String sourceDesNumeroDeReconnaissance;
    private String rangDePriorisation;
    private String type;
    private String statut;
    private String phase;
    private String code;
    @Column(nullable = false)
    private Boolean active = true;

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Double latitude;
    private Double longitude;
    private Double montantMainOeuvreQualifier;
    private Double montantMainOeuvreNonQualifier;
    private Double montantAssurance;
    private Double montantMateriaux;
    private Double montantTotal;
    private String createdBy;
    private String modifyBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long composanteId;
    private Long quartierId;
}