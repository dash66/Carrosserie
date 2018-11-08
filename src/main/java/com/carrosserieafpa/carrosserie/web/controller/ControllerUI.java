package com.carrosserieafpa.carrosserie.web.controller;

import com.carrosserieafpa.carrosserie.dao.*;
import com.carrosserieafpa.carrosserie.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@SessionAttributes({"client", "prestation", "prestations", "facturations", "facturation", "voiture"})
@Controller
public class ControllerUI {
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

    @RequestMapping(value = {"/menu", "/"})
    public String menu() {
        return "form-menu";
    }

    @GetMapping("/vueEnregistrement")
    public String afficherPageEnregistrement(
            @ModelAttribute("prestation") Prestation prestation,
            @ModelAttribute("client") Client client,
            @ModelAttribute("prestations") ArrayList<Prestation> prestations,
            @ModelAttribute("voiture") Voiture voiture,
            Model model, HttpServletRequest request) {

        model.addAttribute("actes", acteDao.findAll());
        model.addAttribute("finitions", finitionDao.findAll());
        model.addAttribute("prestations", prestations);
        model.addAttribute("voiture", voiture);

        return "form-enregistrement";
    }

    @GetMapping("/admin")
    public String affichageMenuAdministrateur(
            Model model, Acte acte, Finition finition, Prestation prestation) {

        List<Finition> finitions = finitionDao.findAll();
        List<Acte> actes = acteDao.findAll();

        model.addAttribute("acte", acte);
        model.addAttribute("finition", finition);
        model.addAttribute("actes", actes);
        model.addAttribute("finitions", finitions);
        model.addAttribute("prestation", prestation);

        return "form-administrateur";
    }

    @RequestMapping("/archive")
    public String consulterArchive(Model model, @ModelAttribute("client") Client client, @ModelAttribute("voiture") Voiture voiture) {

        List<Prestation> prestations = facturationDao.recherchePrestationDansFactureParClientId(client.getId());
        Double prixFacture = facturationDao.recherchePrixFactureParIdClient(client.getId());
        List<Facturation> facturations = clientDao.rechercherFacturationsParClient(client.getId());

        model.addAttribute("prestations", prestations);
        model.addAttribute("client", client);
        model.addAttribute("voiture", voiture);
        model.addAttribute("prixFacture", prixFacture);
        model.addAttribute("facturations", facturations);

        return "form-archive";
    }

    @GetMapping(value = "/rechercher") // fonction de la page archive
    public String recherche(Model model, SessionStatus sessionStatus) {

        List<Finition> finitions = finitionDao.findAll();
        List<Prestation> prestations = prestationDao.findAll();
        List<Acte> actes = acteDao.findAll();

        model.addAttribute("finitions", finitions);
        model.addAttribute("actes", actes);
        model.addAttribute("prestations", prestations);
        model.addAttribute("prestation", new Prestation());

        sessionStatus.setComplete();
        return "form-recherche";
    }

    @RequestMapping("/facturation")
    public String facture(Model model,
                          @ModelAttribute("prestations") List<Prestation> prestationList,
                          @ModelAttribute("client") Client client,
                          @ModelAttribute("voiture") Voiture voiture,
                          @ModelAttribute("prestation") Prestation prestation,
                          @ModelAttribute("facturation") Facturation facturation,
                          SessionStatus sessionStatus) {

        Double prixFacture = facturationDao.recherchePrixFactureParIdClient(client.getId());
        model.addAttribute("prixFacture", prixFacture);
        Double prixFinal = 0.0;

        model.addAttribute("prixFinal", prixFinal);

        sessionStatus.setComplete();
        return "form-facturation";
    }
}
