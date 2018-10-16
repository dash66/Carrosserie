package com.carrosserieafpa.carrosserie.dao;

import com.carrosserieafpa.carrosserie.entity.Facturation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FacturationDao extends JpaRepository<Facturation, Long> {

    @Query("SELECT '*' FROM Facturation JOIN Client ON facturation.client_id =client.id JOIN Voiture ON client.id = Voiture.client_id WHERE client.id =:id")
    Facturation rechercheClientEtInfoParId(@Param("id") Long id);

    @Query("SELECT '*' FROM Facturation WHERE Facturation.id = :id")
    Long rechercherFactureParId (@Param("id") Long Id);
}
