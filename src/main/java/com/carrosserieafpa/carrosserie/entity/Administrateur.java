package com.carrosserieafpa.carrosserie.entity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Administrateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String motDePasse;

    public Administrateur() {
    }

    public Administrateur(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Administrateur(Long id, String motDePasse) {
        this.id = id;
        this.motDePasse = motDePasse;
    }

    @Override
    public String toString() {
        return "Administrateur{" +
                "id=" + id +
                ", motDePasse='" + motDePasse + '\'' +
                '}';
    }
}
