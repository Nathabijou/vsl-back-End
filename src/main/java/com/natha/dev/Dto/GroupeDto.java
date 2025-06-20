package com.natha.dev.Dto;

import lombok.Getter;
import lombok.Setter;

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




    private double prixAction;
    private double tauxInteret;

    private Long userName;
    private Long compteId;
    private Long communeId;

    // Getter & Setter
    public LocalDateTime getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(LocalDateTime datecreation) {
        this.datecreation = datecreation;
    }
}
