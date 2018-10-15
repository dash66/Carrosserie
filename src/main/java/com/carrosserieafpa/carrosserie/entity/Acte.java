package com.carrosserieafpa.carrosserie.entity;

import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
public class Acte implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_acte;
    private String acte;



    @OneToMany(mappedBy = "acte")
    private Collection<Prestation> prestations;

    public Acte(String acte) {
        this.acte = acte;
    }

    public Long getId_acte() {
        return id_acte;
    }

    public void setId_acte(Long id_acte) {
        this.id_acte = id_acte;
    }

    public String getActe() {
        return acte;
    }

    public void setActe(String acte) {
        this.acte = acte;
    }

    public Acte() {
    }

    @Override
    public String toString() {
        return "Acte{" +
                "id_acte=" + id_acte +
                ", acte='" + acte + '\'' +
                '}';
    }
}
