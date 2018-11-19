package com.carrosserieafpa.carrosserie.dao;

import com.carrosserieafpa.carrosserie.entity.Client;
import com.carrosserieafpa.carrosserie.entity.Facturation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientDao extends JpaRepository<Client, Long> {


    Client findClientByNomAndPrenom(String nom, String prenom);

    @Query("Select new Client(prenom, nom, adresse, email, telephone, numAfpa) FROM Client WHERE id = :id")
    Client findByImmat(@Param("id") Long id);

    Client findClientByFacturation(Facturation facturation);

    @Query("SELECT c FROM Client c ORDER BY nom")
    List<Client> findAllClientOrderByNom();
    }
