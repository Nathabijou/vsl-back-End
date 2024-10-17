package com.natha.dev.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class GroupeDto {
    private Long   id;
    private String nom;
    private String adresse;
    private String responsable;
    private String description;

    private Long userId;
    private Long compteId;
    private Long communeId;
}
