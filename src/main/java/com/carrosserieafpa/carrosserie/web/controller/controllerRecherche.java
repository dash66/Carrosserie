package com.carrosserieafpa.carrosserie.web.controller;

import com.carrosserieafpa.carrosserie.dao.*;
import com.carrosserieafpa.carrosserie.entity.Client;
import com.carrosserieafpa.carrosserie.entity.Facturation;
import com.carrosserieafpa.carrosserie.entity.Prestation;
import com.carrosserieafpa.carrosserie.entity.Voiture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SessionAttributes({"client", "prestation", "prestations"})
@Controller
public class controllerRecherche {

  @Autowired ActeDao acteDao;
  @Autowired FinitionDao finitionDao;
  @Autowired PrestationDao prestationDao;
  @Autowired FacturationDao facturationDao;
  @Autowired VoitureDao voitureDao;
  @Autowired ClientDao clientDao;

  @ModelAttribute("client")
  public Client getClient() {
    return new Client();
  }

  @ModelAttribute("prestation")
  public Prestation getPrestation() {
    return new Prestation();
  }

  @ModelAttribute("prestations")
  public List<Prestation> getPrestas() {
    return new ArrayList<>();
  }

  @PostMapping(value = "/rechercherClient") // fonction de la page archive
  public String rechercheClient(
      @ModelAttribute("client") Client michaelKnight,
      Model model,
      HttpServletRequest httpServletRequest) {

    String prenom = httpServletRequest.getParameter("prenom3");
    String nom = httpServletRequest.getParameter("nom3");

    Long id = clientDao.rechercherClientParNometPrenom(nom, prenom);
    Optional<Client> client = clientDao.findById(id);
    michaelKnight = client.get();

    Voiture k2000 = voitureDao.findVoitureByClient(id);

    model.addAttribute("client", michaelKnight);
    model.addAttribute("voiture", k2000);

    return "form-archive";
  }

  @PostMapping(value = "/rechercherVoiture") // fonction de la page archive
  public String rechercheVoiture(Model model, HttpServletRequest httpServletRequest) {

    String immat = httpServletRequest.getParameter("Immatriculation");
    String marque = httpServletRequest.getParameter("Marque");
    String modele = httpServletRequest.getParameter("model");

    Long numFacture = Long.valueOf(httpServletRequest.getParameter("numFacture"));
    Optional<Facturation> facturation = facturationDao.findById(numFacture);
    Facturation facturation1 = facturation.get();

    Voiture k2000 = voitureDao.rechercherVoitureparImmat(immat);
    model.addAttribute("voiture", k2000);

    Long id = k2000.getClient().getId();
    Client clint = clientDao.findByImmat(id);
    model.addAttribute("client", clint);
    model.addAttribute("fature", facturation1);


    return "form-archive";
  }

  @PostMapping(value = "/rechercherFacture") // fonction de la page archive
  public String rechercheFacture(Model model, HttpServletRequest httpServletRequest) {

    Long numFacture = Long.valueOf(httpServletRequest.getParameter("numFacture"));

    Optional<Facturation> facturation = facturationDao.findById(numFacture);
    Facturation facturation1 = facturation.get();
    Client clint = clientDao.findByImmat(facturation1.getClient().getId());
    Voiture granTorino = voitureDao.findVoitureByClient(facturation1.getClient().getId());

    model.addAttribute("client", clint);
    model.addAttribute("voiture", granTorino);
    model.addAttribute("fature", facturation1);

    return "form-archive";
  }

  @GetMapping("/rechercheClientExistant")
  public String rechercheClientExistant(Model model, HttpServletRequest httpServletRequest) {

    String prenom = httpServletRequest.getParameter("prenom3");
    String nom = httpServletRequest.getParameter("nom3");

    Long id = clientDao.rechercherClientParNometPrenom(nom, prenom);
    Optional<Client> client = clientDao.findById(id);
    Client michaelKnight = client.get();

    model.addAttribute("client", michaelKnight);

    return "redirect:/vueEnregistrement";
  }

  @PostMapping("/rechercheVoitureExistant")
  public String rechercheVoitureExistant(
      Model model, HttpServletRequest httpServletRequest, @ModelAttribute("client") Client client) {

    String immat = httpServletRequest.getParameter("Immatriculation");

    Voiture k2000 = voitureDao.rechercherVoitureparImmat(immat);
    model.addAttribute("voiture", k2000);

    Optional<Client> clint = clientDao.findById(k2000.getClient().getId());

    client = clint.get();
    model.addAttribute("client", client);

    return "redirect:/vueEnregistrement";
  }

  @PostMapping("/rechercheFactureExistant")
  public String rechercheFactureExistant(Model model, HttpServletRequest httpServletRequest) {

    Long numFacture = Long.valueOf(httpServletRequest.getParameter("facture"));

    Optional<Facturation> facturation = facturationDao.findById(numFacture);
    Facturation facturation1 = facturation.get();
    Client clint = clientDao.findByImmat(facturation1.getClient().getId());
    Voiture granTorino = voitureDao.findVoitureByClient(facturation1.getClient().getId());

    model.addAttribute("client", clint);
    model.addAttribute("voiture", granTorino);
    model.addAttribute("fature", facturation1);

    return "redirect:/vueEnregistrement";
  }
}
