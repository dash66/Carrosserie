package com.carrosserieafpa.carrosserie.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;


@ToString(exclude = {"client"})
@Data
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

}



