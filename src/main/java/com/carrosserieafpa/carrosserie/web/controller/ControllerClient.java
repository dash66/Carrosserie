package com.carrosserieafpa.carrosserie.web.controller;

import com.carrosserieafpa.carrosserie.dao.*;
import com.carrosserieafpa.carrosserie.entity.*;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SessionAttributes({"client", "prestations", "voiture", "facturation"})
@Controller
public class ControllerClient {


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
    public String enregistrementClient(HttpServletRequest request, @ModelAttribute("client") Client client) {
        client = this.creationClient(client, request);

        Voiture voiture = this.creationVoiture(client, request);
        Collection<Voiture> tutures = new HashSet<>();
        tutures.add(voiture);
        client.setVoiture(tutures);

        if (client.getNom() != null) {
            clientDao.save(client);
            voitureDao.save(voiture);
        }

        return "redirect:/vueEnregistrement";
    }

    @RequestMapping("/createNewVehicule")
    public String creationNewVoiture(@ModelAttribute("client") Client client, HttpServletRequest request, @ModelAttribute("voiture") Voiture voiture) {

        voiture.setMarque(request.getParameter("marque"));
        voiture.setImmat(request.getParameter("immat"));
        voiture.setModele(request.getParameter("modele"));
        voiture.setCodeCouleur(request.getParameter("couleur"));
        voiture.setCategorie(request.getParameter("categorie"));
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-mm-dd");
        String date = request.getParameter("date");
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateDef = new SimpleDateFormat("dd-MM-yyyy").format(date1);

        voiture.setDate(dateDef);
        voiture.setClient(client);

        client.getVoiture().add(voiture);

        voitureDao.save(voiture);

        return "redirect:/vueEnregistrement";
    }

    @RequestMapping("/setVoitureClient")
    public String settageVoitureClient(HttpServletRequest request, Model model) {
        Voiture voiture = new Voiture();

        Long id = Long.valueOf(request.getParameter("vehicule"));
        Optional<Voiture> tuture = voitureDao.findById(id);
        if (tuture.isPresent()) {
            voiture = tuture.get();
        }
        model.addAttribute("voiture", voiture);


        return "redirect:/vueEnregistrement";
    }

    @RequestMapping("/addPresta")
    public String ajouterPrestaList(RedirectAttributes ra,
                                    @ModelAttribute("prestation") Prestation prestation,
                                    @ModelAttribute("client") Client client, @ModelAttribute("voiture") Voiture voiture) {
        Acte acte = prestation.getActe();
        Finition finition = prestation.getFinition();

        prestation.setId_presta(prestationDao.FindIdByActeAndFinition(acte, finition));
        try {
            prestation.setPrix(prestationDao.findPrixById(prestation.getId_presta()));
        } catch (NullPointerException e) {
        }
        ra.addAttribute(prestation);
        return "redirect:/savePresta";
    }

    @RequestMapping("/savePresta")
    public String sauvegarderPrestas(@ModelAttribute("prestation") Prestation prestation, @ModelAttribute("client") Client client, @ModelAttribute("prestations") List<Prestation> prestations, Model model) {
        prestations.add(prestation);

        model.addAttribute("prestations", prestations);
        model.addAttribute("prestation", prestation);
        return "redirect:/vueEnregistrement";
    }

    @RequestMapping("/saveFacture")
    public String sauvegarderFacture(@ModelAttribute("prestations") List<Prestation> prestations,
                                     @ModelAttribute("client") Client client,
                                     @ModelAttribute("voiture") Voiture voiture,
                                     @ModelAttribute("facturation") Facturation facturation,
                                     RedirectAttributes ra) {

        Collection<Prestation> prestationsColl = new HashSet<>();

        prestationsColl.addAll(prestations);
        facturation.setPrestation(prestationsColl);

        Double prixFactureCalcule = calculPrixFinal(prestations, voiture);

        facturation.setPrix(prixFactureCalcule);
        LocalDateTime HeureNonFormatee = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy à HH:mm");
        String formatDateTime = HeureNonFormatee.format(timeFormatter);
        facturation.setDate(formatDateTime);
        facturation.setClient(client);
        facturation.setVoiture(voiture);
        facturationDao.save(facturation);

        ra.addAttribute("client", client);
        ra.addAttribute("facturation", facturation);


        return "redirect:/facturation/" + facturation.getId();
    }

    public Double calculPrixFinal(@ModelAttribute("prestations") List<Prestation> prestations, @ModelAttribute("voiture") Voiture voiture) {

        Double prixTotalFacture = 0.0;
        for (Prestation presta : prestations) {
            Double prixParPresta = presta.getPrix();

            prixTotalFacture = prixTotalFacture + prixParPresta;
        }

        Double prixFinal1 = prixTotalFacture;
        switch (voiture.getCategorie()) {
            case "petit":
                prixFinal1 -= prixTotalFacture * 0.1;
                break;
            case "moyen":
                prixFinal1 = prixTotalFacture;
                break;
            case "gros":
                prixFinal1 += prixTotalFacture * 0.1;
                break;
            case "fourgon":
                prixFinal1 += prixTotalFacture * 0.2;
                break;
        }

        Double prixFinal = arrondir(prixFinal1, 2);

        return prixFinal;
    }

    public double arrondir(double nombre, double nbApVirg) {
        return (double) ((int) (nombre * Math.pow(10, nbApVirg) + .5)) / Math.pow(10, nbApVirg);
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

    public Voiture creationVoiture(@ModelAttribute("client") Client client, HttpServletRequest request) {

        Voiture voiture = new Voiture();
        voiture.setMarque(request.getParameter("marque"));
        voiture.setImmat(request.getParameter("immat"));
        voiture.setModele(request.getParameter("modele"));
        voiture.setCodeCouleur(request.getParameter("couleur"));
        voiture.setCategorie(request.getParameter("categorie"));
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-mm-dd");
        String date = request.getParameter("date");
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateDef = new SimpleDateFormat("dd-MM-yyyy").format(date1);

        voiture.setDate(dateDef);


        voiture.setClient(client);

        return voiture;
    }

    @RequestMapping("/deletePresta")
    public String supprimerPrestation(@ModelAttribute("prestation") Prestation prestation,
                                      @ModelAttribute("prestations") List<Prestation> prestations,
                                      Model model) {

        Optional<Prestation> prestationTmp = prestationDao.findById(prestation.getId_presta());

        if (prestationTmp.isPresent()) {
            prestation = prestationTmp.get();
        }
        if (prestations != null && !prestations.isEmpty()) {
            int number = -1;

            for (Prestation presta : prestations) {
                if (presta.getId_presta() == prestation.getId_presta()) {

                    number = prestations.indexOf(presta);
                }
            }
            prestations.remove(number);
            model.addAttribute("prestations", prestations);
            model.addAttribute("prestation", prestation);
        }
        return "redirect:/vueEnregistrement";
    }

    @RequestMapping(value = "/html2pdf", consumes = {"application/x-www-form-urlencoded"} )
    public String html2pdf(Map<String, Object> data, @ModelAttribute("facturation") Facturation facturation, TemplateEngine templateEngine, SessionStatus sessionStatus) throws IOException, DocumentException {

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");

        templateEngine.setTemplateResolver(templateResolver);
        Context context = new Context();
        context.setVariables(data);
        String html = templateEngine.process("/templates/facture", context);

        OutputStream outputStream = new FileOutputStream("facture.pdf");
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();

        sessionStatus.setComplete();
       return "redirect:/menu";
    }
  }