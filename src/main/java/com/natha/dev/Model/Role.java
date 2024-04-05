package com.natha.dev.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    @JoinTable(name = "ROLE_PRIVILEGE",
            joinColumns = {
                    @JoinColumn(name = "ROLE_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "PRIVILEGE_ID")
            }
    )
    private Set<Privilege> privileges;

    // Ajoute metòd pou ajoute yon priveleg nan lis la
    public void addPrivilege(Privilege privilege) {
        privileges.add(privilege);
    }



}
