package com.carrosserieafpa.carrosserie.dao;

import com.carrosserieafpa.carrosserie.entity.Prestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrestationDao extends JpaRepository<Prestation, Long> {

    @Query("SELECT a.acte FROM Acte AS a JOIN Prestation AS p ON p.acte=a.id_acte WHERE a.id_acte = :id_acte")
    String findJointureActe(@Param("id_acte") Long id_acte);
}
