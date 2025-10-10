package cryorbiter.mojito_spb.repository;

import cryorbiter.mojito_spb.dto.FactureDto;
import cryorbiter.mojito_spb.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cryorbiter.mojito_spb.model.Facture;

import java.util.Date;
import java.util.List;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {
    long countByDate(Date date);
    List<Facture> findAllByClient(Client entity);
}
