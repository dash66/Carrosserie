package com.carrosserieafpa.carrosserie.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@ToString(exclude = {"client"})
@Data
@Entity
public class Acte implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_acte;
    private String libelle;



    @OneToMany(mappedBy = "acte", cascade = CascadeType.REMOVE)
    private Collection<Prestation> prestations;

    @Override
    public String toString() {
        return libelle;
    }
}
