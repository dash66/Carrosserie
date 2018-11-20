package com.carrosserieafpa.carrosserie.web.controller;

import com.carrosserieafpa.carrosserie.dao.*;
import com.carrosserieafpa.carrosserie.entity.Client;
import com.carrosserieafpa.carrosserie.entity.Facturation;
import com.carrosserieafpa.carrosserie.entity.Prestation;
import com.carrosserieafpa.carrosserie.entity.Voiture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@SessionAttributes({
  "client",
  "prestation",
  "prestations",
  "voiture",
  "facturations",
  "facturation"
})
@Controller
public class ControllerRecherche {

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

  @ModelAttribute("voiture")
  public Voiture getVoiture() {
    return new Voiture();
  }

  @ModelAttribute("prestation")
  public Prestation getPrestation() {
    return new Prestation();
  }

  @ModelAttribute("prestations")
  public List<Prestation> getPrestas() {
    return new ArrayList<>();
  }

  @ModelAttribute("facturation")
  public Facturation getFacturation() {
    return new Facturation();
  }

  @ModelAttribute("facturations")
  public List<Facturation> getFacturations() {
    return new ArrayList<>();
  }

  @RequestMapping(value = "/rechercherClient") // fonction de la page archive
  public String rechercheClient(
      @ModelAttribute("client") Client michaelKnight,
      Model model,
      HttpServletRequest httpServletRequest,
      RedirectAttributes ra) {

    String prenom = httpServletRequest.getParameter("prenom3");
    String nom = httpServletRequest.getParameter("nom3");

    Collection<Facturation> facturations = new HashSet<>();
    Collection<Voiture> k2000 = new HashSet<>();

    try {
      michaelKnight = clientDao.findClientByNomAndPrenom(nom, prenom);
      k2000 = michaelKnight.getVoiture();
      facturations = michaelKnight.getFacturation();
    } catch (NullPointerException e) {
      ra.addFlashAttribute("message", "Ce client n'existe pas !");
      return "redirect:/rechercher";
    }

    model.addAttribute("client", michaelKnight);
    model.addAttribute("facturations", facturations);
    model.addAttribute("voitures", k2000);

    return "redirect:/archive";
  }

  @PostMapping(value = "/rechercherVoiture") // fonction de la page archive
  public String rechercheVoiture(
      RedirectAttributes ra,
      Model model,
      HttpServletRequest httpServletRequest,
      @ModelAttribute("client") Client clint) {

    String immat = httpServletRequest.getParameter("Immatriculation");
    Voiture k2000 = new Voiture();
    List<Facturation> facturations = new ArrayList<>();

    try {
      k2000 = voitureDao.findByImmat(immat);
      Long id = k2000.getClient().getId();
      clint = k2000.getClient();
      facturations = facturationDao.findFacturationByVoiture(k2000);
    } catch (NullPointerException e) {
      ra.addFlashAttribute("message", "Ce véhicule n'existe pas !");
      return "redirect:/rechercher";
    }

    model.addAttribute("voiture", k2000);
    model.addAttribute("client", clint);
    model.addAttribute("facturations", facturations);

    ra.addAttribute("facturations", facturations);
    return "form-archive";
  }

