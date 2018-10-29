package com.carrosserieafpa.carrosserie.entity;

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
  private String adresse;

  @Nullable
  private String email;

  private int telephone;
  private String numAfpa;

  @OneToMany(mappedBy = "client")
  private Collection<Voiture> voiture;

  @OneToMany(mappedBy = "client")
  private Collection<Facturation> facturation;

  public Client(String prenom, String nom, @Nullable String adresse, @Nullable String email, int telephone, String numAfpa, Collection<Voiture> voiture, Collection<Facturation> facturation) {
    this.prenom = prenom;
    this.nom = nom;
    this.adresse = adresse;
    this.email = email;
    this.telephone = telephone;
    this.numAfpa = numAfpa;
    this.voiture = voiture;
    this.facturation = facturation;
  }

  public Client(String prenom, String nom, @Nullable String adresse, @Nullable String email, int telephone, String numAfpa) {
    this.prenom = prenom;
    this.nom = nom;
    this.adresse = adresse;
    this.email = email;
    this.telephone = telephone;
    this.numAfpa = numAfpa;
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

  public String getAdresse() {
    return adresse;
  }

  public void setAdresse(String adresse) {
    this.adresse = adresse;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getTelephone() {
    return telephone;
  }

  public void setTelephone(int telephone) {
    this.telephone = telephone;
  }

  public String getNumAfpa() {
    return numAfpa;
  }

  public void setNumAfpa(String numAfpa) {
    this.numAfpa = numAfpa;
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
        + adresse
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
