package com.natha.dev.Dto;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class UsersDto {
    @Id

    private String userName;
    private String userEmail;
    private String userPassword;
    private String userFirstName;
    private String userLastName;
    private String otpCode;
    private String userSexe;

    private boolean status;
    private LocalDateTime lastLoginTime;
    private LocalDateTime lastLogoutTime;
    private String createdBy;

    public UsersDto(String username, String email) {
        this.userName = username; // Initialiser l'attribut username
        this.userEmail = email;       // Initialiser l'attribut email
    }
}
