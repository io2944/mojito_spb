package cryorbiter.mojito_spb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cryorbiter.mojito_spb.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    /**
     * Trouve un rôle par son nom
     */
    Optional<Role> findByName(String name);
    

}
