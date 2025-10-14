package cryorbiter.mojito_spb.controller;

import cryorbiter.mojito_spb.dto.ClientDto;
import cryorbiter.mojito_spb.dto.CommandeDto;
import cryorbiter.mojito_spb.dto.DevisDto;
import cryorbiter.mojito_spb.dto.FactureDto;
import cryorbiter.mojito_spb.service.ClientService;
import cryorbiter.mojito_spb.service.CommandeService;
import cryorbiter.mojito_spb.service.DevisService;
import cryorbiter.mojito_spb.service.FactureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {
    private final ClientService clientService;
    private final DevisService devisService;
    private final CommandeService commandeService;
    private final FactureService factureService;

    public SearchController(ClientService clientService, DevisService devisService,
                            CommandeService commandeService, FactureService factureService) {
        this.clientService = clientService;
        this.devisService = devisService;
        this.commandeService = commandeService;
        this.factureService = factureService;
    }

    @GetMapping("/search")
    public String rechercher(@RequestParam("query") String query, Model model) {
        List<ClientDto> clients = clientService.findByNom(query);
        List<DevisDto> devis = devisService.findByLibelle(query);
        List<CommandeDto> commandes = commandeService.findByLibelle(query);
        List<FactureDto> factures = factureService.findByLibelle(query);

        model.addAttribute("query", query);
        model.addAttribute("clients", clients);
        model.addAttribute("devis", devis);
        model.addAttribute("commandes", commandes);
        model.addAttribute("factures", factures);
        return "Search/results";
    }
}
