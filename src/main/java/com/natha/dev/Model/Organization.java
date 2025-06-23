package com.natha.dev.Model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    public class Organization {

        @Id
        @Column(length = 10)
        private String idorg;

        @Column(nullable = false, unique = true)
        private String name;

        private String type; // ONG, Finance, Health, etc.

        private String edition; // Basic, Pro, Enterprise...

        private String status; // ACTIVE, INACTIVE, ARCHIVED...

        private Boolean isSandbox = false;

        private String contactEmail;

        private String phoneNumber;

        private String address;

        private String description;

        private String language; // ex: fr, en

        private String currency; // ex: HTG, USD

        private String createdBy;

        private Boolean active = true;

        private LocalDateTime startDate; // Dat kòmansman itilizasyon

        private LocalDateTime createdAt;

        private LocalDateTime updatedAt; // Dènye modify

        private LocalDateTime trialExpirationDate; // Si se trial org

        private LocalDateTime lastModifiedDate;

        @ManyToOne
        @JoinColumn(name = "system_id")
        private MySystem mySystem;


//        @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
//        private List<AppInstance> applications;

        @PrePersist
        protected void onCreate() {
            this.createdAt = LocalDateTime.now();
            this.startDate = LocalDateTime.now();
            this.lastModifiedDate = LocalDateTime.now();

            if (this.idorg == null) {
                this.idorg = generateCustomIdOrg(10);
            }
        }

        @PreUpdate
        protected void onUpdate() {
            this.updatedAt = LocalDateTime.now();
            this.lastModifiedDate = LocalDateTime.now();
        }

        private String generateCustomIdOrg(int length) {
            String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefg0123456789";
            StringBuilder sb = new StringBuilder();
            Random rand = new Random();

            for (int i = 0; i < length; i++) {
                sb.append(chars.charAt(rand.nextInt(chars.length())));
            }

            return sb.toString();
        }
    }
