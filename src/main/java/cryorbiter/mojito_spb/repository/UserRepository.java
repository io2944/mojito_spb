package cryorbiter.mojito_spb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cryorbiter.mojito_spb.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Trouve un utilisateur par son nom d'utilisateur
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Trouve un utilisateur par son email
     */
    Optional<User> findByEmail(String email);
    

}
