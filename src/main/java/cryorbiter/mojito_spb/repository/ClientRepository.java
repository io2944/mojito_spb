package cryorbiter.mojito_spb.repository;

import cryorbiter.mojito_spb.dto.DevisDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cryorbiter.mojito_spb.model.Client;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
