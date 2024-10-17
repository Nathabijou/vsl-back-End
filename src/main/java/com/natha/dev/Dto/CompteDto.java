package com.natha.dev.Dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
@Getter
@Setter
public class CompteDto {

    private Long id;
    private String  nom;
    private int numeroCompte;
    private BigDecimal balance;
    private BigDecimal pret;
    private BigDecimal interet;
    private BigDecimal balanceDue;
    private Date datePret;
    private Date remboursement;
    private  int nombreDaction;
    private BigDecimal prixAction;

    private Long userId;
    private Long groupeuserId;

//    public void setGroupeUsersId(Long id) {
//    }
}
