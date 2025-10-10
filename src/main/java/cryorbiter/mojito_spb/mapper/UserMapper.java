package cryorbiter.mojito_spb.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import cryorbiter.mojito_spb.dto.UserDto;
import cryorbiter.mojito_spb.model.User;

@Component
public class UserMapper {

    /**
     * Convertit une entité User en DTO User
     */
    public UserDto toDto(User entity) {
        if (entity == null) {
            return null;
        }
        
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setEmail(entity.getEmail());
        dto.setRoles(entity.getRoles().stream().map(RoleMapper::toDto).toList());
        return dto;
    }
    
    /**
     * Convertit un DTO User en entité User
     */
    public User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }

        User entity = new User();
        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setEmail(dto.getEmail());
        entity.setRoles(dto.getRoles().stream().map(RoleMapper::toEntity).toList());
        return entity;
    }
    
    /**
     * Met à jour une entité existante avec les données du DTO
     */
    public void updateEntity(User entity, UserDto dto) {
        if (entity == null || dto == null) {
            return;
        }
        
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setEmail(dto.getEmail());
    }
    
    /**
     * Convertit une liste d'entités en liste de DTOs
     */
    public List<UserDto> toDtoList(List<User> entities) {
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
    public List<User> toEntityList(List<UserDto> dtos) {
        if (dtos == null) {
            return null;
        }
        
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
