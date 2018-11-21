package com.carrosserieafpa.carrosserie.web.controller;

import com.carrosserieafpa.carrosserie.dao.*;
import com.carrosserieafpa.carrosserie.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SessionAttributes({"prestation", "prestations"})
@Controller
public class ControllerAdmin {

    private static final String ADMINISTRATION = "/admin";

    @Autowired ActeDao acteDao;
    @Autowired FinitionDao finitionDao;
    @Autowired PrestationDao prestationDao;
    @Autowired ClientDao clientDao;
    @Autowired AdminDao adminDao;

    @ModelAttribute("prestation") public Prestation getPrestation() {
        return new Prestation();
    }
    @ModelAttribute("prestations") public List<Prestation> getPrestas() {
        return new ArrayList<>();
    }

    @PostMapping("/acte")
    public String ajouterNouveauActe(HttpServletRequest httpServletRequest, Acte acte, RedirectAttributes ra) {
        acte.setLibelle(httpServletRequest.getParameter("libelle"));
        acteDao.save(acte);
        ra.addFlashAttribute("message3", "Acte ajouté en base !!");
        return "redirect:" + ADMINISTRATION;
    }

    @PostMapping("/finition")
    public String ajouterNouveauFinition(HttpServletRequest httpServletRequest, Finition finition, RedirectAttributes ra) {
        finition.setLibelle(httpServletRequest.getParameter("libelle2"));
        ra.addFlashAttribute("message4", "Finition ajouté en base !!");
        finitionDao.save(finition);

        return "redirect:" + ADMINISTRATION;
    }

    @PostMapping("/prix")
    public String ajouterPrix(@ModelAttribute("prestation") Prestation prestation, SessionStatus status, RedirectAttributes ra) {
        Prestation presta = prestationDao.findByActeAndFinition(prestation.getActe(), prestation.getFinition());

        if (presta != null) {
            prestationDao.delete(presta);
        }
        prestationDao.save(prestation);
        status.setComplete();
        ra.addFlashAttribute("message5", "Prix ajouté en base !!");

        return "redirect:" + ADMINISTRATION;
    }

    @RequestMapping("/admin/delete/acte")
    public String supprimerUnActe(@ModelAttribute("prestation") Prestation prestation, RedirectAttributes ra){
        Acte acteSupprimer = prestation.getActe();
        acteDao.delete(acteSupprimer);
        ra.addFlashAttribute("message6", "Acte supprimé de la base !!");
        return "redirect:" + ADMINISTRATION;
    }

    @RequestMapping("/admin/delete/finition")
    public String supprimerUneFinition(@ModelAttribute("prestation") Prestation prestation, RedirectAttributes ra){
        Finition finitionSupprimer = prestation.getFinition();
        finitionDao.delete(finitionSupprimer);
        ra.addFlashAttribute("message7", "Finition supprimée de la base !!");
        return "redirect:" + ADMINISTRATION;
    }

    @RequestMapping("/admin/delete/client")
    public String supprimerUnClient(HttpServletRequest httpServletRequest, Client client, RedirectAttributes ra) {

        Long idToDelete = Long.valueOf(httpServletRequest.getParameter("client"));
        Optional<Client> clientTmp = clientDao.findById(idToDelete);
        if (clientTmp.isPresent()) {
            client = clientTmp.get();
        }
        clientDao.delete(client);
        ra.addFlashAttribute("message8", "Client supprimé de la base !!");
        return "redirect:" + ADMINISTRATION;
    }

    @RequestMapping("/verifierLogin")
    public String verifierMotDepasse(HttpServletRequest request) {
        String entryPwd = request.getParameter("password");
        List<Administrateur> admins = adminDao.findAll();

        List<String> passwords = new ArrayList<>();
        for (Administrateur admin : admins) {
            passwords.add(admin.getMotDePasse());
        }

        if (passwords.contains(entryPwd)) {
            return "redirect:" + ADMINISTRATION;
        } else {
            return "/login";
        }
    }

    @RequestMapping("/admin/changerMdp")
    public String changerMdp(HttpServletRequest request, RedirectAttributes ra) {
        String ancienMdp = request.getParameter("ancienMdp");
        Administrateur admin;
        try {
            admin = adminDao.findByMotDePasse(ancienMdp);
            admin.getMotDePasse();
        } catch (NullPointerException e) {
            ra.addFlashAttribute("message", "Ancien mot de passe incorrect !!");
            return "redirect:" + ADMINISTRATION;
        }
        String nouveauMdp = request.getParameter("nouveauMdp");
        admin.setMotDePasse(nouveauMdp);
        adminDao.save(admin);
        ra.addFlashAttribute("message2", "Mot de passe mis à jour !!");
        return "redirect:" + ADMINISTRATION;
    }
}
