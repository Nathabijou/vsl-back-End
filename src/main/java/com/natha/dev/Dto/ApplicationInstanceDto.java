package com.natha.dev.Dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationInstanceDto {

    private String idApp;
    private String name;
    private String description;
    private String logo;
    private String status;
    private Boolean active;

    private Boolean isSandbox;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastModifiedDate;
    private String createdBy;
    private String language;
    private String themeColor;

    // Pou kenbe lyen ak Organization sèlman pa id (pou evite nested full entity nan DTO)
    private String organizationId;
    private String organizationName;  // Si ou vle montre non òganizasyon an tou

}
