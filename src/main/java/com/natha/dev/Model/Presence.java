package com.natha.dev.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Presence {

    @Id
    private String idPresence;

    private LocalDate datePresence;

    private LocalTime heurEntrer;

    private LocalTime heureSorti;

    private String createBy;

    @ManyToOne
    private ProjetBeneficiaire projetBeneficiaire;
}

