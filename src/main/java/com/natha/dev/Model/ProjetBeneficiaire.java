package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "projet_beneficiaire")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjetBeneficiaire {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idProjetBeneficiaire;

    @ManyToOne
    @JoinColumn(name = "projet_id", nullable = false)
    private Projet projet;

    @ManyToOne
    @JoinColumn(name = "beneficiaire_id", nullable = false)
    private Beneficiaire beneficiaire;

    @OneToMany(mappedBy = "projetBeneficiaire", cascade = CascadeType.ALL)
    private List<Payroll> payrolls;






}
