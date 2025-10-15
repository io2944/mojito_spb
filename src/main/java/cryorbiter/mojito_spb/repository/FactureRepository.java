package cryorbiter.mojito_spb.repository;

import cryorbiter.mojito_spb.dto.FactureDto;
import cryorbiter.mojito_spb.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cryorbiter.mojito_spb.model.Facture;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {
    long countByDate(Date date);
    List<Facture> findAllByClient(Client entity);

    List<Facture> findByLibelleContainingIgnoreCase(String libelle);

    @Query(value = """
        SELECT DATE_TRUNC('month', d.date_creation) AS mois,
                SUM((d.poids*d.prix_kilo)*(1+d.tva/100)) AS total_mensuel
        FROM factures f
        JOIN documents d ON f.id = d.id
        GROUP BY DATE_TRUNC('month', d.date_creation)
        ORDER BY mois
            """, nativeQuery = true)
    List<Object[]> getTotalParMois();

    @Query(value =""" 
        SELECT c.nom AS client,
            SUM((d.poids*d.prix_kilo)*(1+d.tva/100)) AS total
        FROM clients c
        JOIN documents d ON c.id = d.client_id
        JOIN factures f ON f.id = d.id
        GROUP BY c.nom
        ORDER BY total DESC
        LIMIT 5
""", nativeQuery = true)
    List<Object[]> getTopClientsParChiffreAffaire();

    List<Facture> findByClientEmail(String mail);
}
