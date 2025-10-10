package cryorbiter.mojito_spb.controller;

import cryorbiter.mojito_spb.service.CommandeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import cryorbiter.mojito_spb.service.FactureService;
import cryorbiter.mojito_spb.dto.CommandeDto;
import cryorbiter.mojito_spb.dto.FactureDto;
import cryorbiter.mojito_spb.enumerations.StatutFacture;
import cryorbiter.mojito_spb.enumerations.TypeDocument;
import cryorbiter.mojito_spb.form.FactureForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@Transactional
public class FactureController {

    private final FactureService factureService;
    private final CommandeService commandeService;

    public static final String SESSION_FACTURE = "factures";

    @Autowired
    public FactureController(FactureService factureService, CommandeService  commandeService) {
        this.factureService = factureService;
        this.commandeService = commandeService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/listeFacture")
    public String showForm(Model model, HttpSession session) {
        List<FactureDto> factures = factureService.getAllFactures();
        model.addAttribute("types", TypeDocument.values());
        model.addAttribute("statuts", StatutFacture.values());
        model.addAttribute("factures", factures);
        return "Facture/listeFacture";
    }

    @GetMapping("/facture/commande/{id}")
    @ResponseBody
    public CommandeDto getCommande(@PathVariable Long id) {
        CommandeDto commande = commandeService.getCommandeById(id);
        if (commande == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Commande introuvable");
        }
        return commande;
    }


    @GetMapping("/createFacture")
    public String showForm(@RequestParam(value="commandeId", required=false) Long commandeId, Model model) {
        FactureForm factureForm = new FactureForm();
        if (commandeId != null) {
            CommandeDto commande = commandeService.getCommandeById(commandeId);
            if (commande != null) {
                factureForm.setCommande(commande);
                factureForm.setNomDocument(commande.getNomDocument());
                factureForm.setDate(commande.getDate());
                factureForm.setType(commande.getType());
                factureForm.setPoids(commande.getPoids());
                factureForm.setPrixKilo(commande.getPrixKilo());
                factureForm.setTva(commande.getTva());
                factureForm.setClient(commande.getClient());
                factureForm.setLibelle(commande.getLibelle());
                factureForm.setCommentaire(commande.getCommentaire());
            }
        }

        List<CommandeDto> commandes = commandeService.getAllCommandes();
        model.addAttribute("commandes", commandes);

        model.addAttribute("factureForm",factureForm);
        model.addAttribute("types", TypeDocument.values());
        model.addAttribute("statuts", StatutFacture.values());
        return "Facture/createFacture";
    }

    @PostMapping("/createFacture")
    public String checkFactureInfo(@Valid FactureForm factureForm, BindingResult result, Model  model, HttpSession session) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(e -> System.out.println(e));
            model.addAttribute("types", TypeDocument.values());
            model.addAttribute("statuts", StatutFacture.values());
            return "Facture/createFacture";
        }

//        CommandeDto commandeDto = commandeDAOJpa.getCommandeById(commandeId);
//        if (commandeDto == null) {
//            return "redirect:/listeCommande";
//        }

        if (factureForm.getCommande() == null && factureForm.getCommandeId() != null) {
            CommandeDto commandeDto = commandeService.getCommandeById(factureForm.getCommandeId());
//            factureForm.setCommande(commandeDto);
            System.out.println(commandeDto + " client=" + commandeDto.getClient());

            if (commandeDto != null) {
                factureForm.setCommande(commandeDto);
                if (commandeDto.getClient() != null) {
                    factureForm.setClient(commandeDto.getClient());
                    factureForm.setClientId(commandeDto.getClient().getId());
                }
            }
        }

