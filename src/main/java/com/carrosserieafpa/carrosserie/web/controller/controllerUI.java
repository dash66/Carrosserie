package com.carrosserieafpa.carrosserie.web.controller;

import com.carrosserieafpa.carrosserie.dao.ActeDao;
import com.carrosserieafpa.carrosserie.dao.FacturationDao;
import com.carrosserieafpa.carrosserie.dao.FinitionDao;
import com.carrosserieafpa.carrosserie.dao.PrestationDao;
import com.carrosserieafpa.carrosserie.entity.Acte;
import com.carrosserieafpa.carrosserie.entity.Facturation;
import com.carrosserieafpa.carrosserie.entity.Finition;
import com.carrosserieafpa.carrosserie.entity.Prestation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class controllerUI {

  @Autowired ActeDao acteDao;

  @Autowired FinitionDao finitionDao;

  @Autowired PrestationDao prestationDao;

  @Autowired FacturationDao facturationDao;

  @RequestMapping(value = {"/menu", "/"})
  public String menu(Model model) {

    return "form-menu";
  }

  @RequestMapping("/consulter")
  public String consulter(Model model) {

    return "form-consulter";
  }

  @GetMapping("/enregistrer")
  public String enregistrer(Model model) {

    /*
    Pour listes déroulantes
     */
    List<Finition> finitions = finitionDao.findAll();
    List<Acte> actes = acteDao.findAll();

    /*
    Pour tableau de prestations
     */
    List<Facturation> factures = facturationDao.findAll();

    Prestation prestation = new Prestation();

    model.addAttribute("prestation", prestation);
    model.addAttribute("factures", factures);
    model.addAttribute("actes", actes);
    model.addAttribute("finitions", finitions);

    return "form-enregistrement";
  }

  @PostMapping("/enregistrer")
  public String ajouterPresta(Prestation prestation) {

    System.out.println(prestation.toString());

    Acte acte = prestation.getActe();
    //  System.out.println(acte.toString());

    Finition finition = prestation.getFinition();
    //  System.out.println(finition.toString());

    prestation.setId_presta(prestationDao.FindIdByActeAndFinition(acte, finition));

    Facturation facture = new Facturation();
    facture.setPrestation(prestation);
    facturationDao.save(facture);

    return "form-enregistrement";
  }

  @PostMapping("/ajouterActe")
  public String ajouterActe(Acte acte, HttpServletRequest httpServletRequest) {

    acte.setLibelle(httpServletRequest.getParameter("libelle"));
    acteDao.save(acte);

    return "form-enregistrement";
  }

  @PostMapping("/ajouterFinition")
  public String ajouterFinition(Finition finition, HttpServletRequest httpServletRequest) {

    finition.setLibelle(httpServletRequest.getParameter("libelle2"));
    finitionDao.save(finition);

    return "form-enregistrement";
  }
}
