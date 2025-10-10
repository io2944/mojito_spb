package cryorbiter.mojito_spb.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import cryorbiter.mojito_spb.dto.DevisDto;
import cryorbiter.mojito_spb.model.Client;
import cryorbiter.mojito_spb.dto.ClientDto;
import cryorbiter.mojito_spb.mapper.ClientMapper;
import cryorbiter.mojito_spb.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClientService {

    private ClientRepository clientRepository;
    private ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    public ClientDto creerClient(ClientDto client) {
        if (client == null) {
            throw new IllegalArgumentException("client doit être renseigné");
        }
        
        Client entity = clientMapper.toEntity(client);
        Client savedEntity = clientRepository.save(entity);
        return clientMapper.toDto(savedEntity);
    }

    public ClientDto getClientById(Long id) {
        if (id == null) {
            return null;
        }
        
        Optional<Client> clientOpt = clientRepository.findById(id);
        return clientOpt.map(clientMapper::toDto).orElse(null);
    }

    public ClientDto modifierClient(ClientDto client) {
        if (client == null) {
            throw new IllegalArgumentException("client doit être renseigné");
        }
        
        if (client.getId() == null) {
            throw new IllegalArgumentException("L'identifiant du client doit être renseigné pour la modification");
        }
        
        // Vérifier que le client existe et utiliser la méthode update du mapper
        Optional<Client> existingEntityOpt = clientRepository.findById(client.getId());
        if (!existingEntityOpt.isPresent()) {
            throw new IllegalArgumentException("Aucun client trouvé avec l'identifiant " + client.getId());
        }
        
        Client existingEntity = existingEntityOpt.get();
        clientMapper.updateEntity(existingEntity, client);
        Client savedEntity = clientRepository.save(existingEntity);
        return clientMapper.toDto(savedEntity);
    }

    public void supprimerClient(ClientDto client) {
        if (client == null) {
            throw new IllegalArgumentException("client doit être renseigné");
        }
        
        if (client.getId() == null) {
            throw new IllegalArgumentException("L'identifiant du client doit être renseigné pour la suppression");
        }
        
        clientRepository.deleteById(client.getId());
    }

    public List<ClientDto> getAllClients() {
        System.out.println("debug" + clientRepository.findAll());
        return clientRepository.findAll().stream().map(clientMapper::toDto).collect(Collectors.toList());

    }

}
