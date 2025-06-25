package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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





}
