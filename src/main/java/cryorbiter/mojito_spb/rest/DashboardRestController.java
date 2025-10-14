package cryorbiter.mojito_spb.rest;

import cryorbiter.mojito_spb.dto.ClientDto;
import cryorbiter.mojito_spb.dto.CommandeDto;
import cryorbiter.mojito_spb.dto.DevisDto;
import cryorbiter.mojito_spb.dto.FactureDto;
import cryorbiter.mojito_spb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardRestController {
    private final ClientService clientService;
    private final DevisService devisService;
    private final CommandeService commandeService;
    private final FactureService factureService;
    private final DashboardService dashboardService;

    @Autowired
    public DashboardRestController(ClientService clientService, DevisService devisService, CommandeService commandeService, FactureService factureService, DashboardService dashboardService) {
        this.clientService = clientService;
        this.devisService = devisService;
        this.commandeService = commandeService;
        this.factureService = factureService;
        this.dashboardService = dashboardService;
    }

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("nbClients", clientService.count());
        stats.put("nbDevis", devisService.count());
        stats.put("nbCommandes", commandeService.count());
        stats.put("nbFactures", factureService.count());
        return stats;
    }

    @GetMapping("/revenus")
    public Map<String, Object> getRevenusParMois() {

        return dashboardService.getRevenusParMois();
    }

    @GetMapping("/top-clients")
    public Map<String, Object> getTopClients() {
        return dashboardService.getTopClients();
    }

    @GetMapping("/devis-par-statut")
    public Map<String, Object> getDevisParStatut() {
        return dashboardService.getDevisParStatut();
    }

}

