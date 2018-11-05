package com.carrosserieafpa.carrosserie.web.controller;

import com.carrosserieafpa.carrosserie.dao.*;
import com.carrosserieafpa.carrosserie.entity.Acte;
import com.carrosserieafpa.carrosserie.entity.Finition;
import com.carrosserieafpa.carrosserie.entity.Prestation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@SessionAttributes({"client", "prestation", "prestations"})
@Controller
public class ControllerAdmin {

  @Autowired ActeDao acteDao;
  @Autowired FinitionDao finitionDao;
  @Autowired PrestationDao prestationDao;
  @Autowired FacturationDao facturationDao;
  @Autowired VoitureDao voitureDao;
  @Autowired ClientDao clientDao;


  @ModelAttribute("prestation")
  public Prestation getPrestation(){
    return new Prestation();
  }

  @ModelAttribute("prestations")
  public List<Prestation> getPrestas(){
    return new ArrayList<>();}

  @PostMapping("/acte")
  public String ajouterNouveauActe(HttpServletRequest httpServletRequest, Acte acte) {

    acte.setLibelle(httpServletRequest.getParameter("libelle"));

    acteDao.save(acte);

    return "redirect:/admin";
  }

  @PostMapping("/finition")
  public String ajouterNouveauFinition(HttpServletRequest httpServletRequest, Finition finition) {

    finition.setLibelle(httpServletRequest.getParameter("libelle2"));
    finitionDao.save(finition);

    return "redirect:/admin";
  }

  @PostMapping("/prix")
  public String ajouterPrix(HttpServletRequest httpServletRequest, Prestation prestation) {

    prestation.setPrix(Double.valueOf(httpServletRequest.getParameter("libelle3")));
    prestationDao.save(prestation);

    return "redirect:/admin";
  }

  @RequestMapping("/admin/delete/acte")
  public String supprimerUnActe(
      Acte acte,
      HttpServletRequest httpServletRequest,
      @ModelAttribute("prestation") Prestation prestation) /*@PathVariable ("id") Long id*/ {

    String ActeASupprimer = httpServletRequest.getParameter("acte");
    Acte acteSupprimer = prestation.getActe();
    acteDao.delete(acteSupprimer);

    return "redirect:/admin";
  }

  @RequestMapping("/admin/delete/finition")
  public String supprimerUneFinition(
      Finition finition,
      HttpServletRequest httpServletRequest,
      @ModelAttribute("prestation") Prestation prestation) /*@PathVariable ("id") Long id*/ {

    String FinitionASupprimer = httpServletRequest.getParameter("finition");
    Finition finitionSupprimer = prestation.getFinition();
    finitionDao.delete(finitionSupprimer);

    return "redirect:/admin";
  }
}
