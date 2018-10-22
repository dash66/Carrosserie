package com.carrosserieafpa.carrosserie.web.controller;

import com.carrosserieafpa.carrosserie.dao.*;
import com.carrosserieafpa.carrosserie.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @PostMapping(value = "/rechercher") // fonction de la page archive
    public String recherche2(Model model, HttpServletRequest httpServletRequest) {

        String prenom = httpServletRequest.getParameter("prenom3");
        String nom = httpServletRequest.getParameter("nom3");
        String immat = httpServletRequest.getParameter("Immatriculation");
        String marque = httpServletRequest.getParameter("Marque");
        String modele = httpServletRequest.getParameter("model");
        Long numFacture = Long.valueOf(httpServletRequest.getParameter("numFacture"));

        Long id = clientDao.rechercherClientParNometPrenom(nom, prenom);
        Optional<Client> client = clientDao.findById(id);
        Client michaelKnight = client.get();

        Long id1 = voitureDao.rechercherVoitureparImmat(immat);
        Optional<Voiture> voiture = voitureDao.findById(id1);
        Voiture k2000 = voiture.get();

        Long id2 = facturationDao.rechercherFactureParId(numFacture);
        Optional<Facturation> facturation = facturationDao.findById(id2);
        Facturation nightRider = facturation.get();

        model.addAttribute("client", michaelKnight);
        model.addAttribute("voiture", k2000);
        model.addAttribute("facturation", nightRider);

        return "form-archive";
    }

    @GetMapping(
            "/recherche") // fonction de la page enregistrement, doit y retourner : trouver un client
    // existant pour préparer presta
    public String effectuerUnerecherche(Client client, HttpServletRequest httpServletRequest) {

        Long clientId =
                clientDao.rechercherClientParNometPrenom(
                        httpServletRequest.getParameter("nom"), httpServletRequest.getParameter("prenom"));

        Facturation resultatRecherche =
                facturationDao.rechercheClientEtInfoParId(
                        Long.valueOf(httpServletRequest.getParameter("id")));

        return "form-enregistrement";
    }

    @GetMapping(value = {"/admin", "/acte", "/finition"})
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

    @RequestMapping(value = {"/menu", "/"})
    public String menu(Model model) {
        return "form-menu";
    }

    @RequestMapping("/enregistrer")
    public String enregistrer(@ModelAttribute("prestation") Prestation prestation, @ModelAttribute("client") Client client, @ModelAttribute("voiture") Voiture voiture, Model model, HttpServletRequest request) {

        List<Finition> finitions = finitionDao.findAll();
        List<Acte> actes = acteDao.findAll();
        List<Facturation> factures = facturationDao.findAll();
        List<Prestation> prestations = new ArrayList<>();

        model.addAttribute("factures", factures);
        model.addAttribute("actes", actes);
        model.addAttribute("finitions", finitions);
        model.addAttribute("prestations", prestations);

        client = this.creationClient(client, request);
        clientDao.save(client);

        voiture = this.creationVoiture(voiture, request, client);
        voitureDao.save(voiture);

        Acte acte = prestation.getActe();
        Finition finition = prestation.getFinition();

        prestation.setId_presta(prestationDao.FindIdByActeAndFinition(acte, finition));
        try {prestation.setPrix(prestationDao.findPrixById(prestation.getId_presta()));}
        catch(NullPointerException e){}
        prestations.add(prestation);

        return "form-enregistrement";
    }

    @PostMapping("/admin")
    public String ajouterPrix(HttpServletRequest httpServletRequest, Prestation prestation) {

        prestation.setPrix(Double.valueOf(httpServletRequest.getParameter("libelle3")));
        prestationDao.save(prestation);

        return "form-administrateur";
    }

    @PostMapping("/acte")
    public String ajouterNouveauActe(
            Model model,
            Finition finition,
            HttpServletRequest httpServletRequest,
            Acte acte,
            Prestation prestation) {

        acte.setLibelle(httpServletRequest.getParameter("libelle"));

        acteDao.save(acte);
        this.affichageMenuAdministrateur(model, acte, finition, prestation);
        return "form-administrateur";
    }

    @PostMapping("/finition")
    public String ajouterNouveauFinition(
            Model model,
            Acte acte,
            HttpServletRequest httpServletRequest,
            Finition finition,
            Prestation prestation) {

        finition.setLibelle(httpServletRequest.getParameter("libelle2"));
        finitionDao.save(finition);

        this.affichageMenuAdministrateur(model, acte, finition, prestation);
        return "form-administrateur";
    }

    @RequestMapping("/archive")
    public String consulterArchive(Model model) {

        return "form-archive";
    }

      public String retrouverCatégorieVoiturePourClientExistant(Model model, HttpServletRequest httpServletRequest) {

        Long clientId = clientDao.rechercherClientParNometPrenom(
                httpServletRequest.getParameter("nom"), httpServletRequest.getParameter("prenom"));

        String categorieClientExistant = voitureDao.rechercherCategorieVoitureParId(clientId);

        return categorieClientExistant;
    }

    public Client creationClient(Client client, HttpServletRequest request) {
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

    public Voiture creationVoiture(Voiture voiture, HttpServletRequest request, Client client) {
        voiture.setMarque(request.getParameter("marque"));
        voiture.setImmat(request.getParameter("immat"));
        voiture.setModele(request.getParameter("modele"));
        voiture.setCodeCouleur(request.getParameter("couleur"));
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
    

    public Double calculPrixFinal(List<Prestation> prestations, Voiture voiture) {

        Double prixTotalFacture = null;
        for (Prestation presta :
                prestations) {
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
}



