package com.carrosserieafpa.carrosserie.entity;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prenom;
    private String nom;

    @Nullable
    private String rue;
    @Nullable
    private String codePostal;
    @Nullable
    private String ville;

    @Nullable
    private String email;

    private String telephone;
    private String numAfpa;

    @OneToMany(mappedBy = "client", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Voiture> voiture;

    @OneToMany(mappedBy = "client", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Facturation> facturation;

    public Client(String prenom, String nom, @Nullable String rue, @Nullable String codePostal, @Nullable String ville, @Nullable String email, String telephone, String numAfpa, Collection<Voiture> voiture, Collection<Facturation> facturation) {
        this.prenom = prenom;
        this.nom = nom;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.email = email;
        this.telephone = telephone;
        this.numAfpa = numAfpa;
        this.voiture = voiture;
        this.facturation = facturation;
    }

    public Client() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Nullable
    public String getRue() {
        return rue;
    }

    public void setRue(@Nullable String rue) {
        this.rue = rue;
    }

    @Nullable
    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(@Nullable String codePostal) {
        this.codePostal = codePostal;
    }

    @Nullable
    public String getVille() {
        return ville;
    }

    public void setVille(@Nullable String ville) {
        this.ville = ville;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNumAfpa() {
        return numAfpa;
    }

    public void setNumAfpa(String numAfpa) {
        this.numAfpa = numAfpa;
    }

    @Ignore
    public Collection<Voiture> getVoiture() {
        return voiture;
    }

    public void setVoiture(Collection<Voiture> voiture) {
        this.voiture = voiture;
    }

    public Collection<Facturation> getFacturation() {
        return facturation;
    }

    public void setFacturation(Collection<Facturation> facturation) {
        this.facturation = facturation;
    }

    @Override
    public String toString() {
        return "Client{"
                + "id="
                + id
                + ", prenom='"
                + prenom
                + '\''
                + ", nom='"
                + nom
                + '\''
                + ", adresse='"
                + rue + ' ' + codePostal + ' ' + ville +
                + '\''
                + ", email='"
                + email
                + '\''
                + ", telephone="
                + telephone
                + ", numAfpa="
                + numAfpa
                + '}';
    }
}
