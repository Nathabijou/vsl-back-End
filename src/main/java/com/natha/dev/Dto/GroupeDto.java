package com.natha.dev.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class GroupeDto {
    private Long   id;
    private String nom;
    private String adresse;
    private String responsable;
    private String description;
    private LocalDateTime datecreation;

    @JsonProperty("interetCumule")
    private boolean interetCumule;

    private BigDecimal prixAction;
    private BigDecimal tauxInteret;
    private BigDecimal solde;
    private BigDecimal montant;
    private int totalAction;
    private BigDecimal totalInteret;
    private BigDecimal capital;
    private int interet;

    private Long userName;
//    private Long compteId;
    private Long communeId;

    // Getter & Setter
    public LocalDateTime getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(LocalDateTime datecreation) {
        this.datecreation = datecreation;
    }

    public void setIdGroupe(Long id) {
    }
}
