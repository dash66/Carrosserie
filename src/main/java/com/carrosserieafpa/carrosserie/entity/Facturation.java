package com.carrosserieafpa.carrosserie.entity;

import javax.persistence.*;
import java.util.Collection;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_presta", referencedColumnName = "id_presta")
    private Prestation prestation;

    public Facturation(double prix, String codeCouleur, Client client, Prestation prestation) {
        this.prix = prix;
        this.client = client;
        this.prestation = prestation;
    }

    public Facturation() {
    }

    public Prestation getPrestation() {
        return prestation;
    }

    public void setPrestation(Prestation prestation) {
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

        public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Facturation{" +
                "id=" + id +
                ", prix=" + prix +
                ", client=" + client +
                ", prestation=" + prestation +
                '}';
    }
}
