package com.carrosserieafpa.carrosserie.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String prenom;
  private String nom;
  private String adresse;
  private String email;
  private int telephone;
  private Long numAfpa;

  @OneToMany(mappedBy = "client")
  private Collection<Voiture> voiture;

  @OneToMany(mappedBy = "client")
  private Collection<Facturation> facturation;

  public Client(
      Long id,
      String prenom,
      String nom,
      String adresse,
      String email,
      int telephone,
      Long numAfpa) {
    this.id = id;
    this.prenom = prenom;
    this.nom = nom;
    this.adresse = adresse;
    this.email = email;
    this.telephone = telephone;
    this.numAfpa = numAfpa;
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

  public Long getNumAfpa() {
    return numAfpa;
  }

  public void setNumAfpa(Long numAfpa) {
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
