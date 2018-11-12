package com.carrosserieafpa.carrosserie.dao;

import com.carrosserieafpa.carrosserie.entity.Acte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActeDao extends JpaRepository<Acte, Long> {

}

