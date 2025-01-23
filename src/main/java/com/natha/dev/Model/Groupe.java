package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Groupe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long   id;
    private String nom;
    private String adresse;
    private String responsable;
    private String description;
    private String userGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commune_id", nullable = false)
    private Commune commune;

    @OneToMany(mappedBy = "groupe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Groupe_Users> groupeUsers;


}
