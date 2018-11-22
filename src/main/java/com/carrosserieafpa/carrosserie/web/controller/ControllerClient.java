package com.carrosserieafpa.carrosserie.web.controller;

import com.carrosserieafpa.carrosserie.dao.*;
import com.carrosserieafpa.carrosserie.entity.*;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SessionAttributes({"client", "prestations", "voiture", "facturation"})
@Controller
public class ControllerClient {

    public static final String ENREGISTREMENT = "/enregistrement";
    public static final String FACTURE = "/facturation/";
    public static final String ACCUEIL = "/menu";

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

    @ModelAttribute("prestations")
    public List<Prestation> getPrestas() {
        return new ArrayList<>();
    }

    @ModelAttribute("facturation")
    public Facturation getFacturation() {
        return new Facturation();
    }

    @RequestMapping("/saveClient")
    public String enregistrementClient(HttpServletRequest request, @ModelAttribute("client") Client client, @ModelAttribute("voiture") Voiture voiture, Model model, RedirectAttributes ra) {
        client = this.creationClient(request);

        voiture = this.creationVoiture(client, request);
        Collection<Voiture> tutures = new HashSet<>();
        tutures.add(voiture);
        client.setVoiture(tutures);

        if (client.getNom() != null) {
            clientDao.save(client);
            voitureDao.save(voiture);
        }
        model.addAttribute("client", client);
        ra.addFlashAttribute("message1", "Client & voiture ajoutés en base !!");

        return "redirect:" + ENREGISTREMENT;
    }

    @RequestMapping("/createNewVehicule")
    public String creationNewVoiture(@ModelAttribute("client") Client client, @ModelAttribute("voiture") Voiture voiture, HttpServletRequest request, RedirectAttributes ra) {
        String date = request.getParameter("date");
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateDef = new SimpleDateFormat("dd-MM-yyyy").format(date1);

        voiture.setMarque(request.getParameter("marque"));
        voiture.setImmat(request.getParameter("immat"));
        voiture.setModele(request.getParameter("modele"));
        voiture.setCodeCouleur(request.getParameter("couleur"));
        voiture.setCategorie(request.getParameter("categorie"));
        voiture.setDate(dateDef);
        voiture.setClient(client);
        client.getVoiture().add(voiture);

        voitureDao.save(voiture);
        ra.addFlashAttribute("message2", "Voiture ajoutée en base !!");

        return "redirect:" + ENREGISTREMENT;
    }

    @RequestMapping("/addPresta")
    public String ajouterPrestaList(@ModelAttribute("prestation") Prestation prestation,
                                    @ModelAttribute("prestations") List<Prestation> prestations) {
        prestation.setId_presta(prestationDao.findIdByActeAndFinition(prestation.getActe(), prestation.getFinition()));
        prestation.setPrix(prestationDao.findPrixById(prestation.getId_presta()));
        prestations.add(prestation);
        return "redirect:" + ENREGISTREMENT;
    }

    @RequestMapping("/deletePresta")
    public String supprimerPrestation(@ModelAttribute("prestation") Prestation prestation,
                                      @ModelAttribute("prestations") List<Prestation> prestations) {

        Optional<Prestation> prestationTmp = prestationDao.findById(prestation.getId_presta());

        if (prestationTmp.isPresent()) {
            prestation = prestationTmp.get();
        }
        if (prestations != null && !prestations.isEmpty()) {
            int number = -1;

            for (Prestation presta : prestations) {
                if (presta.getId_presta().equals(prestation.getId_presta())) {
                    number = prestations.indexOf(presta);
                }
            }
            prestations.remove(number);
        }
        return "redirect:" + ENREGISTREMENT;
    }

