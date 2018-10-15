package com.carrosserieafpa.carrosserie.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
public class Finition  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_finition;
    private String finition;

    @OneToMany(mappedBy = "finition")
    private Collection<Prestation> prestations;

    public Finition(String finition) {
        this.finition = finition;
    }

    public Long getId_finition() {
        return id_finition;
    }

    public void setId_finition(Long id_finition) {
        this.id_finition = id_finition;
    }

    public String getFinition() {
        return finition;
    }

    public void setFinition(String finition) {
        this.finition = finition;
    }

    public Finition() {
    }

    @Override
    public String toString() {
        return "Finition{" +
                "id_finition=" + id_finition +
                ", finition='" + finition + '\'' +
                '}';
    }
}
