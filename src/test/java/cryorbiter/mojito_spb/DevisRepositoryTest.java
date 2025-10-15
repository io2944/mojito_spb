package cryorbiter.mojito_spb;

import cryorbiter.mojito_spb.enumerations.StatutClient;
import cryorbiter.mojito_spb.enumerations.StatutDevis;
import cryorbiter.mojito_spb.enumerations.TypeClient;
import cryorbiter.mojito_spb.enumerations.TypeDocument;
import cryorbiter.mojito_spb.model.Client;
import cryorbiter.mojito_spb.model.Devis;
import cryorbiter.mojito_spb.repository.ClientRepository;
import cryorbiter.mojito_spb.repository.DevisRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class DevisRepositoryTest {
    @Autowired
    private DevisRepository devisRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void testFindAllByClient() {
        Client client = new Client();
        client.setNom("Durand");
        client.setPrenom("Alice");
        client.setEmail("alice@exemple.com");
        client.setTypeClient(TypeClient.PROFESSIONNEL);
        client.setStatutClient(StatutClient.CLIENT);
        client.setActive(true);
        client.setCommentaire("TestCommentaire");
        client.setAdresse("TestAdresse");
        client.setTelephoneFixe("0123456789");
        client.setTelephoneMobile("0987654321");
        client.setEntreprise("TestEntreprise");
        clientRepository.save(client);

        Devis devis = new Devis();
        devis.setNomDocument("Devis Test");
        devis.setDate(java.sql.Date.valueOf(LocalDate.now()));
        devis.setClient(client);
        devis.setCommentaire("TestCommentaire");
        devis.setPoids(10f);
        devis.setPrixKilo(5.0);
        devis.setTva(20.0f);
        devis.setStatut(StatutDevis.ANNULEE);
        devis.setLibelle("TestLibelle");
        devis.setType(TypeDocument.ALIMENTAIRES);
        devisRepository.save(devis);

        List<Devis> result = devisRepository.findAllByClient(client);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNomDocument()).isEqualTo("Devis Test");
    }
}
