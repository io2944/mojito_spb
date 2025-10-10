package cryorbiter.mojito_spb.service;

import cryorbiter.mojito_spb.dto.ClientDto;
import cryorbiter.mojito_spb.dto.CommandeDto;
import cryorbiter.mojito_spb.mapper.ClientMapper;
import cryorbiter.mojito_spb.mapper.CommandeMapper;
import cryorbiter.mojito_spb.model.Client;
import cryorbiter.mojito_spb.model.Commande;
import cryorbiter.mojito_spb.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommandeService {
    private final CommandeRepository commandeRepository;
    private final CommandeMapper commandeMapper;
    private final ClientMapper clientMapper;

    @Autowired
    public CommandeService(CommandeRepository commandeRepository, CommandeMapper commandeMapper, ClientMapper clientMapper) {
        this.commandeRepository = commandeRepository;
        this.commandeMapper = commandeMapper;
        this.clientMapper = clientMapper;
    }

    public Optional<CommandeDto> findById(Long id) {
        Commande commandeEntity = commandeRepository.findById(id).orElse(null);
        if (commandeEntity == null) {
            return Optional.empty();
        }
        return Optional.of(commandeMapper.toDto(commandeEntity));
    }

    public CommandeDto creerCommande(CommandeDto commande) {
        if (commande == null) {
            throw new IllegalArgumentException("commande doit être renseignée");
        }

        // Format de date pour le nom
        String dateStr = new java.text.SimpleDateFormat("yyyyMMdd").format(commande.getDate());

        // Compter combien de commandes existent pour cette date
        long countToday = commandeRepository.countByDate(commande.getDate());

        // Numéro séquentiel du jour, 001, 002, ...
        String numero = String.format("%03d", countToday + 1);

        // Définir le nom du document
        commande.setNomDocument("CMD_" + dateStr + "_" + numero);


        Commande entity = commandeMapper.toEntity(commande);
        Commande savedEntity = commandeRepository.save(entity);
        return commandeMapper.toDto(savedEntity);
    }

    public CommandeDto getCommandeById(long id) {
        Optional<Commande> commandeOpt = commandeRepository.findById(id);
        return commandeOpt.map(commandeMapper::toDto).orElse(null);
    }

    public List<CommandeDto> getAllCommandes() {
        return commandeMapper.toDtoList(commandeRepository.findAll());
    }

    public void modifierCommande(CommandeDto commande) {
        if (commande == null) {
            throw new IllegalArgumentException("commande doit être renseignée");
        }

        if (commande.getId() <= 0) {
            throw new IllegalArgumentException("L'identifiant de la commande doit être renseigné pour la modification");
        }

        // Vérifier que la commande existe et utiliser la méthode update du mapper
        Optional<Commande> existingEntityOpt = commandeRepository.findById(commande.getId());
        if (!existingEntityOpt.isPresent()) {
            throw new IllegalArgumentException("Aucune commande trouvée avec l'identifiant " + commande.getId());
        }

        Commande existingEntity = existingEntityOpt.get();
        commandeMapper.updateEntity(existingEntity, commande);
        commandeRepository.save(existingEntity);
    }

    public void supprimerCommande(CommandeDto commande) {
        if (commande == null) {
            throw new IllegalArgumentException("commande doit être renseignée");
        }

        if (commande.getId() <= 0) {
            throw new IllegalArgumentException("L'identifiant de la commande doit être renseigné pour la suppression");
        }

        commandeRepository.deleteById(commande.getId());
    }

    public List<CommandeDto> getAllByClient(ClientDto client) {
        Client entityCommande = clientMapper.toEntity(client);
        List<Commande> commandes = commandeRepository.findAllByClient(entityCommande);
        return commandes.stream()
                .map(commandeMapper::toDto)
                .collect(Collectors.toList());
    }
}
