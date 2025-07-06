package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @ManyToOne
    @JoinColumn(name = "composante_id")
    private Composante composante;

    // Zone.java
    @ManyToMany
    @JoinTable(
            name = "zone_departement",
            joinColumns = @JoinColumn(name = "zone_id"),
            inverseJoinColumns = @JoinColumn(name = "departement_id")
    )
    private List<Departement> departements;



}
