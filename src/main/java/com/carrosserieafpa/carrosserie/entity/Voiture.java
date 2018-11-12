package com.carrosserieafpa.carrosserie.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
public class Voiture implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String immat;
    private String marque;
    private String modele;
    private String categorie;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String date;
    private String codeCouleur;

    @ManyToOne()
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @OneToMany(mappedBy = "voiture")
    private Collection<Facturation> facturation;

    public Voiture() {
    }

    public Voiture(String immat, String marque, String modele, String categorie, String date, String codeCouleur, Client client) {
        this.immat = immat;
        this.marque = marque;
        this.modele = modele;
        this.categorie = categorie;
        this.date = date;
        this.codeCouleur = codeCouleur;
        this.client = client;
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

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
                ", marque='" + marque + '\'' +
                ", modele='" + modele + '\'' +
                ", categorie='" + categorie + '\'' +
                ", date=" + date +
                ", codeCouleur='" + codeCouleur + '\'' +
                ", client=" + client +
                '}';
    }
}



