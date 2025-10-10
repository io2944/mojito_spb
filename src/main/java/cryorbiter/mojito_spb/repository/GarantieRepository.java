package cryorbiter.mojito_spb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cryorbiter.mojito_spb.model.Garantie;

@Repository
public interface GarantieRepository extends JpaRepository<Garantie, Long> {
    

}
