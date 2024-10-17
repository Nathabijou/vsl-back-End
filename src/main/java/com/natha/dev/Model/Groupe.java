package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Groupe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userGroup;

    private String nom; // Group name
    private String adresse; // Address
    private String responsable; // Responsible person
    private String description;

    @Column(nullable = false) // Indique que ce champ ne peut pas être null
    private Double prixAction = 0.0; // Description

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createDate")
    private Date createDate; // Date created

    @OneToMany(mappedBy = "groupe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Groupe_Users> groupeUsers = new HashSet<>(); // Avoid NullPointerException

    @PrePersist
    protected void onCreate() {
        createDate = new Date(); // Initialize createDate with current date
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private Commune commune;

    public Double getPrixParAction() {
        return getPrixParAction();
    }

    public void setPrixAction(Double prixAction) {
        if (prixAction < 0) {
            throw new IllegalArgumentException("Le prix de l'action ne peut pas être négatif");
        }
        this.prixAction = prixAction;
    }


}
