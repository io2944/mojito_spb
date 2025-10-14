package cryorbiter.mojito_spb.controller;

import cryorbiter.mojito_spb.service.ClientService;
import cryorbiter.mojito_spb.service.CommandeService;
import cryorbiter.mojito_spb.service.DevisService;
import cryorbiter.mojito_spb.service.FactureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LoginController {

    public final ClientService clientService;
    public final DevisService devisService;
    public final CommandeService commandeService;
    private final FactureService factureService;

    public LoginController(ClientService clientService, DevisService devisService, CommandeService commandeService, FactureService factureService) {
        this.clientService = clientService;
        this.devisService = devisService;
        this.commandeService = commandeService;
        this.factureService = factureService;
    }

    // Page publique de login
    @GetMapping("/login")
    public String displayLoginForm() {
        return "mylogin";
    }

    // Page d’accueil après login
    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("nbClients", clientService.count());
        model.addAttribute("nbDevis", devisService.count());
        model.addAttribute("nbCommandes", commandeService.count());
        model.addAttribute("nbFactures", factureService.count());
//        model.addAttribute("revenus", List.of(1200, 1500, 1800, 2100, 1900));
//        model.addAttribute("mois", List.of("Jan", "Fév", "Mar", "Avr", "Mai"));
        return "home";
    }

}
