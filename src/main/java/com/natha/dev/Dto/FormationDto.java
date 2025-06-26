package com.natha.dev.Dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormationDto {
    private String idFormation;
    private String titre;
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String typeFormation;

}
