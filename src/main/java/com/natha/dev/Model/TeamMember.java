package com.natha.dev.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class TeamMember {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Users user;

    @ManyToOne
    private Team team;

    private String role; // eg: "ADMIN", "MEMBER"
}
