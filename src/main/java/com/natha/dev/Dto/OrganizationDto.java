package com.natha.dev.Dto;

import com.natha.dev.Model.MySystem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Random;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class OrganizationDto {

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
