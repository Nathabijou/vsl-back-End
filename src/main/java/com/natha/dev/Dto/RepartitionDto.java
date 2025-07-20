package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RepartitionDto {

    private String prenom;
    private String nom;
    private String sexe;
    private String telephone;
    private int totalAction;
    private BigDecimal solde;
    private BigDecimal montantAToucher;
    private String statutPaiement; // NOUVO CHAN POU ESTATI PEMAN

}
