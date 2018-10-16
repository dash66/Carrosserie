package com.carrosserieafpa.carrosserie.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Voiture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String immat;
    private String marque;
    private String modele;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String codeCouleur;

    @ManyToOne()
    @JoinColumn(name = "id_client", referencedColumnName = "id")
    private Client client;

    public Voiture() {
    }

    public Voiture(String immat, String marque, String modele, Date date, String codeCouleur, Client client) {
        this.immat = immat;
        this.marque = marque;
        this.modele = modele;
        this.date = date;
        this.codeCouleur = codeCouleur;
        this.client = client;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImmat() {
        return immat;
    }

    public void setImmat(String immat) {
        this.immat = immat;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCodeCouleur() {
        return codeCouleur;
    }

    public void setCodeCouleur(String codeCouleur) {
        this.codeCouleur = codeCouleur;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Voiture{" +
                "id=" + id +
                ", immat='" + immat + '\'' +
                ", modele='" + modele + '\'' +
                ", date=" + date +
                ", codeCouleur='" + codeCouleur + '\'' +
                ", client=" + client +
                '}';
    }
}



