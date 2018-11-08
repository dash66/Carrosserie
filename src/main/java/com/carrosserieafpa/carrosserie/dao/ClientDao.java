package com.carrosserieafpa.carrosserie.dao;

import com.carrosserieafpa.carrosserie.entity.Client;
import com.carrosserieafpa.carrosserie.entity.Facturation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientDao extends JpaRepository<Client, Long> {

    @Query("SELECT id FROM Client WHERE nom = :nom AND prenom = :prenom")
    Long rechercherClientParNometPrenom(@Param("nom") String nom, @Param("prenom") String prenom);

    @Query("SELECT MAX(id) FROM Client")
    Long findLastId();

    @Query("Select new Client(prenom, nom, adresse, email, telephone, numAfpa) FROM Client WHERE id = :id")
    Client findByImmat(@Param("id") Long id);

    @Query("SELECT facturation FROM Client WHERE client_id=:id")
    List<Facturation> rechercherFacturationsParClient(@Param("id") Long id);

    Client findClientByFacturation(Facturation facturation);
    }
