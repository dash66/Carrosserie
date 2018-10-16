package com.carrosserieafpa.carrosserie.entity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

import javax.persistence.*;

import java.util.Collection;

@Entity
public class Prestation {

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

    @OneToMany(mappedBy = "prestation", cascade = CascadeType.ALL)
    private Collection<Facturation> facturations;


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

    public Collection<Facturation> getFacturations() {
        return facturations;
    }

    public void setFacturations(Collection<Facturation> facturations) {
        this.facturations = facturations;
    }

    @Override
    public String toString() {
        return "Acte effectué : " + acte + ". Vous avez choisi comme finition : " + finition + ". Le prix de cette prestation est de : " + prix + " euros";
    }
}
