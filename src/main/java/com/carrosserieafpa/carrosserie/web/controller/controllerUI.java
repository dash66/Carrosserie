package com.carrosserieafpa.carrosserie.web.controller;

import com.carrosserieafpa.carrosserie.dao.*;
import com.carrosserieafpa.carrosserie.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class controllerUI {

  @Autowired ActeDao acteDao;

  @Autowired FinitionDao finitionDao;

  @Autowired PrestationDao prestationDao;

  @Autowired FacturationDao facturationDao;

  @Autowired VoitureDao voitureDao;

  @Autowired ClientDao clientDao;

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


    //test pull

    return "form-enregistrement";
  }

  @PostMapping("/enregistrer")
  public String ajouterPresta(Prestation prestation) {

    Acte acte = prestation.getActe();

    Finition finition = prestation.getFinition();
    Long id = prestationDao.FindIdByActeAndFinition(acte, finition);

    prestation.setId_presta(id);
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

  @PostMapping("/saveClientAndCar")
  public String ajouterClientEtVoiture(Client client, Voiture voiture, HttpServletRequest request) {
    client.setPrenom(request.getParameter("prenom"));
    client.setNom(request.getParameter("nom"));
    client.setAdresse(request.getParameter("adresse"));
    client.setTelephone(Integer.valueOf(request.getParameter("telephone")));
    client.setEmail(request.getParameter("email"));
    client.setNumAfpa(Long.valueOf(request.getParameter("numAfpa")));

    clientDao.save(client);

    voiture.setMarque(request.getParameter("marque"));
    voiture.setImmat(request.getParameter("immat"));
    voiture.setModele(request.getParameter("modele"));
    voiture.setCodeCouleur(request.getParameter("couleur"));
    SimpleDateFormat dt = new SimpleDateFormat("yyyy-mm-dd");
    Date date = null;
    try {
      date = dt.parse(request.getParameter("date"));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    voiture.setDate(date);

    voiture.setClient(client);

    voitureDao.save(voiture);

    return "form-enregistrement";
  }

  @GetMapping("/recherche")
  public String effectuerUnerecherche(Client client, HttpServletRequest httpServletRequest) {

    Long clientId =
        clientDao.rechercherClientParNometPrenom(
            httpServletRequest.getParameter("nom"), httpServletRequest.getParameter("prenom"));

    Facturation resultatRecherche =
        facturationDao.rechercheClientEtInfoParId(
            Long.valueOf(httpServletRequest.getParameter("id")));

    return "form-enregistrement";
  }
}
