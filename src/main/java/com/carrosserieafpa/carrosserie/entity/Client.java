package com.carrosserieafpa.carrosserie.entity;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.lang.Nullable;
import javax.persistence.*;
import java.util.Collection;

@ToString(exclude = {"voiture"})
@Data
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


}
