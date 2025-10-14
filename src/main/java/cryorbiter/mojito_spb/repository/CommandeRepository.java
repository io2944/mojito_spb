package cryorbiter.mojito_spb.repository;

import cryorbiter.mojito_spb.dto.CommandeDto;
import cryorbiter.mojito_spb.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cryorbiter.mojito_spb.model.Commande;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
    long countByDate(Date date);
    List<Commande> findAllByClient(Client entity);

    List<Commande> findByNomDocumentContainingIgnoreCase(String nom);
}
