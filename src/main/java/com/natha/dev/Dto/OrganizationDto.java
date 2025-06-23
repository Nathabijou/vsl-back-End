package com.natha.dev.Dto;

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

    private String idorg;

    private boolean status;

    private String name;

    private String type; // ONG, Finance, Health, etc.

    private String edition; // Basic, Pro, Enterprise...

    private Boolean isSandbox = false;

    private Integer maxAppsAllowed;

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

    // Metòd pou jenere ID nan backend si ou vle (men sa ka rete nan service/entity)
    public String generateCustomIdOrg(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefg0123456789";
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();

        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
