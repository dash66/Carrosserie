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

    @RequestMapping(value = {"/menu", "/"})
    public String menu(Model model) {
        return "form-menu";
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

    @GetMapping("/recherche")
    // fonction de la page enregistrement, doit y retourner : trouver un client existant pour préparer presta
    public String effectuerUnerecherche(Client client, HttpServletRequest httpServletRequest) {

        Long clientId =
                clientDao.rechercherClientParNometPrenom(
                        httpServletRequest.getParameter("nom"), httpServletRequest.getParameter("prenom"));

        Facturation resultatRecherche =
                facturationDao.rechercheClientEtInfoParId(
                        Long.valueOf(httpServletRequest.getParameter("id")));

        return "form-enregistrement";
    }

    @RequestMapping("/enregistrer")
    public String enregistrer(@ModelAttribute("prestation") Prestation prestation, Model model, HttpServletRequest request, Voiture voiture) {
        Client client = new Client();
        List<Finition> finitions = finitionDao.findAll();
        List<Acte> actes = acteDao.findAll();
        List<Prestation> prestations = new ArrayList<>();

        model.addAttribute("actes", actes);
        model.addAttribute("finitions", finitions);
        model.addAttribute("prestations", prestations);
        model.addAttribute("client", client);

        this.coupleClientVoitureBase(client, request, voiture);
        Long id = clientDao.findLastId();
        Optional<Client> clientTmp = clientDao.findById(id);
        client = clientTmp.get();

        Acte acte = prestation.getActe();
        Finition finition = prestation.getFinition();

        prestation.setId_presta(prestationDao.FindIdByActeAndFinition(acte, finition));
        try {
            prestation.setPrix(prestationDao.findPrixById(prestation.getId_presta()));
        } catch (NullPointerException e) {
        }
        prestations.add(prestation);

        return "form-enregistrement";
    }

    @PostMapping("/saveClient")
    public Client coupleClientVoitureBase(Client client, HttpServletRequest request, Voiture voiture) {

        client = this.creationClient(client, request);
        voiture = this.creationVoiture(client, request);

        if (client.getNom() != null) {
            clientDao.save(client);
            voitureDao.save(voiture);
        }

        return client;
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

    @PostMapping("/prix")
    public String ajouterPrix(HttpServletRequest httpServletRequest, Prestation prestation) {

        prestation.setPrix(Double.valueOf(httpServletRequest.getParameter("libelle3")));
        prestationDao.save(prestation);

        return "redirect:/admin";
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



