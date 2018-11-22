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

    public static final String ENREGISTREMENT = "/enregistrement";
    public static final String ARCHIVE = "/archive";

    @Autowired ActeDao acteDao;
    @Autowired FinitionDao finitionDao;
    @Autowired PrestationDao prestationDao;
    @Autowired FacturationDao facturationDao;
    @Autowired VoitureDao voitureDao;
    @Autowired ClientDao clientDao;

    @ModelAttribute("client") public Client getClient() {
        return new Client();
    }
    @ModelAttribute("voiture") public Voiture getVoiture() {
        return new Voiture();
    }
    @ModelAttribute("prestation") public Prestation getPrestation() {
        return new Prestation();
    }
    @ModelAttribute("prestations") public List<Prestation> getPrestas() {
        return new ArrayList<>();
    }
    @ModelAttribute("facturation") public Facturation getFacturation() {
        return new Facturation();
    }
    @ModelAttribute("facturations") public List<Facturation> getFacturations() {
        return new ArrayList<>();
    }

    @RequestMapping("/rechercherClient")
    public String rechercheClient(
            @ModelAttribute("client") Client michaelKnight,
            Model model,
            HttpServletRequest httpServletRequest,
            RedirectAttributes ra) {

        String prenom = httpServletRequest.getParameter("prenom3");
        String nom = httpServletRequest.getParameter("nom3");

        Collection<Facturation> facturations;
        Collection<Voiture> k2000;

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

    @PostMapping("/rechercherVoiture")
    public String rechercheVoiture(RedirectAttributes ra, Model model, HttpServletRequest httpServletRequest, @ModelAttribute("client") Client clint) {

        Voiture k2000;
        List<Facturation> facturations;

        try {
            k2000 = voitureDao.findByImmat(httpServletRequest.getParameter("Immatriculation"));
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

    @RequestMapping(value = "/rechercherFacture")
    public String rechercheFacture(Model model, RedirectAttributes ra, HttpServletRequest httpServletRequest, @ModelAttribute("facturations") List<Facturation> facturations, @ModelAttribute("facturation") Facturation facturation) {

        Long numFacture = Long.valueOf(httpServletRequest.getParameter("Robert"));

        try {
            Optional<Facturation> facture = facturationDao.findById(numFacture);
            facturation = facture.get();
            facturations = new ArrayList<>();
            facturations.add(facturation);
        } catch (NoSuchElementException e) {
            ra.addFlashAttribute("message", "Cette facture n'existe pas !");
            return "redirect:/rechercher";}

        model.addAttribute("prestations", facturation.getPrestation());
        model.addAttribute("facturation", facturation);
        model.addAttribute("client", facturation.getClient());
        model.addAttribute("voiture", facturation.getVoiture());
        model.addAttribute("facturations", facturations);

        return "redirect:/facturation/" + facturation.getId();
    }

    @RequestMapping("/rechercheClientExistant")
    public String rechercheClientExistant(Model model, HttpServletRequest httpServletRequest, RedirectAttributes ra) {

        String prenom = httpServletRequest.getParameter("prenom");
        String nom = httpServletRequest.getParameter("nom");
        Client michaelKnight;

        try {
            michaelKnight = clientDao.findClientByNomAndPrenom(nom, prenom);
            michaelKnight.getPrenom();
        } catch (NullPointerException e) {
            ra.addFlashAttribute("message", "Ce client n'existe pas !");
            return "redirect:" + ENREGISTREMENT;
        }
        model.addAttribute("client", michaelKnight);

        return "redirect:" + ENREGISTREMENT;
    }

    @RequestMapping("/rechercheVoitureExistant")
    public String rechercheVoitureExistant(Model model, HttpServletRequest httpServletRequest, @ModelAttribute("client") Client client, RedirectAttributes ra) {
        Client clint;
        Voiture k2000;

        try {
            k2000 = voitureDao.findByImmat(httpServletRequest.getParameter("Immatriculation"));
            clint = k2000.getClient();
        } catch (NullPointerException e) {
            ra.addFlashAttribute("message", "Ce véhicule n'existe pas !");
            return "redirect:" + ENREGISTREMENT;
        }
        model.addAttribute("client", clint);
        model.addAttribute("voiture", k2000);

        return "redirect:" + ENREGISTREMENT;
    }

    @RequestMapping("/rechercheFactureExistant")
    public String rechercheFactureExistant(Model model, HttpServletRequest httpServletRequest, RedirectAttributes ra) {
        Facturation facturation1 = null;
        try {
            Long numFacture = Long.valueOf(httpServletRequest.getParameter("facture"));
            Optional<Facturation> facture = facturationDao.findById(numFacture);
            if (facture.isPresent()) {
                facturation1 = facture.get();
            }
        } catch (NumberFormatException e) {
            ra.addFlashAttribute("message", "Cette facture n'existe pas !");
            return "redirect:" + ENREGISTREMENT;
        }
        model.addAttribute("facturation", facturation1);

        return "redirect:" + ENREGISTREMENT;
    }

    @RequestMapping("/setVoitureClientRecherche")
    public String settageVoitureClient(@ModelAttribute("voiture") Voiture voiture, @ModelAttribute("client") Client client, HttpServletRequest request, Model model) {
        Long id = Long.valueOf(request.getParameter("vehicule"));
        Optional<Voiture> tuture = voitureDao.findById(id);
        if (tuture.isPresent()) {
            voiture = tuture.get();
        }

        model.addAttribute("voiture", voiture);

        return "redirect:" + ARCHIVE;
    }
}
