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

import java.util.ArrayList;
import java.util.List;

@SessionAttributes({"client", "prestation", "prestations"})
@Controller
public class controllerUI {

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

    @ModelAttribute("prestation")
    public Prestation getPrestation() {
        return new Prestation();
    }

    @ModelAttribute("prestations")
    public List<Prestation> getPrestas() {
        return new ArrayList<>();
    }

    @RequestMapping(value = {"/menu", "/"})
    public String menu(Model model) {
        return "form-menu";
    }

    @GetMapping("/vueEnregistrement")
    public String afficherPageEnregistrement(
            @ModelAttribute("prestation") Prestation prestation,
            @ModelAttribute("client") Client client,
            @ModelAttribute("prestations") ArrayList<Prestation> prestations,
            Model model) {

        model.addAttribute("actes", acteDao.findAll());
        model.addAttribute("finitions", finitionDao.findAll());
        model.addAttribute("prestations", prestations);

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
    public String consulterArchive(Model model, @ModelAttribute("client") Client client) {

        Voiture voiture = voitureDao.findVoitureByClient(client.getId());
        List<Prestation> prestations = facturationDao.recherchePrestationDansFactureParClientId(client.getId());
        Double prixFacture  = facturationDao.recherchePrixFactureParIdClient(client.getId());

        model.addAttribute("prestations", prestations);
        model.addAttribute("client", client);
        model.addAttribute("voiture", voiture);
        model.addAttribute("prixFacture", prixFacture);



        return "form-archive";
    }

    @GetMapping(value = "/rechercher") // fonction de la page archive
    public String recherche(Model model) {

        List<Finition> finitions = finitionDao.findAll();
        model.addAttribute("finitions", finitions);

        List<Acte> actes = acteDao.findAll();
        model.addAttribute("actes", actes);

        List<Prestation> prestations = prestationDao.findAll();
        model.addAttribute("prestations", prestations);

        Prestation prestation = new Prestation();
        model.addAttribute("prestation", prestation);

        return "form-recherche";
    }

    @RequestMapping("/facturation")
    public String facture() {

        return "form-facturation";
    }
}
