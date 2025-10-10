package cryorbiter.mojito_spb.mapper;

import java.util.List;
import java.util.stream.Collectors;

import cryorbiter.mojito_spb.model.Devis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cryorbiter.mojito_spb.dto.DevisDto;

@Component
public class DevisMapper {

    @Autowired
    private ClientMapper clientMapper;

    /**
     * Convertit une entité Devis en DTO Devis
     */
    public DevisDto toDto(Devis entity) {
        if (entity == null) {
            return null;
        }
        
        DevisDto dto = new DevisDto();
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
        
        // Propriétés spécifiques à Devis
        dto.setStatut(entity.getStatut());
        
        // Relations
        if (entity.getClient() != null) {
            dto.setClient(clientMapper.toDto(entity.getClient()));
        }
        
        return dto;
    }
    
    /**
     * Convertit un DTO Devis en entité Devis
     */
    public Devis toEntity(DevisDto dto) {
        if (dto == null) {
            return null;
        }
        
        Devis entity = new Devis();
        // Propriétés héritées de Document
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setDate(dto.getDate());
        entity.setPoids(dto.getPoids());
        entity.setTva(dto.getTva());
        entity.setType(dto.getType());
        entity.setLibelle(dto.getLibelle());
        entity.setPrixKilo(dto.getPrixKilo());
        entity.setCommentaire(dto.getCommentaire());
        entity.setNomDocument(dto.getNomDocument());
        
        // Propriétés spécifiques à Devis
        entity.setStatut(dto.getStatut());
        
        // Relations
        if (dto.getClient() != null) {
            entity.setClient(clientMapper.toEntity(dto.getClient()));
        }
        
        return entity;
    }
    
    /**
     * Met à jour une entité existante avec les données du DTO
     */
    public void updateEntity(Devis entity, DevisDto dto) {
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
        
        // Propriétés spécifiques à Devis
        entity.setStatut(dto.getStatut());
        
        // Relations
        if (dto.getClient() != null) {
            entity.setClient(clientMapper.toEntity(dto.getClient()));
        }
    }
    
    /**
     * Convertit une liste d'entités en liste de DTOs
     */
    public List<DevisDto> toDtoList(List<Devis> entities) {
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
    public List<Devis> toEntityList(List<DevisDto> dtos) {
        if (dtos == null) {
            return null;
        }
        
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
