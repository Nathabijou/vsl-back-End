package com.natha.dev.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class GroupeDto {
    private Long   id;
    private String nom;
    private String adresse;
    private String responsable;
    private String description;

    private Long communeId; // Référence à la commune
    private List<UsersDto> utilisateurs; // Liste des utilisateurs associés
    private List<CompteDto> comptes;
}
