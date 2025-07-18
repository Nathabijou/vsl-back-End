package com.natha.dev.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
//@Builder
public class Users {
    @Id

    private String userName; // identifiant
    private String userEmail;
    private String userPassword;
    private String userFirstName;
    private String userLastName;
    private String userTelephone;
    private String userIdentification;

    private String otpCode;
    private String userSexe;

    private boolean status;
    private LocalDateTime lastLoginTime;
    private LocalDateTime lastLogoutTime;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createDate")
    private Date createDate;

    @PrePersist
    protected void onCreate() {
        createDate = new Date();
    }
    @Column(name = "createdBy")
    private String createdBy;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = {
                    @JoinColumn(name = "user_name")  // Chanje isit la
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_name")
            }
    )
    private Set<Role> role;


    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }

    public String getOtpCode() {
        return this.otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

}
