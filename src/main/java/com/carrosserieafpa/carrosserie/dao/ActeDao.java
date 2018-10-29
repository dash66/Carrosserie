package com.carrosserieafpa.carrosserie.dao;

import com.carrosserieafpa.carrosserie.entity.Acte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ActeDao extends JpaRepository<Acte, Long> {

  @Query("SELECT '*' FROM Acte WHERE libelle = :libelle")
  public Acte findByLibelle(@Param("libelle") String libelle);
}
