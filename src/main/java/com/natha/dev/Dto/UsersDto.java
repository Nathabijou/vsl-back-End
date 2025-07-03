package com.natha.dev.Dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsersDto {
    @Id

    private String userName;
    private String userEmail;
    private String userPassword;
    private String userFirstName;
    private String userTelephone;

    private String userLastName;
    private String otpCode;
    private String userSexe;

    private boolean status;
    private LocalDateTime lastLoginTime;
    private LocalDateTime lastLogoutTime;
    private String createdBy;
    private String applicationId;


    public UsersDto(String username, String email, String userFirstName, String userLastName) {
        this.userName = username; // Initialiser l'attribut username
        this.userEmail = email;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;// Initialiser l'attribut email
    }
}
