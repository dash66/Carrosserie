package com.carrosserieafpa.carrosserie.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

@Entity
public class Facturation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private double prix;

    @ManyToOne()
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @ManyToOne()
    @JoinColumn(name = "voiture_id", referencedColumnName = "id")
    private Voiture voiture;

    private String date;

    @ManyToMany(cascade = CascadeType.REMOVE)
    private Collection<Prestation> prestation;

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


    public Facturation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Collection<Prestation> getPrestation() {
        return prestation;
    }

    public void setPrestation(Collection<Prestation> prestation) {
        this.prestation = prestation;
    }

    @Override
    public String toString() {
        return "Facture n°" + id + " " + "-- Date : " + date;
    }
}
