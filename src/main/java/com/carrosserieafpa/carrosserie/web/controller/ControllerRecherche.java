package com.carrosserieafpa.carrosserie.web.controller;

import com.carrosserieafpa.carrosserie.dao.*;
import com.carrosserieafpa.carrosserie.entity.Client;
import com.carrosserieafpa.carrosserie.entity.Facturation;
import com.carrosserieafpa.carrosserie.entity.Prestation;
import com.carrosserieafpa.carrosserie.entity.Voiture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@SessionAttributes({"client", "prestation", "prestations"})
@Controller
public class ControllerRecherche {

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
        List<Facturation> facturations = facturationDao.rechercheFacturationParClientId(michaelKnight);

        model.addAttribute("client", michaelKnight);
        model.addAttribute("voiture", k2000);
        model.addAttribute("facturations", facturations);

        return "form-archive";
    }

    @PostMapping(value = "/rechercherVoiture") // fonction de la page archive
    public String rechercheVoiture(Model model, HttpServletRequest httpServletRequest) {

        String immat = httpServletRequest.getParameter("Immatriculation");
        Voiture k2000 = voitureDao.rechercherVoitureparImmat(immat);
        model.addAttribute("voiture", k2000);

        Long id = k2000.getClient().getId();
        Client clint = clientDao.findByImmat(id);
        model.addAttribute("client", clint);

        return "form-archive";
    }

    @PostMapping(value = "/rechercherFacture") // fonction de la page archive
    public String rechercheFacture(Model model, HttpServletRequest httpServletRequest) {

        Long numFacture = Long.valueOf(httpServletRequest.getParameter("numFacture"));
        Optional<Facturation> facturation = facturationDao.findById(numFacture);
        Facturation facturation1 = facturation.get();

        model.addAttribute("voiture", facturation1.getVoiture());
        model.addAttribute("facturation", facturation1);
        model.addAttribute("client", facturation1.getClient());
        model.addAttribute("prixFacture", facturation1.getPrix());
        model.addAttribute("prestations", facturation1.getPrestation());





        return "form-facturation";
    }

    @RequestMapping("/rechercheClientExistant")
    // fonction de la page enregistrement, doit y retourner : trouver un client existant pour préparer
    // presta
    public String rechercheClientExistant(Model model, HttpServletRequest httpServletRequest) {

        String prenom = httpServletRequest.getParameter("prenom");
        String nom = httpServletRequest.getParameter("nom");

        Long id = clientDao.rechercherClientParNometPrenom(nom, prenom);
        Optional<Client> client = clientDao.findById(id);
        Client michaelKnight = client.get();

        model.addAttribute("client", michaelKnight);

        return "redirect:/vueEnregistrement";
    }

    @RequestMapping("/rechercheVoitureExistant") // fonction de la page enregistrement, doit y retourner : trouver un client existant pour préparer presta
    public String rechercheVoitureExistant(Model model, HttpServletRequest httpServletRequest, @ModelAttribute("client") Client client) {

        String immat = httpServletRequest.getParameter("Immatricualtion");
        Voiture k2000 = voitureDao.rechercherVoitureparImmat(immat);


        Client clint = k2000.getClient();
        model.addAttribute("client", clint);

        return "redirect:/vueEnregistrement";
    }

    @GetMapping("/rechercheFactureExistant")
    // fonction de la page enregistrement, doit y retourner : trouver un client existant pour préparer
    // presta
    public String rechercheFactureExistant(Model model, HttpServletRequest httpServletRequest) {

        Long numFacture = Long.valueOf(httpServletRequest.getParameter("facture"));
        Optional<Facturation> facturation = facturationDao.findById(numFacture);
        Facturation facturation1 = facturation.get();

        model.addAttribute("facturation", facturation1);

        return "redirect:/vueEnregistrement";
    }
}