  @RequestMapping(value = "/rechercherFacture") // fonction de la page archive
  public String rechercheFacture(
      Model model,
      RedirectAttributes ra,
      HttpServletRequest httpServletRequest,
      @ModelAttribute("client") Client client,
      @ModelAttribute("voiture") Voiture voiture,
      @ModelAttribute("facturations") List<Facturation> facturations,
      @ModelAttribute("prestations") Collection<Prestation> prestations,
      @ModelAttribute("prestation") Prestation prestation,
      @ModelAttribute("facturation") Facturation facturation) {

    Long numFacture = Long.valueOf(httpServletRequest.getParameter("Robert"));

    try {
      Optional<Facturation> facture = facturationDao.findById(numFacture);
      facturation = facture.get();
      facturations = new ArrayList<>();
      facturations.add(facturation);
      client = clientDao.findClientByFacturation(facturation);
      prestations = facturation.getPrestation();

      voiture = voitureDao.findVoitureByFacturation(facturation);
    } catch (NoSuchElementException e) {
      ra.addFlashAttribute("message", "Cette facture n'existe pas !");
      return "redirect:/rechercher";}

    model.addAttribute("prestations", prestations);
    model.addAttribute("facturation", facturation);
    model.addAttribute("client", client);
    model.addAttribute("voiture", voiture);
    model.addAttribute("facturations", facturations);

    return "redirect:/facturation/" + facturation.getId();
  }

  @RequestMapping("/rechercheClientExistant")
  // fonction de la page enregistrement, doit y retourner : trouver un client existant pour préparer
  // presta
  public String rechercheClientExistant(
      Model model, HttpServletRequest httpServletRequest, RedirectAttributes ra) {

    String prenom = httpServletRequest.getParameter("prenom");
    String nom = httpServletRequest.getParameter("nom");
    Client michaelKnight = new Client();

    try {
      michaelKnight = clientDao.findClientByNomAndPrenom(nom, prenom);
      michaelKnight.getPrenom();
    } catch (NullPointerException e) {
      ra.addFlashAttribute("message", "Ce client n'existe pas !");
      return "redirect:/vueEnregistrement";
    }

    model.addAttribute("client", michaelKnight);

    return "redirect:/vueEnregistrement";
  }

  @RequestMapping(
      "/rechercheVoitureExistant") // fonction de la page enregistrement, doit y retourner : trouver
  // un client existant pour préparer presta
  public String rechercheVoitureExistant(
      Model model,
      HttpServletRequest httpServletRequest,
      @ModelAttribute("client") Client client,
      @ModelAttribute("voiture") Voiture k2000,
      RedirectAttributes ra) {

    String Immatriculation = httpServletRequest.getParameter("Immatriculation");
    Client clint = new Client();

    try {
      k2000 = voitureDao.findByImmat(Immatriculation);
      clint = k2000.getClient();
    } catch (NullPointerException e) {
      ra.addFlashAttribute("message", "Ce véhicule n'existe pas !");
      return "redirect:/vueEnregistrement";
    }
    model.addAttribute("client", clint);
    model.addAttribute("voiture", k2000);

    System.out.println(k2000);
    ra.addAttribute(k2000);

    return "redirect:/vueEnregistrement";
  }

  @RequestMapping("/rechercheFactureExistant")
  // fonction de la page enregistrement, doit y retourner : trouver un client existant pour préparer
  // presta
  public String rechercheFactureExistant(
      Model model, HttpServletRequest httpServletRequest, RedirectAttributes ra) {

    Facturation facturation1 = new Facturation();
    try {
      Long numFacture = Long.valueOf(httpServletRequest.getParameter("facture"));

      Optional<Facturation> Facture = facturationDao.findById(numFacture);
      facturation1 = Facture.get();
    } catch (NumberFormatException e) {
      ra.addFlashAttribute("message", "Cette facture n'existe pas !");
      return "redirect:/vueEnregistrement";
    }

    model.addAttribute("facturation", facturation1);

    return "redirect:/vueEnregistrement";
  }

  @RequestMapping("/setVoitureClientRecherche")
  public String settageVoitureClient(
      @ModelAttribute("voiture") Voiture voiture,
      @ModelAttribute("client") Client client,
      HttpServletRequest request,
      Model model,
      RedirectAttributes ra) {
    Long id = Long.valueOf(request.getParameter("vehicule"));
    Optional<Voiture> tuture = voitureDao.findById(id);
    if (tuture.isPresent()) {
      voiture = tuture.get();
    }

    model.addAttribute("voiture", voiture);

    return "redirect:/archive";
  }
}
