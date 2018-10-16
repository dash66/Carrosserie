package com.carrosserieafpa.carrosserie.dao;

import com.carrosserieafpa.carrosserie.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientDao extends JpaRepository<Client, Long> {

    @Query("SELECT id FROM Client WHERE nom = :nom and prenom =:prenom")
    Long rechercherClientParNometPrenom (@Param("nom") String nom, @Param("prenom") String prenom);
}
