package com.carrosserieafpa.carrosserie.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
public class Finition  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_finition;
    private String libelle;

    @OneToMany(mappedBy = "finition")
    private Collection<Prestation> prestations;

    public Finition(String libelle) {
        this.libelle = libelle;
    }

    public Long getId_finition() {
        return id_finition;
    }

    public void setId_finition(Long id_finition) {
        this.id_finition = id_finition;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Finition() {
    }

    @Override
    public String toString() {
        return libelle;
    }
}
