package cryorbiter.mojito_spb.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import cryorbiter.mojito_spb.mapper.RoleMapper;
import cryorbiter.mojito_spb.model.Role;
import cryorbiter.mojito_spb.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletException;
import jakarta.transaction.Transactional;

import cryorbiter.mojito_spb.dto.RoleDto;
import cryorbiter.mojito_spb.form.RoleForm;

@Transactional
@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private RoleForm roleForm;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleService(RoleRepository roleRepository, RoleForm roleForm,  RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleForm = roleForm;
        this.roleMapper = roleMapper;
    }


    public RoleDto createRole(RoleForm role) {
        if (role == null) {
            throw new IllegalArgumentException("role doit être renseigné");
        }

        // Vérifier si le rôle existe déjà par nom
        if (role.getName() != null && roleRepository.findByName(role.getName()).isPresent()) {
            throw new IllegalArgumentException("Role avec le nom " + role.getName() + " existe déjà.");
        }

        RoleDto roleDto = new RoleDto();
        roleDto.setName(role.getName());

        Role entity = RoleMapper.toEntity(roleDto);
        Role savedEntity = roleRepository.save(entity);
        return RoleMapper.toDto(savedEntity);
    }

    public RoleDto updateRole(Long id, RoleForm roleForm)
            throws ServletException, IOException {
        if (roleForm == null) {
            throw new IllegalArgumentException("role doit être renseigné");
        }

        if (roleForm.getId() == null) {
            throw new IllegalArgumentException("L'identifiant du rôle doit être renseigné pour la modification");
        }

        RoleDto role = this.getRoleById(id);
        role.setName(roleForm.getName());

        Optional<Role> existingEntityOpt = roleRepository.findById(role.getId());
        if (!existingEntityOpt.isPresent()) {
            throw new IllegalArgumentException("Aucun rôle trouvé avec l'identifiant " + role.getId());
        }

        Role existingEntity = existingEntityOpt.get();
        roleMapper.updateEntity(existingEntity, role);
        Role savedEntity = roleRepository.save(existingEntity);
        return RoleMapper.toDto(savedEntity);
    }

    public RoleDto getRoleById(Long id) {
        if (id == null) {
            return null;
        }

        Optional<Role> roleOpt = roleRepository.findById(id);
        return roleOpt.map(RoleMapper::toDto).orElse(null);
    }

    public void deleteRole(Long id) {
        RoleDto role = this.getRoleById(id);

        if (role == null) {
            throw new IllegalArgumentException("role doit être renseigné");
        }

        if (role.getId() == null) {
            throw new IllegalArgumentException("L'identifiant du rôle doit être renseigné pour la suppression");
        }

        roleRepository.deleteById(role.getId());
    }

    public List<RoleDto> getAllRoles() {
        return roleMapper.toDtoList(roleRepository.findAll());
    }

    // Méthodes utilitaires supplémentaires
    public RoleDto findByName(String name) {
        Optional<Role> roleOpt = roleRepository.findByName(name);
        return roleOpt.map(RoleMapper::toDto).orElse(null);
    }

    public List<RoleDto> getRolesByIds(Long[] roles) {
        if (roles == null || roles.length == 0) {
            return List.of();
        }
        return roleMapper.toDtoList(roleRepository.findAllById(
                Arrays.stream(roles).toList()));
    }
}
