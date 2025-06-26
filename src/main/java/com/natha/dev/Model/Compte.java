package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String  nom;
    private int numeroCompte;
    private BigDecimal balance;
    private BigDecimal interet;
    private BigDecimal balanceDue;


    private  int nombreDaction;

    private BigDecimal montant;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createDate")
    private Date createDate;
    private String createBy;

    @ManyToOne
    @JoinColumn(name = "groupe_user_id", nullable = false)
    private Groupe_Users groupe_users;

    @Column(name = "user_name")
    private String userName;
    private Long groupeId;

    public void setGroupeUsers(Groupe_Users groupeUsers) {
        this.groupe_users = groupeUsers;
    }

}
