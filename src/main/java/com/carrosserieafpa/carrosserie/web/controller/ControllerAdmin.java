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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SessionAttributes({"client", "prestation", "prestations"})
@Controller
public class ControllerAdmin {

  @Autowired ActeDao acteDao;
  @Autowired FinitionDao finitionDao;
  @Autowired PrestationDao prestationDao;
  @Autowired FacturationDao facturationDao;
  @Autowired VoitureDao voitureDao;
  @Autowired ClientDao clientDao;
  @Autowired AdminDao adminDao;

  @ModelAttribute("prestation")
  public Prestation getPrestation() {
    return new Prestation();
  }

  @ModelAttribute("prestations")
  public List<Prestation> getPrestas() {
    return new ArrayList<>();
  }

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
  public String ajouterPrix(
      @ModelAttribute("prestation") Prestation prestation, SessionStatus status) {

    prestationDao.save(prestation);
    status.setComplete();

    return "redirect:/admin";
  }

  @RequestMapping("/admin/delete/acte")
  public String supprimerUnActe(
      Acte acte,
      HttpServletRequest httpServletRequest,
      @ModelAttribute("prestation") Prestation prestation) {

    String ActeASupprimer = httpServletRequest.getParameter("acte");
    Acte acteSupprimer = prestation.getActe();
    acteDao.delete(acteSupprimer);

    return "redirect:/admin";
  }

  @RequestMapping("/admin/delete/client")
  public String supprimerUnClient(HttpServletRequest httpServletRequest, Client client) {

    Long idToDelete = Long.valueOf(httpServletRequest.getParameter("client"));
    Optional<Client> clientTmp = clientDao.findById(idToDelete);
    if (clientTmp.isPresent()) {
      client = clientTmp.get();
    }
    clientDao.delete(client);

    return "redirect:/admin";
  }

  @RequestMapping("/admin/setClient")
  public String setClientToDeleteVoiture(
      HttpServletRequest request, Model model, RedirectAttributes ra) {
    Client client = new Client();

    Long id = Long.valueOf(request.getParameter("proprio"));
    System.out.println(id);
    Optional<Client> cleint = clientDao.findById(id);
    if (cleint.isPresent()) {
      client = cleint.get();
    }
    model.addAttribute("client", client);
    ra.addAttribute("client", client);
    return "redirect:/admin/delete/voiture";
  }

  @RequestMapping("/admin/delete/voiture")
  public String supprimerUnVehicule(
      HttpServletRequest request, Model model, @ModelAttribute("client") Client client) {

    Long id = Long.valueOf(request.getParameter("proprio"));
    Optional<Client> cleint = clientDao.findById(id);
    if (cleint.isPresent()) {
      client = cleint.get();
    }
    model.addAttribute("client", client);

    return "redirect:/admin";
  }

  @RequestMapping("/admin/delete/finition")
  public String supprimerUneFinition(
      Finition finition,
      HttpServletRequest httpServletRequest,
      @ModelAttribute("prestation") Prestation prestation) {

    String FinitionASupprimer = httpServletRequest.getParameter("finition");
    Finition finitionSupprimer = prestation.getFinition();
    finitionDao.delete(finitionSupprimer);

    return "redirect:/admin";
  }

  @RequestMapping("/verifierLogin")
  public String verifierMotDepasse(HttpServletRequest request) {
    String entryPwd = request.getParameter("password");
    List<Administrateur> admins = adminDao.findAll();

    List<String> passwords = new ArrayList<>();
    for (Administrateur admin : admins) {
      passwords.add(admin.getMotDePasse());
    }

    if (passwords.contains(entryPwd)) {
      return "redirect:/admin";
    } else {
      return "/login";
    }
  }

  @RequestMapping("/admin/changerMdp")
  public String changerMdp(HttpServletRequest request, RedirectAttributes ra) {

    String ancienMdp = request.getParameter("ancienMdp");
    Administrateur admin = new Administrateur();
    try {
      admin = adminDao.findByMotDePasse(ancienMdp);
      admin.getMotDePasse();
    } catch (NullPointerException e) {
      ra.addFlashAttribute("message", "Ancien mot de passe incorrect !!");
      return "redirect:/admin";
    }
    String nouveauMdp = request.getParameter("nouveauMdp");
    admin.setMotDePasse(nouveauMdp);
    adminDao.save(admin);
    ra.addFlashAttribute("message2", "Mot de passe mis à jour !!");

    return "redirect:/admin";

  }
}
