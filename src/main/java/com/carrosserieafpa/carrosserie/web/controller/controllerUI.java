package com.carrosserieafpa.carrosserie.web.controller;

import com.carrosserieafpa.carrosserie.dao.ActeDao;
import com.carrosserieafpa.carrosserie.dao.FacturationDao;
import com.carrosserieafpa.carrosserie.dao.FinitionDao;
import com.carrosserieafpa.carrosserie.dao.PrestationDao;
import com.carrosserieafpa.carrosserie.entity.Acte;
import com.carrosserieafpa.carrosserie.entity.Facturation;
import com.carrosserieafpa.carrosserie.entity.Finition;
import com.carrosserieafpa.carrosserie.entity.Prestation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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


    @RequestMapping(value={"/menu", "/"})

    public String menu(Model model) {

        return "form-menu";
    }

    @RequestMapping("/consulter")
    public String consulter(Model model) {

        return "form-consulter";
    }

    @GetMapping("/enregistrer")
    public String enregistrer(Model model, HttpServletRequest request) {

        List<Finition> finitions = finitionDao.findAll();
        List<Acte> actes = acteDao.findAll();
        List<Facturation> factures = facturationDao.findAll();
        Facturation facture = new Facturation();
        Prestation prestation = new Prestation();

        model.addAttribute("prestation", prestation);
        model.addAttribute("factures", factures);
        model.addAttribute("actes", actes);
        model.addAttribute("finitions", finitions);

        return "form-enregistrement";
    }

    @PostMapping("/enregistrer")
    public String ajouterPresta(Prestation prestation, HttpServletRequest request) {

        System.out.println(prestation.toString());
        Long idActe = Long.valueOf(request.getParameter("idActeSaMere"));
        Long idFinition = Long.valueOf(request.getParameter("idFinitionSaMere"));
        Facturation facture = new Facturation();
        Collection<Prestation> listeprestas = new ArrayList<>();
        prestation.setId_presta(prestationDao.FindIdByActeAndFinition(idActe, idFinition));
        listeprestas.add(prestation);
        facturationDao.save(facture);

        return "form-enregistrement";
    }

}
