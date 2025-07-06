package com.natha.dev.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.tools.Diagnostic;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Composante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code; // e.g. HAL-1137, J0005
    private String nom;
    private String type; // e.g. "ZONE_BASED", "CENTER_BASED", "DIRECT_PROJECT"

    @ManyToOne
    @JoinColumn(name = "application_instance_id")
    @JsonBackReference
    private ApplicationInstance applicationInstance;

    @JsonIgnore
    @ManyToMany(mappedBy = "composantes")
    private Set<Users> users = new HashSet<>();


    private String description;
    private String createdBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "composante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Zone> zones;

    @OneToMany(mappedBy = "composante", cascade = CascadeType.ALL)
    private List<Projet> projets;


    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }




}

