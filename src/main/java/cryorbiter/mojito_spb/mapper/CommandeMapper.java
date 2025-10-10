package cryorbiter.mojito_spb.mapper;

import java.util.List;
import java.util.stream.Collectors;

import cryorbiter.mojito_spb.model.Commande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cryorbiter.mojito_spb.dto.CommandeDto;

@Component
public class CommandeMapper {

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private DevisMapper devisMapper;

    /**
     * Convertit une entité Commande en DTO Commande
     */
    public CommandeDto toDto(Commande entity) {
        if (entity == null) {
            return null;
        }
        
        CommandeDto dto = new CommandeDto();
        // Propriétés héritées de Document
        dto.setId(entity.getId());
        dto.setDate(entity.getDate());
        dto.setPoids(entity.getPoids());
        dto.setTva(entity.getTva());
        dto.setType(entity.getType());
        dto.setLibelle(entity.getLibelle());
        dto.setPrixKilo(entity.getPrixKilo());
        dto.setCommentaire(entity.getCommentaire());
        dto.setNomDocument(entity.getNomDocument());
        
        // Propriétés spécifiques à Commande
        dto.setOrbite(entity.isOrbite());
        dto.setStatutCommande(entity.getStatutCommande());
        
        // Relations
        if (entity.getClient() != null) {
            dto.setClient(clientMapper.toDto(entity.getClient()));
        }
        if (entity.getDevis() != null) {
            dto.setDevis(devisMapper.toDto(entity.getDevis()));
        }
        
        return dto;
    }
    
    /**
     * Convertit un DTO Commande en entité Commande
     */
    public Commande toEntity(CommandeDto dto) {
        if (dto == null) {
            return null;
        }
        
        Commande entity = new Commande();
        // Propriétés héritées de Document
        entity.setId(dto.getId());
        entity.setDate(dto.getDate());
        entity.setPoids(dto.getPoids());
        entity.setTva(dto.getTva());
        entity.setType(dto.getType());
        entity.setLibelle(dto.getLibelle());
        entity.setPrixKilo(dto.getPrixKilo());
        entity.setCommentaire(dto.getCommentaire());
        entity.setNomDocument(dto.getNomDocument());
        
        // Propriétés spécifiques à Commande
        entity.setOrbite(dto.isOrbite());
        entity.setStatutCommande(dto.getStatutCommande());
        
        // Relations
        if (dto.getClient() != null) {
            entity.setClient(clientMapper.toEntity(dto.getClient()));
        }
        if (dto.getDevis() != null) {
            entity.setDevis(devisMapper.toEntity(dto.getDevis()));
        }
        
        return entity;
    }
    
    /**
     * Met à jour une entité existante avec les données du DTO
     */
    public void updateEntity(Commande entity, CommandeDto dto) {
        if (entity == null || dto == null) {
            return;
        }
        
        // Propriétés héritées de Document
        entity.setDate(dto.getDate());
        entity.setPoids(dto.getPoids());
        entity.setTva(dto.getTva());
        entity.setType(dto.getType());
        entity.setLibelle(dto.getLibelle());
        entity.setPrixKilo(dto.getPrixKilo());
        entity.setCommentaire(dto.getCommentaire());
        entity.setNomDocument(dto.getNomDocument());
        
        // Propriétés spécifiques à Commande
        entity.setOrbite(dto.isOrbite());
        entity.setStatutCommande(dto.getStatutCommande());
        
        // Relations
        if (dto.getClient() != null) {
            entity.setClient(clientMapper.toEntity(dto.getClient()));
        }
        if (dto.getDevis() != null) {
            entity.setDevis(devisMapper.toEntity(dto.getDevis()));
        }
    }
    
    /**
     * Convertit une liste d'entités en liste de DTOs
     */
    public List<CommandeDto> toDtoList(List<Commande> entities) {
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
    public List<Commande> toEntityList(List<CommandeDto> dtos) {
        if (dtos == null) {
            return null;
        }
        
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
