package cryorbiter.mojito_spb.mapper;

import java.util.List;
import java.util.stream.Collectors;

import cryorbiter.mojito_spb.model.Client;
import org.springframework.stereotype.Component;

import cryorbiter.mojito_spb.dto.ClientDto;

@Component
public class ClientMapper {

    /**
     * Convertit une entité Client en DTO Client
     */
    public ClientDto toDto(Client entity) {
        if (entity == null) {
            return null;
        }
        
        ClientDto dto = new ClientDto();
        dto.setId(entity.getId());
        dto.setNom(entity.getNom());
        dto.setPrenom(entity.getPrenom());
        dto.setEntreprise(entity.getEntreprise());
        dto.setAdresse(entity.getAdresse());
        dto.setTelephoneFixe(entity.getTelephoneFixe());
        dto.setTelephoneMobile(entity.getTelephoneMobile());
        dto.setCommentaire(entity.getCommentaire());
        dto.setEmail(entity.getEmail());
        dto.setActive(entity.isActive());
        dto.setTypeClient(entity.getTypeClient());
        dto.setStatutClient(entity.getStatutClient());
        
        return dto;
    }
    
    /**
     * Convertit un DTO Client en entité Client
     */
    public Client toEntity(ClientDto dto) {
        if (dto == null) {
            return null;
        }
        
        Client entity = new Client();
        entity.setId(dto.getId());
        entity.setNom(dto.getNom());
        entity.setPrenom(dto.getPrenom());
        entity.setEntreprise(dto.getEntreprise());
        entity.setAdresse(dto.getAdresse());
        entity.setTelephoneFixe(dto.getTelephoneFixe());
        entity.setTelephoneMobile(dto.getTelephoneMobile());
        entity.setCommentaire(dto.getCommentaire());
        entity.setEmail(dto.getEmail());
        entity.setActive(dto.getActive());
        entity.setTypeClient(dto.getTypeClient());
        entity.setStatutClient(dto.getStatutClient());
        
        return entity;
    }
    
    /**
     * Met à jour une entité existante avec les données du DTO
     */
    public void updateEntity(Client entity, ClientDto dto) {
        if (entity == null || dto == null) {
            return;
        }
        
        entity.setNom(dto.getNom());
        entity.setPrenom(dto.getPrenom());
        entity.setEntreprise(dto.getEntreprise());
        entity.setAdresse(dto.getAdresse());
        entity.setTelephoneFixe(dto.getTelephoneFixe());
        entity.setTelephoneMobile(dto.getTelephoneMobile());
        entity.setCommentaire(dto.getCommentaire());
        entity.setEmail(dto.getEmail());
        entity.setActive(dto.getActive());
        entity.setTypeClient(dto.getTypeClient());
        entity.setStatutClient(dto.getStatutClient());
    }
    
    /**
     * Convertit une liste d'entités en liste de DTOs
     */
    public List<ClientDto> toDtoList(List<Client> entities) {
        if (entities == null) {
            return null;
        }
        
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Convertit une liste de DTOs en liste d'entités
     */
    public List<Client> toEntityList(List<ClientDto> dtos) {
        if (dtos == null) {
            return null;
        }
        
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
