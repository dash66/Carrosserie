package com.carrosserieafpa.carrosserie.dao;

import com.carrosserieafpa.carrosserie.entity.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoitureDao extends JpaRepository<Voiture, Long> {

    @Query("SELECT '*' FROM Voiture WHERE immat = :immat")
    Long rechercherVoitureparImmat (@Param("immat") String immat);

    @Query("SELECT categorie FROM Voiture WHERE client_id = :id")
    String rechercherCategorieVoitureParId(@Param("id") Long id);
}
