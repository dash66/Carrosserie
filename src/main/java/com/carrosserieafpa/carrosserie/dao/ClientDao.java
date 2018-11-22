package com.carrosserieafpa.carrosserie.dao;

import com.carrosserieafpa.carrosserie.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientDao extends JpaRepository<Client, Long> {


    Client findClientByNomAndPrenom(String nom, String prenom);

    @Query("SELECT c FROM Client c ORDER BY nom")
    List<Client> findAllClientOrderByNom();
    }
