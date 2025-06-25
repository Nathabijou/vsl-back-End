package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjetBeneficiaireDto {
    private String idProjetBeneficiaire;
    private String projetId;
    private String beneficiaireId;
}
