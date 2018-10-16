package com.carrosserieafpa.carrosserie.dao;

import com.carrosserieafpa.carrosserie.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientDao extends JpaRepository<Client, Long> {

    @Query("SELECT id FROM Client WHERE Client.nom = :nom and Client.prenom =:prenom;")

}
