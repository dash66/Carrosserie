package com.carrosserieafpa.carrosserie.dao;

import com.carrosserieafpa.carrosserie.entity.Client;
import com.carrosserieafpa.carrosserie.entity.Facturation;
import com.carrosserieafpa.carrosserie.entity.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoitureDao extends JpaRepository<Voiture, Long> {


    Voiture findByImmat (String immat);

    List<Voiture> findVoitureByClient(Client client);

    Voiture findVoitureByFacturation(Facturation facturation);
}
