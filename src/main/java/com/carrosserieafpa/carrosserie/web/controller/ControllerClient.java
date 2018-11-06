package com.carrosserieafpa.carrosserie.web.controller;

import com.carrosserieafpa.carrosserie.dao.*;
import com.carrosserieafpa.carrosserie.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SessionAttributes({"client", "prestations", "voiture", "facturation"})
@Controller
public class ControllerClient {

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

  @ModelAttribute("prestations")
  public List<Prestation> getPrestas() {
    return new ArrayList<>();
  }

  @ModelAttribute("voiture")
  public Voiture getVoiture() {
    return new Voiture();
  }

  @ModelAttribute("facturation")
  public Facturation getFacturation() {
    return new Facturation();
  }

  @PostMapping("/saveClient")
  public String enregistrementClient(
      HttpServletRequest request, @ModelAttribute("client") Client client) {
    client = this.creationClient(client, request);
    Voiture voiture = this.creationVoiture(client, request);

    if (client.getNom() != null) {
      clientDao.save(client);
      voitureDao.save(voiture);
    }
    System.out.println(client);
    return "redirect:/addPresta";
  }

  @RequestMapping("/createNewVehicule")
  public String creationNewVoiture(
      @ModelAttribute("client") Client client, HttpServletRequest request) {
    Voiture voiture = new Voiture();
    voiture.setMarque(request.getParameter("marque"));
    voiture.setImmat(request.getParameter("immat"));
    voiture.setModele(request.getParameter("modele"));
    voiture.setCodeCouleur(request.getParameter("couleur"));
    voiture.setCategorie(request.getParameter("categorie"));

    String date = request.getParameter("date");
    Date date1 = null;
    try {
      date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    String dateDef = new SimpleDateFormat("dd-MM-yyyy").format(date1);

    voiture.setDate(dateDef);
    voiture.setClient(client);

    client.getVoiture().add(voiture);

    voitureDao.save(voiture);

    return "redirect:/vueEnregistrement";
  }

  @RequestMapping("/setVoitureClient")
  public String settageVoitureClient(
      @ModelAttribute("voiture") Voiture voiture, HttpServletRequest request, Model model) {
    Long id = Long.valueOf(request.getParameter("vehicule"));
    Optional<Voiture> tuture = voitureDao.findById(id);
    if (tuture.isPresent()) {
      voiture = tuture.get();
    }
    model.addAttribute("voiture", voiture);

    return "redirect:/vueEnregistrement";
  }

  @RequestMapping("addPresta")
  public String ajouterPrestaList(
      RedirectAttributes ra,
      @ModelAttribute("prestation") Prestation prestation,
      @ModelAttribute("client") Client client,
      @ModelAttribute("voiture") Voiture voiture) {
    Acte acte = prestation.getActe();
    Finition finition = prestation.getFinition();

    prestation.setId_presta(prestationDao.FindIdByActeAndFinition(acte, finition));
    try {
      prestation.setPrix(prestationDao.findPrixById(prestation.getId_presta()));
    } catch (NullPointerException e) {
    }
    ra.addAttribute(prestation);
    return "redirect:/savePresta";
  }

  @RequestMapping("/savePresta")
  public String sauvegarderPrestas(
      @ModelAttribute("prestation") Prestation prestation,
      @ModelAttribute("client") Client client,
      @ModelAttribute("prestations") List<Prestation> prestations,
      Model model) {
    prestations.add(prestation);

    model.addAttribute("prestations", prestations);
    model.addAttribute("prestation", prestation);
    return "redirect:/vueEnregistrement";
  }

  @RequestMapping("/saveFacture")
  public String sauvegarderFacture(
      @ModelAttribute("prestations") List<Prestation> prestations,
      @ModelAttribute("client") Client client,
      @ModelAttribute("voiture") Voiture voiture,
      @ModelAttribute("facturation") Facturation facturation,
      RedirectAttributes ra) {

    Collection<Prestation> prestationsColl = new HashSet<>();

    prestationsColl.addAll(prestations);
    facturation.setPrestation(prestationsColl);

    Double prixFactureCalcule = calculPrixFinal(prestations, voiture);

    facturation.setPrix(prixFactureCalcule);
    LocalDateTime HeureNonFormatee = LocalDateTime.now();
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy à HH:mm");
    String formatDateTime = HeureNonFormatee.format(timeFormatter);
    facturation.setDate(formatDateTime);
    facturation.setClient(client);
    facturation.setVoiture(voiture);
    facturationDao.save(facturation);

    ra.addAttribute("client", client);

    return "redirect:/facturation";
  }

  public Double calculPrixFinal(
      @ModelAttribute("prestations") List<Prestation> prestations,
      @ModelAttribute("voiture") Voiture voiture) {

    Double prixTotalFacture = 0.0;
    for (Prestation presta : prestations) {
      Double prixParPresta = presta.getPrix();
      prixTotalFacture = prixTotalFacture + prixParPresta;
    }

    Double prixFinal = prixTotalFacture;
    switch (voiture.getCategorie()) {
      case "petit":
        prixFinal -= prixTotalFacture * 0.1;
        break;
      case "moyen":
        prixFinal = prixTotalFacture;
        break;
      case "gros":
        prixFinal += prixTotalFacture * 0.1;
        break;
      case "fourgon":
        prixFinal += prixTotalFacture * 0.2;
        break;
    }
    return prixFinal;
  }

  public Client creationClient(
      @ModelAttribute("client") Client client, HttpServletRequest request) {

    client.setPrenom(request.getParameter("prenom"));
    client.setNom(request.getParameter("nom"));
    client.setAdresse(request.getParameter("adresse"));
    try {
      client.setTelephone(Integer.valueOf(request.getParameter("telephone")));
    } catch (NumberFormatException e) {
    }
    client.setEmail(request.getParameter("email"));
    client.setNumAfpa(request.getParameter("numAfpa"));

    return client;
  }

  public Voiture creationVoiture(
      @ModelAttribute("client") Client client, HttpServletRequest request) {

    Voiture voiture = new Voiture();
    voiture.setMarque(request.getParameter("marque"));
    voiture.setImmat(request.getParameter("immat"));
    voiture.setModele(request.getParameter("modele"));
    voiture.setCodeCouleur(request.getParameter("couleur"));
    voiture.setCategorie(request.getParameter("categorie"));
    SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyyy");

    String date = request.getParameter("date");
    Date date1 = null;
    try {
      date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    String dateDef = new SimpleDateFormat("dd-MM-yyyy").format(date1);

    voiture.setDate(dateDef);
    voiture.setClient(client);

    return voiture;
  }
}
