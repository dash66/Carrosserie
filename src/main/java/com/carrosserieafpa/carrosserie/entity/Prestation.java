package com.carrosserieafpa.carrosserie.entity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

import javax.persistence.*;

import java.util.Collection;

@Entity
public class Prestation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_presta;

    @Nullable
    private double prix;

    @ManyToOne()
    @JoinColumn(name = "id_acte", referencedColumnName = "acte")
    private Acte acte;

    @ManyToOne()
    @JoinColumn(name = "id_finition", referencedColumnName = "id_finition")
    private Finition finition;

    @OneToMany(mappedBy = "prestation", cascade = CascadeType.MERGE)
    private Collection<Facturation> facturations;


    public Prestation(double prix, Acte acte, Finition finition, Collection<Facturation> facturations) {
        this.prix = prix;
        this.acte = acte;
        this.finition = finition;
//        this.facturations = facturations;
    }

    public Prestation() {
    }

    public void setActe(Acte acte) {
        this.acte = acte;
    }

   /* public Collection<Facturation> getFacturations() {
        return facturations;
    }

    public void setFacturations(Collection<Facturation> facturations) {
        this.facturations = facturations;
    }*/

    public Long getId_presta() {
        return id_presta;
    }

    public void setId_presta(Long id_presta) {
        this.id_presta = id_presta;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Acte getActe() {
        return acte;
    }

    public Finition getFinition() {
        return finition;
    }

    public void setFinition(Finition finitions) {
        this.finition = finitions;
    }

    @Override
    public String toString() {
        return "Prestation{" +
                "id_presta=" + id_presta +
                ", prix=" + prix +
                ", acte=" + acte +
                ", finition=" + finition +
                '}';
    }
}
