package com.carrosserieafpa.carrosserie.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

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

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    @Override
    public String toString() {
        return "Facture n°" + id + " " + "-- Date : " + date;
    }
}
