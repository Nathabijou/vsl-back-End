package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Beneficiaire {

    @Id
    @Column(length = 15, columnDefinition = "varchar(15)") // match JoinColumn
    private String idBeneficiaire;



    private String nom;
    private String prenom;
    private String sexe;

    private LocalDate dateNaissance;
    private String domaineDeFormation;
    private String typeIdentification;
    private String identification;
    private String lienNaissance;
    private String qualification;

    private String telephoneContact;
    private String telephonePaiement;
    private String operateurPaiement;

    @ManyToMany
    @JoinTable(
            name = "projet_beneficiaire",
            joinColumns = @JoinColumn(name = "beneficiaire_id"),
            inverseJoinColumns = @JoinColumn(name = "projet_id")
    )
    @Builder.Default
    private List<Projet> projets = new ArrayList<>();

    @PrePersist
    public void generateId() {
        if (this.idBeneficiaire == null) {
            this.idBeneficiaire = generateCustomId(15);
        }
    }

    private String generateCustomId(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
