package com.natha.dev.Dto;

import com.natha.dev.Model.Beneficiaire;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiaireDto {
    private String idBeneficiaire;
    private String nom;
    private String prenom;
    private String sexe;
    private LocalDate dateNaissance;
    private String domaineDeFormation;
    private String typeIdentification;
    private String identification;
    private String lienNaissance;
    private String qualification;
    private String telephoneContact;
    private String telephonePaiement;
    private String operateurPaiement;

    public static BeneficiaireDto fromEntity(Beneficiaire b) {
        if (b == null) {
            return null;
        }
        return new BeneficiaireDto(
                b.getIdBeneficiaire(),
                b.getNom(),
                b.getPrenom(),
                b.getSexe(),
                b.getDateNaissance(),
                b.getDomaineDeFormation(),
                b.getTypeIdentification(),
                b.getIdentification(),
                b.getLienNaissance(),
                b.getQualification(),
                b.getTelephoneContact(),
                b.getTelephonePaiement(),
                b.getOperateurPaiement()
        );
    }

}
