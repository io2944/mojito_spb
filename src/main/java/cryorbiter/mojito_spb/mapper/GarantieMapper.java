package cryorbiter.mojito_spb.mapper;

import java.util.List;
import java.util.stream.Collectors;

import cryorbiter.mojito_spb.model.Garantie;
import org.springframework.stereotype.Component;

import cryorbiter.mojito_spb.dto.GarantieDto;

@Component
public class GarantieMapper {

    /**
     * Convertit une entité Garantie en DTO Garantie
     */
    public GarantieDto toDto(Garantie entity) {
        if (entity == null) {
            return null;
        }
        
        GarantieDto dto = new GarantieDto();
        dto.setId(entity.getId());
        dto.setDate(entity.getDate());
        dto.setCommentaire(entity.getCommentaire());
        
        return dto;
    }
    
    /**
     * Convertit un DTO Garantie en entité Garantie
     */
    public Garantie toEntity(GarantieDto dto) {
        if (dto == null) {
            return null;
        }
        
        Garantie entity = new Garantie();
        entity.setId(dto.getId());
        entity.setDate(dto.getDate());
        entity.setCommentaire(dto.getCommentaire());
        
        return entity;
    }
    
    /**
     * Met à jour une entité existante avec les données du DTO
     */
    public void updateEntity(Garantie entity, GarantieDto dto) {
        if (entity == null || dto == null) {
            return;
        }
        
        entity.setDate(dto.getDate());
        entity.setCommentaire(dto.getCommentaire());
    }
    
    /**
     * Convertit une liste d'entités en liste de DTOs
     */
    public List<GarantieDto> toDtoList(List<Garantie> entities) {
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
    public List<Garantie> toEntityList(List<GarantieDto> dtos) {
        if (dtos == null) {
            return null;
        }
        
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
