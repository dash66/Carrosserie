package com.carrosserieafpa.carrosserie.entity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Collection;

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

    public Long getId_presta() {
        return id_presta;
    }

    public void setId_presta(Long id_presta) {
        this.id_presta = id_presta;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Acte getActe() {
        return acte;
    }

    public void setActe(Acte acte) {
        this.acte = acte;
    }

    public Finition getFinition() {
        return finition;
    }

    public void setFinition(Finition finition) {
        this.finition = finition;
    }

    @Override
    public String toString() {
        return "Acte effectué : " + acte + "." + " \n" +
                "Finition choisie : " + finition + "." + " \n" +
                " Coût de l'intervention : " + prix + " euros.";
    }
}
