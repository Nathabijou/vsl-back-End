package com.natha.dev.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String  nom;
    private String numeroCompte;
    private BigDecimal balance;
    private BigDecimal interet;
    private BigDecimal balanceDue = BigDecimal.ZERO;

    @Column(nullable = false)
    private boolean active = true;



    private Integer nombreDaction;


    private BigDecimal montant;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createDate")
    private Date createDate;
    private String createBy;

    @OneToMany(mappedBy = "account")
    @JsonManagedReference
    private List<Action> actions;

    @OneToMany(mappedBy = "account")
    @JsonManagedReference
    private List<Loan> loans;

    @OneToOne
    @JoinColumn(name = "groupe_users_id", referencedColumnName = "id")
    private Groupe_Users groupeUsers;

    public Groupe_Users getGroupeUsers() {
        return groupeUsers;
    }

    public void setGroupeUsers(Groupe_Users groupeUsers) {
        this.groupeUsers = groupeUsers;
    }



    @Column(name = "user_name")
    private String userName;
    private Long groupeId;


    public void setGroupe_users(Groupe_Users groupeUsers) {
        this.groupeUsers = groupeUsers;
    }

}
