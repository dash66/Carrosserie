package com.carrosserieafpa.carrosserie.web.controller;

import com.carrosserieafpa.carrosserie.dao.*;
import com.carrosserieafpa.carrosserie.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SessionAttributes({"client", "prestation", "prestations", "facturations", "facturation", "voiture"})
@Controller
public class ControllerUI {

    private static final String ACCUEIL = "form-menu";
    private static final String FORM_ENREGISTREMENT = "form-enregistrement";
    private static final String ADMINISTRATION = "form-administrateur";
    private static final String ARCHIVE = "form-archive";
    private static final String RECHERCHE = "form-recherche";
    private static final String FACTURE = "form-facturation";

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

    @GetMapping(value = {"/menu", "/"})
    public String menu(SessionStatus status) {
        status.setComplete();
        return ACCUEIL;
    }

    @GetMapping("/enregistrement")
    public String afficherPageEnregistrement(
            @ModelAttribute("prestation") Prestation prestation,
            @ModelAttribute("client") Client client,
            @ModelAttribute("prestations") ArrayList<Prestation> prestations,
            @ModelAttribute("voiture") Voiture voiture,
            Model model) {
        model.addAttribute("actes", acteDao.findActesOrderByNom());
        model.addAttribute("finitions", finitionDao.findFinitionsOrderByNom());
        model.addAttribute("prestations", prestations);
        model.addAttribute("voiture", voiture);
        model.addAttribute("client", client);
        return FORM_ENREGISTREMENT;
    }

    @RequestMapping("/setVoitureClient")
    public String settageVoitureClient(HttpServletRequest request, Model model) {
        Optional<Voiture> tuture = voitureDao.findById(Long.valueOf(request.getParameter("vehicule")));

        Voiture voiture = tuture.get();
        model.addAttribute("voiture", voiture);
        return "redirect:/enregistrement";
    }

    @GetMapping("/admin")
    public String affichageMenuAdministrateur(Model model) {
        List<Finition> finitions = finitionDao.findFinitionsOrderByNom();
        List<Acte> actes = acteDao.findActesOrderByNom();
        List<Client> clients = clientDao.findAllClientOrderByNom();
        model.addAttribute("acte", new Acte());
        model.addAttribute("finition", new Finition());
        model.addAttribute("actes", actes);
        model.addAttribute("finitions", finitions);
        model.addAttribute("prestation", new Prestation());
        model.addAttribute("clients", clients);
        return ADMINISTRATION;
    }

    @RequestMapping("/archive")
    public String consulterArchive(Model model, @ModelAttribute("client") Client client, @ModelAttribute("voiture") Voiture voiture){
        List<Prestation> prestations = facturationDao.recherchePrestationDansFactureParClientId(client.getId());
        List<Facturation> facturations = facturationDao.findFacturationByClient(client);
        model.addAttribute("prestations", prestations);
        model.addAttribute("client", client);
        model.addAttribute("voiture", voiture);
        model.addAttribute("facturations", facturations);
        return ARCHIVE;
    }

    @GetMapping(value = "/rechercher") // fonction de la page archive
    public String recherche(Model model, SessionStatus sessionStatus) {

        List<Finition> finitions = finitionDao.findFinitionsOrderByNom();
        List<Prestation> prestations = prestationDao.findAll();
        List<Acte> actes = acteDao.findActesOrderByNom();

        model.addAttribute("finitions", finitions);
        model.addAttribute("actes", actes);
        model.addAttribute("prestations", prestations);
        model.addAttribute("prestation", new Prestation());

        sessionStatus.setComplete();
        return RECHERCHE;
    }

    @GetMapping("/facturation/{id}")
    public String facture(Model model, @ModelAttribute("facturation") Facturation facturation, @PathVariable Long id) {
        Optional<Facturation> facture = facturationDao.findById(id);
        if(facture.isPresent()){ facturation = facture.get(); }

        model.addAttribute("facturation", facturation);
        return FACTURE;
    }

    @RequestMapping(value = "/login")
    public String login() {

        return "login";

    }

}
