package com.carrosserieafpa.carrosserie.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Data
@Entity
public class Finition implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id_finition;

  private String libelle;

  @OneToMany(mappedBy = "finition", cascade = CascadeType.REMOVE)
  private Collection<Prestation> prestations;

  public Finition() {}

  @Override
  public String toString() {
    return libelle;
  }
}
