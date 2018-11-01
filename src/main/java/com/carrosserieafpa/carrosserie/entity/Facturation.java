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

    private LocalDateTime date;

    @ManyToMany(cascade = CascadeType.ALL)
    private Collection<Prestation> prestation;

    public Facturation(double prix, Client client, LocalDateTime date, Collection<Prestation> prestation) {
        this.prix = prix;
        this.client = client;
        this.date = date;
        this.prestation = prestation;
    }

    public Facturation() {
    }

    public Collection<Prestation> getPrestation() {
        return prestation;
    }

    public void setPrestation(Collection<Prestation> prestation) {
        this.prestation = prestation;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Facture n°"+
                id + " -- " +
                "Date : " + date + "\n" +
                "**********************************" + "\n" +
                "  Prestation(s) effectuée(s) : " + "\n" +
                prestation + "\n" +
                "**********************************" + "\n";
    }
}
