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

    private BigDecimal interet;
    private BigDecimal balanceDue;

    private  int nombreDaction;
    private boolean active;



    private Long userId;
    private Long groupeuserId;



}
