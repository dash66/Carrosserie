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

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SessionAttributes({"client", "prestation", "prestations"})
@Controller
public class controllerClient {

  @Autowired ActeDao acteDao;
  @Autowired FinitionDao finitionDao;
  @Autowired PrestationDao prestationDao;
  @Autowired FacturationDao facturationDao;
  @Autowired VoitureDao voitureDao;
  @Autowired ClientDao clientDao;

  @ModelAttribute("prestation")
  public Prestation getPrestation() {
    return new Prestation();
  }

  @ModelAttribute("prestations")
  public List<Prestation> getPrestas() {
    return new ArrayList<>();
  }

  @PostMapping("/saveClient")
  public String enregistrementClient(
      HttpServletRequest request,
      @ModelAttribute("client") Client client,
      @ModelAttribute("voiture") Voiture voiture) {
    client = this.creationClient(client, request);
    voiture = this.creationVoiture(client, request);

    if (client.getNom() != null) {
      clientDao.save(client);
      voitureDao.save(voiture);
    }
    System.out.println(client);
    return "redirect:/addPresta";
  }

  @RequestMapping("addPresta")
  public String ajouterPrestaList(
      @ModelAttribute("prestation") Prestation prestation,
      @ModelAttribute("client") Client client, @ModelAttribute("prestations") List<Prestation> prestations) {

    Acte acte = prestation.getActe();
    Finition finition = prestation.getFinition();

    prestation.setId_presta(prestationDao.FindIdByActeAndFinition(acte, finition));
    try {
      prestation.setPrix(prestationDao.findPrixById(prestation.getId_presta()));
    } catch (NullPointerException e) {
    }
    prestations.add(prestation);
    for (Prestation presta : prestations) {
      System.out.println(presta);
    }
    return "redirect:/vueEnregistrement";
  }

  @RequestMapping("/savePresta")
  public String sauvegarderPrestas(
      @ModelAttribute("prestation") Prestation prestation,
      @ModelAttribute("client") Client client,
      Model model) {
    List<Prestation> prestations = new ArrayList<>();
    prestations.add(prestation);
    for (Prestation presta : prestations) {
      System.out.println(presta);
    }
    model.addAttribute("prestation", prestations);
    return "redirect:/vueEnregistrement";
  }

  public String retrouverCatégorieVoiturePourClientExistant(
      Model model, HttpServletRequest httpServletRequest) {

    Long clientId =
        clientDao.rechercherClientParNometPrenom(
            httpServletRequest.getParameter("nom"), httpServletRequest.getParameter("prenom"));

    String categorieClientExistant = voitureDao.rechercherCategorieVoitureParId(clientId);

    return categorieClientExistant;
  }

  public Double calculPrixFinal(List<Prestation> prestations, Voiture voiture) {

    Double prixTotalFacture = null;
    for (Prestation presta : prestations) {
      Double prixParPresta = presta.getPrix();
      prixTotalFacture += prixParPresta;
    }

    Double prixFinal = null;
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

  public Voiture creationVoiture(Client client, HttpServletRequest request) {

    Voiture voiture = new Voiture();
    voiture.setMarque(request.getParameter("marque"));
    voiture.setImmat(request.getParameter("immat"));
    voiture.setModele(request.getParameter("modele"));
    voiture.setCodeCouleur(request.getParameter("couleur"));
    voiture.setCategorie(request.getParameter("categorie"));
    SimpleDateFormat dt = new SimpleDateFormat("yyyy-mm-dd");
    Date date = null;
    try {
      date = dt.parse(request.getParameter("date"));
    } catch (ParseException e) {
      e.printStackTrace();
    } catch (NullPointerException e) {

    }
    voiture.setDate(date);
    voiture.setClient(client);

    return voiture;
  }
}
