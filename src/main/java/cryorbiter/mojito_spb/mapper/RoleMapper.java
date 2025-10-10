package cryorbiter.mojito_spb.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import cryorbiter.mojito_spb.dto.RoleDto;
import cryorbiter.mojito_spb.model.Role;

@Component
public class RoleMapper {

    /**
     * Convertit une entité Role en DTO Role
     */
    public static RoleDto toDto(Role entity) {
        if (entity == null) {
            return null;
        }
        
        RoleDto dto = new RoleDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        
        return dto;
    }
    
    /**
     * Convertit un DTO Role en entité Role
     */
    public static Role toEntity(RoleDto dto) {
        if (dto == null) {
            return null;
        }

        Role entity = new Role();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        
        return entity;
    }
    
    /**
     * Met à jour une entité existante avec les données du DTO
     */
    public void updateEntity(Role entity, RoleDto dto) {
        if (entity == null || dto == null) {
            return;
        }
        
        entity.setName(dto.getName());
    }
    
    /**
     * Convertit une liste d'entités en liste de DTOs
     */
    public List<RoleDto> toDtoList(List<Role> entities) {
        if (entities == null) {
            return null;
        }
        
        return entities.stream()
                .map(RoleMapper::toDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Convertit une liste de DTOs en liste d'entités
     */
    public List<Role> toEntityList(List<RoleDto> dtos) {
        if (dtos == null) {
            return null;
        }
        
        return dtos.stream()
                .map(RoleMapper::toEntity)
                .collect(Collectors.toList());
    }
}
