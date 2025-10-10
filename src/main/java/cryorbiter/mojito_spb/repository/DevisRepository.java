package cryorbiter.mojito_spb.repository;

import cryorbiter.mojito_spb.dto.ClientDto;
import cryorbiter.mojito_spb.dto.DevisDto;
import cryorbiter.mojito_spb.model.Client;
import cryorbiter.mojito_spb.model.Devis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface DevisRepository extends JpaRepository<Devis, Long> {

    long countByDate(Date date);

    List<Devis> findAllByClient(Client client);
}
