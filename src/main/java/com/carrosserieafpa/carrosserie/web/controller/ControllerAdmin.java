package com.carrosserieafpa.carrosserie.web.controller;

import com.carrosserieafpa.carrosserie.dao.*;
import com.carrosserieafpa.carrosserie.entity.Acte;
import com.carrosserieafpa.carrosserie.entity.Client;
import com.carrosserieafpa.carrosserie.entity.Finition;
import com.carrosserieafpa.carrosserie.entity.Prestation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SessionAttributes({"client", "prestation", "prestations"})
@Controller
public class ControllerAdmin {

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


    @ModelAttribute("prestation")
    public Prestation getPrestation() {
        return new Prestation();
    }

    @ModelAttribute("prestations")
    public List<Prestation> getPrestas() {
        return new ArrayList<>();
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

    @PostMapping("/prix")
    public String ajouterPrix(@ModelAttribute("prestation") Prestation prestation, SessionStatus status) {

        prestationDao.save(prestation);
        status.setComplete();

        return "redirect:/admin";
    }

    @RequestMapping("/admin/delete/acte")
    public String supprimerUnActe(
            Acte acte,
            HttpServletRequest httpServletRequest,
            @ModelAttribute("prestation") Prestation prestation) /*@PathVariable ("id") Long id*/ {

        String ActeASupprimer = httpServletRequest.getParameter("acte");
        Acte acteSupprimer = prestation.getActe();
        acteDao.delete(acteSupprimer);

        return "redirect:/admin";
    }

    @RequestMapping("/admin/delete/client")
    public String supprimerUnClient(HttpServletRequest httpServletRequest, Client client) {

        Long idToDelete = Long.valueOf(httpServletRequest.getParameter("client"));
        Optional<Client> clientTmp = clientDao.findById(idToDelete);
        if (clientTmp.isPresent()) {
            client = clientTmp.get();
        }
        clientDao.delete(client);

        return "redirect:/admin";
    }

    @RequestMapping("/admin/setClient")
    public String setClientToDeleteVoiture(HttpServletRequest request, Model model, RedirectAttributes ra) {
        Client client = new Client();

        Long id = Long.valueOf(request.getParameter("proprio"));
        System.out.println(id);
        Optional<Client> cleint = clientDao.findById(id);
        if (cleint.isPresent()) {
            client = cleint.get();
        }
        model.addAttribute("client", client);
        ra.addAttribute("client", client);
        return "redirect:/admin/delete/voiture";
    }


    @RequestMapping("/admin/delete/voiture")
    public String supprimerUnVehicule(HttpServletRequest request, Model model, @ModelAttribute("client") Client client) {


        Long id = Long.valueOf(request.getParameter("proprio"));
        Optional<Client> cleint = clientDao.findById(id);
        if (cleint.isPresent()) {
            client = cleint.get();
        }
        model.addAttribute("client", client);

        return "redirect:/admin";
    }

    @RequestMapping("/admin/delete/finition")
    public String supprimerUneFinition(
            Finition finition,
            HttpServletRequest httpServletRequest,
            @ModelAttribute("prestation") Prestation prestation) /*@PathVariable ("id") Long id*/ {

        String FinitionASupprimer = httpServletRequest.getParameter("finition");
        Finition finitionSupprimer = prestation.getFinition();
        finitionDao.delete(finitionSupprimer);

        return "redirect:/admin";
    }
}
