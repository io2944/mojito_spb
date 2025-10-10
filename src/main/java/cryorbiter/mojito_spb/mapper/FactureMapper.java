package cryorbiter.mojito_spb.mapper;

import java.util.List;
import java.util.stream.Collectors;

import cryorbiter.mojito_spb.model.Facture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cryorbiter.mojito_spb.dto.FactureDto;

@Component
public class FactureMapper {

    @Autowired
    private ClientMapper clientMapper;
    
    @Autowired
    private CommandeMapper commandeMapper;

    /**
     * Convertit une entité Facture en DTO Facture
     */
    public FactureDto toDto(Facture entity) {
        if (entity == null) {
            return null;
        }
        
        FactureDto dto = new FactureDto();
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
        
        // Propriétés spécifiques à Facture
        dto.setStatutFacture(entity.getStatutFacture());
        
        // Relations
        if (entity.getClient() != null) {
            dto.setClient(clientMapper.toDto(entity.getClient()));
        }
        if (entity.getCommande() != null) {
            dto.setCommande(commandeMapper.toDto(entity.getCommande()));
        }
        
        return dto;
    }
    
    /**
     * Convertit un DTO Facture en entité Facture
     */
    public Facture toEntity(FactureDto dto) {
        if (dto == null) {
            return null;
        }
        
        Facture entity = new Facture();
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
        
        // Propriétés spécifiques à Facture
        entity.setStatutFacture(dto.getStatutFacture());
        
        // Relations
        if (dto.getClient() != null) {
            entity.setClient(clientMapper.toEntity(dto.getClient()));
        }
        if (dto.getCommande() != null) {
            entity.setCommande(commandeMapper.toEntity(dto.getCommande()));
        }
        
        return entity;
    }
    
    /**
     * Met à jour une entité existante avec les données du DTO
     */
    public void updateEntity(Facture entity, FactureDto dto) {
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
        
        // Propriétés spécifiques à Facture
        entity.setStatutFacture(dto.getStatutFacture());
        
        // Relations
        if (dto.getClient() != null) {
            entity.setClient(clientMapper.toEntity(dto.getClient()));
        }
        if (dto.getCommande() != null) {
            entity.setCommande(commandeMapper.toEntity(dto.getCommande()));
        }
    }
    
    /**
     * Convertit une liste d'entités en liste de DTOs
     */
    public List<FactureDto> toDtoList(List<Facture> entities) {
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
    public List<Facture> toEntityList(List<FactureDto> dtos) {
        if (dtos == null) {
            return null;
        }
        
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
