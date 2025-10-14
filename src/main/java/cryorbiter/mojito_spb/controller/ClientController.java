package cryorbiter.mojito_spb.controller;

import java.util.List;

import cryorbiter.mojito_spb.dto.CommandeDto;
import cryorbiter.mojito_spb.dto.DevisDto;
import cryorbiter.mojito_spb.dto.FactureDto;
import cryorbiter.mojito_spb.service.CommandeService;
import cryorbiter.mojito_spb.service.DevisService;
import cryorbiter.mojito_spb.service.FactureService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import cryorbiter.mojito_spb.service.ClientService;
import cryorbiter.mojito_spb.dto.ClientDto;
import cryorbiter.mojito_spb.enumerations.StatutClient;
import cryorbiter.mojito_spb.enumerations.TypeClient;
import cryorbiter.mojito_spb.form.ClientForm;

@Controller
@Transactional
public class ClientController {

    private final ClientService clientService;
    private final DevisService devisService;
    private final CommandeService commandeService;
    private final FactureService factureService;

    public ClientController(ClientService clientService, DevisService devisService, CommandeService commandeService, FactureService factureService) {
        this.clientService = clientService;
        this.devisService = devisService;
        this.commandeService = commandeService;
        this.factureService = factureService;
    }

    @GetMapping("/listClient")
    public String showForm(Model model,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size,
                           HttpSession  session) {

        Page<ClientDto> clients = clientService.getAllClients(page, size);
        model.addAttribute("clientsPage", clients);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", clients.getTotalPages());
        //List<ClientDto> clients = clientService.getAllClients();
        //System.out.println("debug" + clients.size());
        model.addAttribute("clients", clients);
        return "Client/listClient";
    }

    @GetMapping("/createClient")
    public String showForm(Model model) {
    	boolean isEdit = false; 
    	
        model.addAttribute("clientForm", new ClientForm());
        model.addAttribute("typeClient", TypeClient.values());
        model.addAttribute("statutClient", StatutClient.values());
        model.addAttribute("isEdit", isEdit);
        
        model.addAttribute("buttonValue", isEdit ? "update" : "save");
        
        return "Client/createClient"; // JSP ou Thymeleaf
    }

    @PostMapping("/createClient")
    public String checkClientInfo(@Valid @ModelAttribute("clientForm") ClientForm clientForm,
                         BindingResult result,
                         Model model, HttpSession  session) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(e -> System.out.println(e)); // <--- affichera toutes les erreurs
            model.addAttribute("typeClient", TypeClient.values());
            model.addAttribute("statutClient", StatutClient.values());
            model.addAttribute("isEdit", false);
            System.out.println("*** Erreur : redirection vers /createClient ***");

            return "redirect:/createClient"; // on réaffiche le formulaire avec erreurs
        }

        ClientDto  clientDto = new ClientDto();
        clientDto.setNom(clientForm.getNom());
        clientDto.setPrenom(clientForm.getPrenom());
        clientDto.setAdresse(clientForm.getAdresse());
        clientDto.setEmail(clientForm.getEmail());
        clientDto.setTelephoneFixe(clientForm.getTelephoneFixe());
        clientDto.setTelephoneMobile(clientForm.getTelephoneMobile());
        clientDto.setEntreprise(clientForm.getEntreprise());
        clientDto.setTypeClient(clientForm.getTypeClient());
        clientDto.setCommentaire(clientForm.getCommentaire());
        clientDto.setActive(clientForm.getActive());
        clientDto.setStatutClient(clientForm.getStatutClient());
        clientService.creerClient(clientDto);
        model.addAttribute("success", "Client créé avec succès !");
        return "redirect:/listClient";
    }

    @GetMapping("/deleteClient")
    public String deleteClient(@RequestParam("id") Long id, Model model, HttpSession  session) {
        try {
            ClientDto clientDto = clientService.getClientById(id);
            if (clientDto != null) {
                clientService.supprimerClient(clientDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Erreur ! Pas de client avec id !" + id);
        }
        return "redirect:/listClient";
    }

    @GetMapping("/updateClient/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        ClientDto clientDto = clientService.getClientById(id);
        if (clientDto == null) {
            return "redirect:/listClient";
        }
        ClientForm clientForm = new ClientForm();
        clientForm.setId(clientDto.getId());
        clientForm.setNom(clientDto.getNom());
        clientForm.setPrenom(clientDto.getPrenom());
        clientForm.setAdresse(clientDto.getAdresse());
        clientForm.setEmail(clientDto.getEmail());
        clientForm.setTelephoneFixe(clientDto.getTelephoneFixe());
        clientForm.setTelephoneMobile(clientDto.getTelephoneMobile());
        clientForm.setEntreprise(clientDto.getEntreprise());
        clientForm.setTypeClient(clientDto.getTypeClient());
        clientForm.setCommentaire(clientDto.getCommentaire());
        clientForm.setActive(clientDto.getActive());
        clientForm.setStatutClient(clientDto.getStatutClient());

        boolean isEdit = true;
        
        model.addAttribute("success", "Client créé avec succès !");
        model.addAttribute("typeClient", TypeClient.values());
        model.addAttribute("statutClient", StatutClient.values());
        model.addAttribute("clientForm", clientForm);
        model.addAttribute("isEdit", isEdit);

        return "Client/createClient";
    }

    @PostMapping("/updateClient/{id}")
    public String updateClient(@PathVariable("id") Long id,
                              @Validated @ModelAttribute("clientForm") ClientForm clientForm,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("typeClient", TypeClient.values());
            model.addAttribute("statutClient", StatutClient.values());
            model.addAttribute("clientForm", clientForm);
            model.addAttribute("isEdit", true);
            return "Client/createClient";
        }

        ClientDto clientDto = clientService.getClientById(id);
        if (clientDto == null) {
            return "redirect:/listClient";
        }

        clientDto.setNom(clientForm.getNom());
        clientDto.setPrenom(clientForm.getPrenom());
        clientDto.setAdresse(clientForm.getAdresse());
        clientDto.setEmail(clientForm.getEmail());
        clientDto.setTelephoneFixe(clientForm.getTelephoneFixe());
        clientDto.setTelephoneMobile(clientForm.getTelephoneMobile());
        clientDto.setEntreprise(clientForm.getEntreprise());
        clientDto.setTypeClient(clientForm.getTypeClient());
        clientDto.setCommentaire(clientForm.getCommentaire());
        clientDto.setActive(clientForm.getActive());
        clientDto.setStatutClient(clientForm.getStatutClient());

        clientService.modifierClient(clientDto);
        return "redirect:/listClient";
    }

    // Afficher les détails d'un client
    @GetMapping("/viewClient/{id}")
    public String viewClient(@PathVariable("id") Long id, Model model) {
        ClientDto clientDto = clientService.getClientById(id);
        if (clientDto == null) {
            return "redirect:/listClient";
        }

        List<DevisDto> devises = devisService.getAllByClient(clientDto);
        List<CommandeDto> commandes = commandeService.getAllByClient(clientDto);
        List<FactureDto> factures = factureService.getAllByClient(clientDto);

        model.addAttribute("client", clientDto);
        model.addAttribute("typeClient", TypeClient.values());
        model.addAttribute("statutClient", StatutClient.values());
        model.addAttribute("devises", devises);
        model.addAttribute("commandes", commandes);
        model.addAttribute("factures", factures);

        return "Client/viewClient"; // JSP pour visualisation
    }

}
