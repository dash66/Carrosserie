package com.carrosserieafpa.carrosserie.web.controller;

import com.carrosserieafpa.carrosserie.dao.*;
import com.carrosserieafpa.carrosserie.entity.*;
import com.sun.deploy.util.SessionState;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.method.annotation.SessionAttributesHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@SessionAttributes({"client", "prestations"})
@Controller
public class controllerClient {

  @Autowired
  ActeDao acteDao;
  @Autowired
  FinitionDao finitionDao;
  @Autowired
  PrestationDao prestationDao;
  @Autowired
  FacturationDao facturationDao;
  @Autowired
  VoitureDao voitureDao;
  @Autowired
  ClientDao clientDao;

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
  public String ajouterPrestaList(RedirectAttributes ra,
                                  @ModelAttribute("prestation") Prestation prestation,
                                  @ModelAttribute("client") Client client/*, @ModelAttribute("prestations") List<Prestation> prestations*/) {
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
  public String sauvegarderPrestas(@ModelAttribute("prestation") Prestation prestation, @ModelAttribute("client") Client client, @ModelAttribute("prestations") List<Prestation> prestations, Model model) {
    prestations.add(prestation);

      for (Prestation presta : prestations) {
          System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
          System.out.println(presta);
          System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
      }

    model.addAttribute("prestations", prestations);
    model.addAttribute("prestation", prestation);
    return "redirect:/vueEnregistrement";
  }

  @RequestMapping("/saveFacture")
  public String sauvegarderFacture(@ModelAttribute("prestations")List <Prestation> prestations, Facturation facturation, @ModelAttribute("client") Client client, RedirectAttributes ra) {

    Collection<Prestation> prestationsColl = new HashSet<>();

      for (Prestation presta : prestations) {
          System.out.println("wmwmwmwmwwmwmmwmwmwmwmwmwmwmwmw");
          System.out.println(presta);
          System.out.println("wmwmwmwmwwmwmmwmwmwmwmwmwmwmwmw");
      }

    prestationsColl.addAll(prestations);
    facturation.setPrestation(prestationsColl);

    facturation.setId(Long.parseLong("1"));

    Voiture voiture = voitureDao.findVoitureByClient(client.getId());
    System.out.println(voiture);

    Double prixFactureCalcule = calculPrixFinal(prestations, voiture);
    System.out.println(prixFactureCalcule);

    facturation.setPrix(prixFactureCalcule);
    System.out.println(facturation.getPrix());

    facturation.setDate(LocalDateTime.now());
    System.out.println(facturation.getDate());

    facturation.setClient(client);
    System.out.println(facturation.getClient());

      System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
      System.out.println(facturation);
      System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");

    facturationDao.save(facturation);

    ra.addAttribute("client", client);

      /*SessionStatus sessionStatus = new SessionStatus() {
          @Override
          public void setComplete() {

          }

          @Override
          public boolean isComplete() {
              return false;
          }
      };
      sessionStatus.setComplete();*/

    return "redirect:/archive";
  }


  public Double calculPrixFinal(@ModelAttribute("prestations")List <Prestation> prestations, Voiture voiture) {

    Double prixTotalFacture = 0.0;
    for (Prestation presta : prestations) {
      Double prixParPresta = presta.getPrix();

      prixTotalFacture = prixTotalFacture + prixParPresta;
      System.out.println("+++++++++++++++++++++++++++++");
      System.out.println("*******-------------*********");
      System.out.println(prixTotalFacture);
      System.out.println("*******-------------*********");
      System.out.println("+++++++++++++++++++++++++++++");
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
    System.out.println();
    System.out.println("+++      PRIX FINAL      ++++");
    System.out.println("+++++++++++++++++++++++++++++");
    System.out.println("*******-------------*********");
    System.out.println(prixTotalFacture);
    System.out.println("*******-------------*********");
    System.out.println("+++++++++++++++++++++++++++++");
    return prixFinal;
  }

  public Client creationClient(@ModelAttribute("client") Client client, HttpServletRequest request) {

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