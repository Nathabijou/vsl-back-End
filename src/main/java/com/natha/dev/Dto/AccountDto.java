package com.natha.dev.Dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountDto {

    private Long idAccount;
    private String  nom;
    private String numeroCompte;
    private BigDecimal balance;
    private BigDecimal depot;
    private BigDecimal solde; // NOUVO CHAN POU SOLDE TOTAL
    private int interet;
    private int monInteret; // NOUVO CHAN POU ENTERÈ PA MANM
    private BigDecimal balanceDue;

    private  int nombreDaction;
    private boolean active;



    private Long userId;
    private Long groupeuserId;
    private int totalAction; 
    private int totalNumberOfSharesFromDeposits; // Nouvo chan pou sòm aksyon nan depo yo

}
