package com.carrosserieafpa.carrosserie.dao;

import com.carrosserieafpa.carrosserie.entity.Finition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinitionDao extends JpaRepository<Finition, Long> {

    @Query("SELECT f FROM Finition f ORDER BY libelle")
    List<Finition> findFinitionsOrderByNom();

}
