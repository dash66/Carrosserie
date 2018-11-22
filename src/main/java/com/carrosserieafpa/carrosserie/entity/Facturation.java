package com.carrosserieafpa.carrosserie.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Data
@Entity
public class Facturation implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private double prix;

    private String remarque;

    @ManyToOne()
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    private String date;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Prestation> prestation;

    @ManyToOne
    @JoinColumn(name = "voiture_id", referencedColumnName = "id")
    private Voiture voiture;

    public Facturation(double prix, String date, Collection<Prestation> prestation) {
        this.prix = prix;
        this.date = date;
        this.prestation = prestation;
    }

    public Facturation(double prix, Client client, String date, Collection<Prestation> prestation) {
        this.prix = prix;
        this.client = client;
        this.date = date;
        this.prestation = prestation;
    }

    public Facturation(double prix, Client client, String date, Collection<Prestation> prestation, Voiture voiture) {
        this.prix = prix;
        this.client = client;
        this.date = date;
        this.prestation = prestation;
        this.voiture = voiture;
    }

    public Facturation(double prix, String remarque, Client client, String date, Collection<Prestation> prestation, Voiture voiture) {
        this.prix = prix;
        this.remarque = remarque;
        this.client = client;
        this.date = date;
        this.prestation = prestation;
        this.voiture = voiture;
    }

    public Facturation() {
    }

    public Facturation(double prix, String date) {
        this.prix = prix;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Facture n°" + id + " " + "-- Date : " + date;
    }
}
