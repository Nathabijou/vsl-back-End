package com.natha.dev.Dto;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Groupe_UsersDto {
    @Id
    private  Long id;
    private String username; // Nom de l'utilisateur
    private Long groupeId;   // Identifiant du groupe
}
