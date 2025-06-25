package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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
    @JoinColumn(name = "application_id")
    private ApplicationInstance applicationInstance;

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


    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


}

