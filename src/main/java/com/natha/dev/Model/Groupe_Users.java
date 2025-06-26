package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "groupe_users")
public class Groupe_Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_name", nullable = false)
    private Users users; // Référence à l'utilisateur

    @ManyToOne
    @JoinColumn(name = "groupe_id", nullable = false)
    private Groupe groupe; // Référence au groupe

    @OneToOne(mappedBy = "groupeUsers", cascade = CascadeType.ALL)
    private Account account;


}
