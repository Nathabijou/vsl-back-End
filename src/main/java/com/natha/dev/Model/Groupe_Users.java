package com.natha.dev.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

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

//    @OneToOne
//    @JoinColumn(name = "groupe_users_id", nullable = false)
//    private Groupe_Users groupeUsers;

//    @OneToOne
//    @JoinColumn(name = "comte_id")
//    private Compte compte;

    @OneToMany(mappedBy = "groupe_users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Compte> comptes;




    // Méthode statique pour créer une instance de Groupe_Users
//    public static Groupe_Users createGroupeUser(Users user, Groupe groupe, Compte compte) {
//        Groupe_Users groupeUser = new Groupe_Users();
//        groupeUser.setUsers(user);
//        groupeUser.setGroupe(groupe);
////        groupeUser.setCompte(compte);
//        return groupeUser;
//    }
}
