package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_application_privilege")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserApplicationPrivilege {
    @EmbeddedId
    private UserAppPrivKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userName")
    @JoinColumn(name = "user_name")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("applicationId")
    @JoinColumn(name = "application_id")
    private ApplicationInstance application;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("privilegeName")
    @JoinColumn(name = "privilege_name")
    private Privilege privilege;
}
