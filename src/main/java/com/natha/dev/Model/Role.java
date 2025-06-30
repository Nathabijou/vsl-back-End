package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Role {

    @Id
   // private Long role_id;
    private String roleName;
    private String roleDescription;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_privilege",
            joinColumns = {
                    @JoinColumn(name = "role_name")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "privilege_name")
            }
    )
    private Set<Privilege> privileges;

    // Ajoute metòd pou ajoute yon priveleg nan lis la
    public void addPrivilege(Privilege privilege) {
        privileges.add(privilege);
    }
}
