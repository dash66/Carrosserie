package com.carrosserieafpa.carrosserie.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
public class Acte implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_acte;
    private String libelle;



    @OneToMany(mappedBy = "acte", cascade = CascadeType.REMOVE)
    private Collection<Prestation> prestations;

    public Long getId_acte() {
        return id_acte;
    }

    public void setId_acte(Long id_acte) {
        this.id_acte = id_acte;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Collection<Prestation> getPrestations() {
        return prestations;
    }

    public void setPrestations(Collection<Prestation> prestations) {
        this.prestations = prestations;
    }

    public Acte() {
    }

    @Override
    public String toString() {
        return libelle;
    }
}
