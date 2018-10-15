package com.carrosserieafpa.carrosserie.dao;

import com.carrosserieafpa.carrosserie.entity.Prestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrestationDao extends JpaRepository<Prestation, Long> {

    @Query("SELECT id_presta FROM Prestation WHERE id_acte = :id_acte AND id_finition = :id_finition")
    Long FindIdByActeAndFinition(@Param("id_acte") Long id_acte, @Param("id_finition") Long id_finition);

}
