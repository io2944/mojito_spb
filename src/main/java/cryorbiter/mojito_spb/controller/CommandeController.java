package cryorbiter.mojito_spb.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cryorbiter.mojito_spb.dto.ClientDto;
import cryorbiter.mojito_spb.service.ClientService;
import cryorbiter.mojito_spb.service.CommandeService;
import cryorbiter.mojito_spb.service.DevisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import cryorbiter.mojito_spb.dto.CommandeDto;
import cryorbiter.mojito_spb.dto.DevisDto;
import cryorbiter.mojito_spb.enumerations.StatutCommande;
import cryorbiter.mojito_spb.enumerations.TypeDocument;
import cryorbiter.mojito_spb.form.CommandeForm;
import org.springframework.web.server.ResponseStatusException;

@Controller
@Transactional
public class CommandeController {

    public static final String SESSION_COMMANDE = "commandes";
    private final ClientService clientService;
    private CommandeService commandeService;
    private DevisService devisService;

    public CommandeController(CommandeService commandeService, DevisService devisService, ClientService clientService) {
        this.commandeService = commandeService;
        this.devisService = devisService;
        this.clientService = clientService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/listeCommande")
    public String showForm(Model model,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size,
                           HttpSession session) {

        Pageable pageable = PageRequest.of(page, size);
        Page<CommandeDto> commandes = commandeService.getAllCommandes(pageable);
        model.addAttribute("commandes", commandes);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", commandes.getTotalPages());
        model.addAttribute("statuts", StatutCommande.values());
        return "Commande/listeCommande";
    }

    @GetMapping("/creerCommande")
    public String showForm(@RequestParam(value="devisId", required=false) Long devisId, Model model) {

        CommandeForm commandeForm = new CommandeForm();

        if (devisId != null) {
            DevisDto devisDto = devisService.getDevisById(devisId);
            if (devisDto != null) {
                commandeForm.setDevis(devisDto);
                commandeForm.setNomDocument(devisDto.getNomDocument());
                commandeForm.setLibelle(devisDto.getLibelle());
                commandeForm.setDate(devisDto.getDate());
                commandeForm.setPoids(devisDto.getPoids());
                commandeForm.setType(devisDto.getType());
                commandeForm.setPrixKilo(devisDto.getPrixKilo());
                commandeForm.setTva(devisDto.getTva());
                commandeForm.setCommentaire(devisDto.getCommentaire());
            }
        }

        List<DevisDto> devisses = devisService.getAllDevis();
        model.addAttribute("devisses", devisses);

        model.addAttribute("commandeForm",commandeForm);
        model.addAttribute("types", TypeDocument.values());
        model.addAttribute("statuts", StatutCommande.values());
        //model.addAttribute("isEdit", false);

        return "Commande/creerCommande"; // JSP ou Thymeleaf
    }

    @GetMapping("/creerCommande/devis/{devisId}")
    @ResponseBody
    public DevisDto getDevisByIdAjax(@PathVariable("devisId") Long devisId) {
        DevisDto devisDto = devisService.getDevisById(devisId);
        if (devisDto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Devis introuvable");
        }
        return devisDto;
    }

    @PostMapping("/creerCommande")
    public String checkCommandeInfo(@Valid @ModelAttribute("commandeForm") CommandeForm commandeForm,
                                  BindingResult result,
                                  Model model) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(System.out::println); // <--- affichera toutes les erreurs
            model.addAttribute("statuts", StatutCommande.values());
            //model.addAttribute("isEdit", false);
            System.out.println("*** Erreur : redirection vers /creerCommande ***");

            return "Commande/creerCommande"; // on réaffiche le formulaire avec erreurs
        }

        // Récupérer le devis depuis l'id
        if (commandeForm.getDevis() == null && commandeForm.getDevisId() != null) {
            DevisDto devisDto = devisService.getDevisById(commandeForm.getDevisId());
            System.out.println(devisDto + " client=" + devisDto.getClient());

            if  (devisDto != null) {
                commandeForm.setDevis(devisDto);
                if (devisDto.getClient() != null) {
                    commandeForm.setClient(devisDto.getClient());
                    commandeForm.setClientId(devisDto.getClient().getId());
                }
            }
        }

