package com.carrosserieafpa.carrosserie.dao;

import com.carrosserieafpa.carrosserie.entity.Acte;
import com.carrosserieafpa.carrosserie.entity.Finition;
import com.carrosserieafpa.carrosserie.entity.Prestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrestationDao extends JpaRepository<Prestation, Long> {

    @Query("SELECT id_presta FROM Prestation WHERE id_acte = :acte AND id_finition = :finition")
    Long findIdByActeAndFinition(@Param("acte") Acte acte, @Param("finition") Finition finition);

    @Query("SELECT prix FROM Prestation WHERE id_presta = :id")
    Double findPrixById(@Param("id") Long id);

    Prestation findByActeAndFinition(Acte acte, Finition finition);
}
