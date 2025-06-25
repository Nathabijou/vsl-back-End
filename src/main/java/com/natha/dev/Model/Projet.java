package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Projet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, columnDefinition = "varchar(36)") // match SQL Server
    private String idProjet;


    private String name;
    private String description;
    private String address;
    private String domaineDeFormation;
    private String numeroDePatente;
    private String numeroDeReconnaissanceLegale;
    private String sourceDesNumeroDeReconnaissance;
    private String rangDePriorisation;
    private String type;
    private String statut; // planifié, en cours, terminé
    private String phase;
    private String code;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    private Double latitude;
    private Double longitude;

    private Double montantMainOeuvreQualifier;
    private Double montantMainOeuvreNonQualifier;
    private Double montantAssurance;
    private Double montantMateriaux;
    private Double montantTotal;

    private String createdBy;
    private String modifyBy;

    @Column(nullable = false)
    private Boolean active = true;

//    @PrePersist
//    private void ensureId() {
//        if (this.idProjet == null || this.idProjet.isBlank()) {
//            this.idProjet = generateRandomId(15);
//        }
//    }

    private static String generateRandomId(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "composante_id")
    private Composante composante;

    @ManyToOne
    @JoinColumn(name = "quartier_id")
    private Quartier quartier;


    @OneToMany(mappedBy = "projet")
    private List<ProjetBeneficiaire> projetBeneficiaires;


}
