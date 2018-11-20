package com.carrosserieafpa.carrosserie.dao;

import com.carrosserieafpa.carrosserie.entity.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminDao extends JpaRepository<Administrateur, Long> {

    public Administrateur findByMotDePasse (String motDePasse);

}
