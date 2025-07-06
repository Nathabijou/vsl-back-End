package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Groupe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long Id;
    private String nom;
    private String description;
    private String adresse;
    private String responsable;
    private LocalDateTime datecreation;

    @Column(nullable = false)
    private BigDecimal prixAction;

    @Column(nullable = false)
    private BigDecimal tauxInteret;



    @PrePersist
    public void setDatecreation() {
        this.datecreation = LocalDateTime.now();
    }



}
