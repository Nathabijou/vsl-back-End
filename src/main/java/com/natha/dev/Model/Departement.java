package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Departement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    // Departement.java
    @ManyToMany(mappedBy = "departements")
    private List<Zone> zones;

    @ManyToOne
    @JoinColumn(name = "zone_id") // mete non kolòn ki konekte departement ak zone nan baz done a
    private Zone zone;

    // getter and setter pou zone
    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }


}
