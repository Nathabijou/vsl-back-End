package com.natha.dev.Model;

import com.natha.dev.Model.Formation;
import com.natha.dev.Model.Presence;
import com.natha.dev.Model.ProjetBeneficiaire;
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
public class ProjetBeneficiaireFormation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "projet_beneficiaire_id")
    private ProjetBeneficiaire projetBeneficiaire;

    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;

    @OneToMany(mappedBy = "projetBeneficiaireFormation", cascade = CascadeType.ALL)
    private List<Presence> presences;
}
