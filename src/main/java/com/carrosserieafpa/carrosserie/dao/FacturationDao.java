package com.carrosserieafpa.carrosserie.dao;

import com.carrosserieafpa.carrosserie.entity.Client;
import com.carrosserieafpa.carrosserie.entity.Facturation;
import com.carrosserieafpa.carrosserie.entity.Prestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FacturationDao extends JpaRepository<Facturation, Long> {

    @Query("SELECT prix FROM Facturation WHERE client_id =:id")
    Double recherchePrixFactureParIdClient(@Param("id") Long id);

    @Query("SELECT prestation FROM Facturation WHERE client_id =:id")
    List<Prestation> recherchePrestationDansFactureParClientId(@Param("id") Long id);

  List<Facturation> findFacturationByClient(Client client);
}
