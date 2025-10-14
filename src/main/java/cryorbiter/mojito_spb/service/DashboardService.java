package cryorbiter.mojito_spb.service;

import cryorbiter.mojito_spb.repository.ClientRepository;
import cryorbiter.mojito_spb.repository.DevisRepository;
import cryorbiter.mojito_spb.repository.FactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class DashboardService {

    private final FactureRepository factureRepository;
    private final DevisRepository devisRepository;
    private final FactureService factureService;

    @Autowired
    public DashboardService(FactureRepository factureRepository, DevisRepository devisRepository, FactureService factureService) {
        this.factureRepository = factureRepository;
        this.devisRepository = devisRepository;
        this.factureService = factureService;
    }

    public Map<String, Object> getRevenusParMois(){
        List<Object[]> resultats = factureRepository.getTotalParMois();
        List<String> labels = new ArrayList<>();
        List<Double> values = new ArrayList<>();
        for(Object[] resultat : resultats){
            Instant instant = (Instant) resultat[0];
            java.util.Date moisDate = java.util.Date.from(instant);
            Double total = ((Number) resultat[1]).doubleValue();

            // Format du mois → ex : "Jan 2025"
            String moisFormate = new java.text.SimpleDateFormat("MMM yyyy", Locale.FRENCH).format(moisDate);
            labels.add(moisFormate.substring(0, 1).toUpperCase() + moisFormate.substring(1)); // "Janv. 2025"
            values.add(total);

        }

        return Map.of("labels", labels, "values", values);
    }

    public Map<String, Object> getTopClients() {
        List<Object[]> resultats = factureRepository.getTopClientsParChiffreAffaire();

        List<String> labels = new ArrayList<>();
        List<Double> values = new ArrayList<>();

        for (Object[] ligne : resultats) {
            labels.add((String) ligne[0]); // nom du client
            values.add(((Number) ligne[1]).doubleValue());
        }

        return Map.of("labels", labels, "values", values);
    }

    public Map<String, Object> getDevisParStatut() {
        List<Object[]> resultats = devisRepository.countByStatut(); // ex: [statut, count]
        List<String> labels = new ArrayList<>();
        List<Long> values = new ArrayList<>();

        for (Object[] ligne : resultats) {
            labels.add((String) ligne[0]);
            values.add(((Number) ligne[1]).longValue());
        }

        return Map.of("labels", labels, "values", values);
    }

}
