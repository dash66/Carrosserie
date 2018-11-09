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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@SessionAttributes({"client", "prestation", "prestations", "voiture", "facturations", "facturation"})
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

        Voiture k2000 = voitureDao.findVoitureByClient(michaelKnight);
        List<Facturation> facturations = facturationDao.findFacturationByClient(michaelKnight);

        Double prixFacture = 0.0;
        for(Facturation facturation1 : facturations){
            prixFacture = facturation1.getPrix();}


        model.addAttribute("client", michaelKnight);
        model.addAttribute("voiture", k2000);
        model.addAttribute("facturations", facturations);
        model.addAttribute("prixFacture", prixFacture);

        return "form-archive";
    }

    @PostMapping(value = "/rechercherVoiture") // fonction de la page archive
    public String rechercheVoiture(Model model, HttpServletRequest httpServletRequest, @ModelAttribute("client") Client clint) {

        String immat = httpServletRequest.getParameter("Immatriculation");
        Voiture k2000 = voitureDao.rechercherVoitureparImmat(immat);
        Long id = k2000.getClient().getId();
        clint = clientDao.findByImmat(id);
        List<Facturation> facturations = facturationDao.findFacturationByClient(clint);

        Double prixFacture = 0.0;
        for(Facturation facturation1 : facturations){
        prixFacture = facturation1.getPrix();}

        model.addAttribute("voiture", k2000);
        model.addAttribute("client", clint);
        model.addAttribute("facturations", facturations);
        model.addAttribute("prixFacture", prixFacture);

        return "form-archive";
    }

    @RequestMapping(value = "/rechercherFacture") // fonction de la page archive
    public String rechercheFacture(Model model, RedirectAttributes ra, HttpServletRequest httpServletRequest,
                                   @ModelAttribute("client")Client client,
                                   @ModelAttribute("voiture")Voiture voiture,
                                   @ModelAttribute("facturations") List<Facturation> facturations,
                                   @ModelAttribute("prestations") Collection<Prestation> prestations,
                                   @ModelAttribute("prestation") Prestation prestation,
                                   @ModelAttribute("facturation") Facturation facturation) {

        Long numFacture = Long.valueOf(httpServletRequest.getParameter("Robert"));
        Optional<Facturation> facture = facturationDao.findById(numFacture);
        facturation = facture.get();

       facturations = new ArrayList<>();
       facturations.add(facturation);

        client = clientDao.findClientByFacturation(facturation);
        voiture = voitureDao.findVoitureByClient(client);
        prestations = facturation.getPrestation();

        System.out.println("*******************");
        System.out.println(numFacture);
        System.out.println("*******************");

        model.addAttribute("prestations", prestations);
        model.addAttribute("facturation", facturation);
        model.addAttribute("client", client);
        model.addAttribute("voiture", voiture);
        model.addAttribute("facturations", facturations);

        return "redirect:/facturation";
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
    public String rechercheVoitureExistant(Model model, HttpServletRequest httpServletRequest, @ModelAttribute("client") Client client, @ModelAttribute("voiture") Voiture k2000, RedirectAttributes ra) {

        String Immatriculation = httpServletRequest.getParameter("Immatriculation");
        k2000 = voitureDao.rechercherVoitureparImmat(Immatriculation);

        Client clint = k2000.getClient();
        model.addAttribute("client", clint);
        model.addAttribute("voiture", k2000);

        System.out.println(k2000);
        ra.addAttribute(k2000);

        return "redirect:/vueEnregistrement";
    }

    @GetMapping("/rechercheFactureExistant")
    // fonction de la page enregistrement, doit y retourner : trouver un client existant pour préparer
    // presta
    public String rechercheFactureExistant(Model model, HttpServletRequest httpServletRequest) {

        Long numFacture = Long.valueOf(httpServletRequest.getParameter("facture"));
        Optional<Facturation> Facture = facturationDao.findById(numFacture);
             Facturation facturation1 = Facture.get();

        model.addAttribute("facturation", facturation1);

        return "redirect:/vueEnregistrement";
    }
}
