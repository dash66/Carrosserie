package com.carrosserieafpa.carrosserie.entity;

import lombok.Data;

import org.springframework.lang.Nullable;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Collection;

@Data
@Entity
public class Prestation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_presta;

    @Nullable
    private double prix;

    @ManyToOne()
    @JoinColumn(name = "id_acte", referencedColumnName = "id_acte")
    private Acte acte;

    @ManyToOne()
    @JoinColumn(name = "id_finition", referencedColumnName = "id_finition")
    private Finition finition;

    public Prestation(double prix, Acte acte, Finition finition, Collection<Facturation> facturations) {
        this.prix = prix;
        this.acte = acte;
        this.finition = finition;

    }

    public Prestation() {
    }

    @Override
    public String toString() {
        return "Acte effectué : " + acte + "." + " \n" +
                "Finition choisie : " + finition + "." + " \n" +
                " Coût de l'intervention : " + prix + " euros.";
    }
}
