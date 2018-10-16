package com.carrosserieafpa.carrosserie.dao;

import com.carrosserieafpa.carrosserie.entity.Facturation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FacturationDao extends JpaRepository<Facturation, Long> {

    @Query("SELECT '*' FROM Facturation AS f JOIN Client AS c ON f.client = id JOIN Voiture AS v ON id = v.client WHERE id =:id")
    String rechercheClientEtInfoParId(@Param("id") Long id);

    @Query("SELECT '*' FROM Facturation WHERE id = :id")
    Long rechercherFactureParId (@Param("id") Long Id);
}
