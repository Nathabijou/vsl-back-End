package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Formation {

    @Id
    private String idFormation;

    private String titre;

    private String description;

    private LocalDate dateDebut;

    private LocalDate dateFin;
    private String typeFormation;


    @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL)
    private List<ProjetBeneficiaireFormation> projetBeneficiaireFormations;


}
