package com.carrosserieafpa.carrosserie.dao;

import com.carrosserieafpa.carrosserie.entity.Finition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FinitionDao extends JpaRepository<Finition, Long> {
/*    @Query("SELECT id FROM Finition WHERE finition = :finition")
    Long findByFinition(@Param("finition") String finition)*/;
}