        System.out.println(devisService+ "le devis recupere " + commandeForm.getClient() + " le client aussi");
        CommandeDto commandeDto = new CommandeDto();
        commandeDto.setDevis(commandeForm.getDevis());
        commandeDto.setClient(commandeForm.getClient()); // héritage du client du devis
        commandeDto.setNomDocument(commandeForm.getNomDocument());
        commandeDto.setLibelle(commandeForm.getLibelle());
        commandeDto.setDate(commandeForm.getDate());
        commandeDto.setPoids(commandeForm.getPoids());
        commandeDto.setType(commandeForm.getType());
        commandeDto.setPrixKilo(commandeForm.getPrixKilo());
        commandeDto.setTva(commandeForm.getTva());
        commandeDto.setCommentaire(commandeForm.getCommentaire());
        commandeDto.setOrbite(commandeForm.isOrbite());
        commandeDto.setStatutCommande(commandeForm.getStatutCommande());

        commandeService.creerCommande(commandeDto);

        return "redirect:/listeCommande";
    }

    @GetMapping("deleteCommande")
    public String deleteCommande(@RequestParam("id") Long id, Model model, HttpSession session) {
        try {
            CommandeDto commande = commandeService.getCommandeById(id);
            if (commande != null) {
                commandeService.supprimerCommande(commande);
                session.removeAttribute(SESSION_COMMANDE);
            }
        } catch (Exception e) {
            System.out.println("*** Erreur : redirection vers /deleteCommande ***");
        }
        return "redirect:/listeCommande";
    }

    @GetMapping("/viewCommande/{id}")
    public String viewCommande(@PathVariable("id") Long id, Model model) {
        CommandeDto commande = commandeService.getCommandeById(id);
        if (commande == null) {
            return "redirect:/listeCommande"; // sécurité si l'id n'existe pas
        }
        model.addAttribute("commande", commande);
        return "Commande/viewCommande";
    }

    @GetMapping("/modifierCommande/{id}")
    public String showEditForm(@PathVariable("id")Long id, Model model) {
        CommandeDto commande = commandeService.getCommandeById(id);
        if (commande == null) {
            return "redirect:/listeCommande";
        }
        CommandeForm commandeForm = new CommandeForm();
        commandeForm.setId(commande.getId());
        commandeForm.setDevis(commande.getDevis());
        commandeForm.setClient(commande.getClient());
        commandeForm.setClientId(commande.getClient().getId());
        commandeForm.setDevisId(commande.getDevis().getId());
        commandeForm.setNomDocument(commande.getNomDocument());
        commandeForm.setLibelle(commande.getLibelle());
        commandeForm.setDate(commande.getDate());
        commandeForm.setPoids(commande.getPoids());
        commandeForm.setType(commande.getType());
        commandeForm.setPrixKilo(commande.getPrixKilo());
        commandeForm.setTva(commande.getTva());
        commandeForm.setCommentaire(commande.getCommentaire());
        commandeForm.setOrbite(commande.isOrbite());
        commandeForm.setStatutCommande(commande.getStatutCommande());

        model.addAttribute("commandeForm", commandeForm);
        model.addAttribute("types", TypeDocument.values());
        model.addAttribute("statuts", StatutCommande.values());
//        model.addAttribute("isEdit", true);

        return "Commande/modifierCommande";
    }

    @PostMapping("/modifierCommande/{id}")
    public String modifierCommande(@PathVariable("id")Long id,
                                   @Valid @ModelAttribute("commandeForm") CommandeForm commandeForm,
                                   BindingResult result,
                                   Model model) {
        if (result.hasErrors()) {
            model.addAttribute("statuts", StatutCommande.values());
            //model.addAttribute("isEdit", false);
            model.addAttribute("commandeForm", commandeForm);
            model.addAttribute("types", TypeDocument.values());
            System.out.println("*** Erreur : redirection vers /deleteCommande ***");
            return "Commande/modifierCommande";
        }

        CommandeDto commande = commandeService.getCommandeById(id);
        if (commande == null) {
            return "redirect:/listeCommande";
        }

        commande.setClient(commandeForm.getClient());
        commande.setDevis(commandeForm.getDevis());
        commande.setNomDocument(commandeForm.getNomDocument());
        commande.setLibelle(commandeForm.getLibelle());
        commande.setDate(commandeForm.getDate());
        commande.setPoids(commandeForm.getPoids());
        commande.setType(commandeForm.getType());
        commande.setPrixKilo(commandeForm.getPrixKilo());
        commande.setTva(commandeForm.getTva());
        commande.setCommentaire(commandeForm.getCommentaire());
        commande.setOrbite(commandeForm.isOrbite());
        commande.setStatutCommande(commandeForm.getStatutCommande());

        commandeService.modifierCommande(commande);
        return "redirect:/listeCommande";
    }

}
