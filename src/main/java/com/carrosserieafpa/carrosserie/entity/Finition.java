package com.carrosserieafpa.carrosserie.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
public class Finition implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id_finition;

  private String libelle;

  @OneToMany(mappedBy = "finition", cascade = CascadeType.REMOVE)
  private Collection<Prestation> prestations;

  public Finition() {}

  public Long getId_finition() {
    return id_finition;
  }

  public void setId_finition(Long id_finition) {
    this.id_finition = id_finition;
  }

  public String getLibelle() {
    return libelle;
  }

  public void setLibelle(String libelle) {
    this.libelle = libelle;
  }

  public Collection<Prestation> getPrestations() {
    return prestations;
  }

  public void setPrestations(Collection<Prestation> prestations) {
    this.prestations = prestations;
  }

  @Override
  public String toString() {
    return libelle;
  }
}
