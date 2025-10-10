package cryorbiter.mojito_spb.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import cryorbiter.mojito_spb.dto.ClientDto;
import cryorbiter.mojito_spb.dto.CommandeDto;
import cryorbiter.mojito_spb.mapper.ClientMapper;
import cryorbiter.mojito_spb.mapper.CommandeMapper;
import cryorbiter.mojito_spb.model.Client;
import cryorbiter.mojito_spb.model.Facture;
import org.springframework.beans.factory.annotation.Autowired;
import cryorbiter.mojito_spb.dto.FactureDto;
import cryorbiter.mojito_spb.mapper.FactureMapper;
import cryorbiter.mojito_spb.repository.FactureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FactureService{

    private final FactureRepository factureRepository;
    private final FactureMapper factureMapper;
    private final CommandeService commandeService;
    private final CommandeMapper commandeMapper;
    private final ClientMapper clientMapper;

    @Autowired
    public FactureService(FactureRepository factureRepository, FactureMapper factureMapper, CommandeService commandeService, CommandeMapper commandeMapper, ClientMapper clientMapper) {
        this.factureRepository = factureRepository;
        this.factureMapper = factureMapper;
        this.commandeService = commandeService;
        this.commandeMapper = commandeMapper;
        this.clientMapper = clientMapper;
    }

    public FactureDto creerFacture(FactureDto facture) {
        if (facture == null) {
            throw new IllegalArgumentException("facture doit être renseignée");
        }

        // Format de date pour le nom
        String dateStr = new java.text.SimpleDateFormat("yyyyMMdd").format(facture.getDate());

        // Compter combien de devis existent pour cette date
        long countToday = factureRepository.countByDate(facture.getDate());

        // Numéro séquentiel du jour, 001, 002, ...
        String numero = String.format("%03d", countToday + 1);

        // Définir le nom du document
        facture.setNomDocument("FAC_" + dateStr + "_" + numero);

        Facture entity = factureMapper.toEntity(facture);

        // Pour ajouter un client à la facture (depuis la commande)
        if (entity.getCommande() != null && entity.getCommande().getId() != null) {
            CommandeDto commandeDto = commandeService.getCommandeById(entity.getCommande().getId());
            entity.setCommande(commandeMapper.toEntity(commandeDto));
            entity.setClient(entity.getCommande().getClient());
        }
        Facture savedEntity = factureRepository.save(entity);
        System.out.println("La nouvelle dans la bdd " + savedEntity);
        return factureMapper.toDto(savedEntity);
    }

    public FactureDto getFactureById(long id) {
        Optional<Facture> factureOpt = factureRepository.findById(id);
        return factureOpt.map(factureMapper::toDto).orElse(null);
    }

    public void modifierFacture(FactureDto facture) {
        if (facture == null) {
            throw new IllegalArgumentException("facture doit être renseignée");
        }
        
        if (facture.getId() <= 0) {
            throw new IllegalArgumentException("L'identifiant de la facture doit être renseigné pour la modification");
        }
        
        // Vérifier que la facture existe et utiliser la méthode update du mapper
        Optional<Facture> existingEntityOpt = factureRepository.findById(facture.getId());
        if (!existingEntityOpt.isPresent()) {
            throw new IllegalArgumentException("Aucune facture trouvée avec l'identifiant " + facture.getId());
        }
        
        Facture existingEntity = existingEntityOpt.get();
        factureMapper.updateEntity(existingEntity, facture);
        factureRepository.save(existingEntity);
    }

    public void supprimerFacture(FactureDto facture) {
        if (facture == null) {
            throw new IllegalArgumentException("facture doit être renseignée");
        }
        
        if (facture.getId() <= 0) {
            throw new IllegalArgumentException("L'identifiant de la facture doit être renseigné pour la suppression");
        }
        
        factureRepository.deleteById(facture.getId());
    }

    public List<FactureDto> getAllFactures() {
        return factureMapper.toDtoList(factureRepository.findAll());
    }

    public List<FactureDto> getAllByClient(ClientDto client) {
        Client entityFacture = clientMapper.toEntity(client);
        List<Facture> factures = factureRepository.findAllByClient(entityFacture);
        return factures.stream()
                .map(factureMapper::toDto)
                .collect(Collectors.toList());
    }
}