    @RequestMapping("/saveFacture")
    public String sauvegarderFacture(@ModelAttribute("prestations") List<Prestation> prestations,
                                     @ModelAttribute("client") Client client,
                                     @ModelAttribute("voiture") Voiture voiture,
                                     @ModelAttribute("facturation") Facturation facturation, HttpServletRequest request) {

        LocalDateTime HeureNonFormatee = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy à HH:mm");
        String formatDateTime = HeureNonFormatee.format(timeFormatter);

        Client clientTmp = clientDao.findClientByNomAndPrenom(client.getNom(), client.getPrenom());
        client.setId(clientTmp.getId());

        Voiture voitTmp = voitureDao.findByImmat(voiture.getImmat());
        voiture.setId(voitTmp.getId());

        facturation.setPrestation(prestations);
        facturation.setPrix(calculPrixFinal(prestations, voiture));
        facturation.setDate(formatDateTime);
        facturation.setRemarque(request.getParameter("remarque"));
        facturation.setClient(client);
        facturation.setVoiture(voiture);
        facturationDao.save(facturation);

        return "redirect:" + FACTURE + facturation.getId();
    }



    private Client creationClient(HttpServletRequest request) {
        Client client = new Client();
        client.setPrenom(request.getParameter("prenom"));
        client.setNom(request.getParameter("nom"));
        client.setRue(request.getParameter("rue"));
        client.setCodePostal(request.getParameter("codePostal"));
        client.setVille(request.getParameter("ville"));
        client.setTelephone(request.getParameter("telephone"));
        client.setEmail(request.getParameter("email"));
        client.setNumAfpa(request.getParameter("numAfpa"));

        return client;
    }
    private Voiture creationVoiture(Client client, HttpServletRequest request) {
        Voiture voiture = new Voiture();
        String date = request.getParameter("date");
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateDef = new SimpleDateFormat("dd-MM-yyyy").format(date1);

        voiture.setMarque(request.getParameter("marque"));
        voiture.setImmat(request.getParameter("immat"));
        voiture.setModele(request.getParameter("modele"));
        voiture.setCodeCouleur(request.getParameter("couleur"));
        voiture.setCategorie(request.getParameter("categorie"));
        voiture.setDate(dateDef);
        voiture.setClient(client);

        return voiture;
    }
    private Double calculPrixFinal(List<Prestation> prestations, Voiture voiture) {

        double prixTotalFacture = 0.0;
        for (Prestation presta : prestations) {
            prixTotalFacture += presta.getPrix();
        }

        switch (voiture.getCategorie()) {
            case "petit":
                prixTotalFacture -= prixTotalFacture * 0.1;
                break;
            case "gros":
                prixTotalFacture += prixTotalFacture * 0.1;
                break;
            case "fourgon":
                prixTotalFacture += prixTotalFacture * 0.2;
                break;
        }

        prixTotalFacture = arrondir(prixTotalFacture, 2);

        return prixTotalFacture;
    }
    private double arrondir(double nombre, int nbApVirg) {
        return (double) ((int) (nombre * Math.pow(10, nbApVirg) + .5)) / Math.pow(10, nbApVirg);
    }

    @RequestMapping(
            value = "/html2pdf",
            consumes = {"application/x-www-form-urlencoded"})
    public String html2pdf(
            Map<String, Object> data,
            @ModelAttribute("facturation") Facturation facturation,
            TemplateEngine templateEngine,
            SessionStatus sessionStatus, RedirectAttributes ra)
            throws IOException, DocumentException {

        String desktopPath = System.getProperty("user.home") + "\\Desktop";

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");

        templateEngine.setTemplateResolver(templateResolver);
        Context context = new Context();
        context.setVariables(data);
        String html = templateEngine.process("/templates/facture", context);

        OutputStream outputStream = new FileOutputStream(desktopPath + "\\facture" + facturation.getId() + ".pdf");
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();

        ra.addFlashAttribute("message1", "Votre facture a bien été exportée en pdf sur votre bureau !!");
        sessionStatus.setComplete();
        return "redirect:/menu";
    }
}