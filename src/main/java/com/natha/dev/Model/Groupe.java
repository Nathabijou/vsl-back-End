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

    @Column(name = "is_interet_cumule", nullable = false, columnDefinition = "bit default 0")
    private boolean interetCumule;



    @Column(name = "frequence_remboursement")
    private String frequenceRemboursement;

    @ManyToOne
    @JoinColumn(name = "commune_id")
    private Commune commune;

    @PrePersist
    public void setDatecreation() {
        this.datecreation = LocalDateTime.now();
    }
}