        FactureDto factureDto = new FactureDto();
        factureDto.setCommande(factureForm.getCommande());
        factureDto.setClient(factureForm.getClient());
        factureDto.setLibelle(factureForm.getLibelle());
        factureDto.setNomDocument(factureForm.getNomDocument());
        factureDto.setDate(factureForm.getDate());
        factureDto.setPoids(factureForm.getPoids());
        factureDto.setPrixKilo(factureForm.getPrixKilo());
        factureDto.setTva(factureForm.getTva());
        factureDto.setType(factureForm.getType());
        factureDto.setCommentaire(factureForm.getCommentaire());
        factureDto.setStatutFacture(factureForm.getStatutFacture());

        FactureDto saved = factureService.creerFacture(factureDto);
        System.out.println("La nouvelle  " + saved);
        return "redirect:/listeFacture";
    }

    @GetMapping("deleteFacture")
    public String deleteFacture(@RequestParam("id") Long id, Model model, HttpSession session) {
        try {
            FactureDto facture = factureService.getFactureById(id);
            if (facture != null) {
                factureService.supprimerFacture(facture);
                session.removeAttribute(SESSION_FACTURE);
            }
        } catch (Exception e) {
            System.out.println("*** Erreur : redirection vers /deleteFacture ***");
        }
        return "redirect:/listeFacture";
    }

    @GetMapping("/viewFacture/{id}")
    public String viewFacture(@PathVariable("id") Long id, Model model) {
        FactureDto facture = factureService.getFactureById(id);
        if (facture == null) {
            return "redirect:/listeFacture"; // sécurité si l'id n'existe pas
        }
        model.addAttribute("facture", facture);
        return "Facture/viewFacture"; // JSP pour visualiser le devis
    }

    @GetMapping("/modifierFacture/{id}")
    public String showEditForm(@PathVariable("id")Long id, Model model) {
        FactureDto facture = factureService.getFactureById(id);
        if (facture == null) {
            return "redirect:/listeFacture";
        }

        FactureForm factureForm = new FactureForm();
        factureForm.setId(facture.getId());
        factureForm.setCommande(facture.getCommande());
        factureForm.setClient(facture.getClient());
        factureForm.setNomDocument(facture.getNomDocument());
        factureForm.setDate(facture.getDate());
        factureForm.setType(facture.getType());
        factureForm.setPoids(facture.getPoids());
        factureForm.setPrixKilo(facture.getPrixKilo());
        factureForm.setTva(facture.getTva());
        factureForm.setLibelle(facture.getLibelle());
        factureForm.setCommentaire(facture.getCommentaire());
        factureForm.setStatutFacture(facture.getStatutFacture());

        factureForm.setClientId(facture.getClient().getId());

        model.addAttribute("factureForm", factureForm);
        model.addAttribute("types", TypeDocument.values());
        model.addAttribute("statuts", StatutFacture.values());
        return "Facture/modifierFacture";
    }

    @PostMapping("/modifierFacture/{id}")
    public String modifierFacture(@PathVariable("id")Long id,
                                  @Valid @ModelAttribute("factureForm") FactureForm factureForm,
                                  BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("statuts", StatutFacture.values());
            model.addAttribute("factureForm", factureForm);
            model.addAttribute("types", TypeDocument.values());
            return "Facture/modifierFacture";
        }

        FactureDto facture = factureService.getFactureById(id);
        if (facture == null) {
            return "redirect:/listeFacture";
        }
        facture.setCommande(factureForm.getCommande());
        facture.setNomDocument(factureForm.getNomDocument());
        facture.setDate(factureForm.getDate());
        facture.setPoids(factureForm.getPoids());
        facture.setPrixKilo(factureForm.getPrixKilo());
        facture.setTva(factureForm.getTva());
        facture.setType(factureForm.getType());
        facture.setClient(factureForm.getClient());
        facture.setLibelle(factureForm.getLibelle());
        facture.setCommentaire(factureForm.getCommentaire());
        facture.setStatutFacture(factureForm.getStatutFacture());

        factureService.modifierFacture(facture);
        return "redirect:/listeFacture";
    }

}
