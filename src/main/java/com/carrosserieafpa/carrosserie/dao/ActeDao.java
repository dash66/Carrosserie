package com.carrosserieafpa.carrosserie.dao;

import com.carrosserieafpa.carrosserie.entity.Acte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActeDao extends JpaRepository<Acte, Long> {

    @Query("SELECT a FROM Acte a ORDER BY libelle")
    List<Acte> findActesOrderByNom();
}

