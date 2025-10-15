package cryorbiter.mojito_spb;

import cryorbiter.mojito_spb.enumerations.StatutClient;
import cryorbiter.mojito_spb.enumerations.TypeClient;
import cryorbiter.mojito_spb.model.Client;
import cryorbiter.mojito_spb.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Utilise application-test.properties (H2 en mémoire)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    void setup() {
        clientRepository.deleteAll();

        // Données de test de base
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
    }

    // Liste des clients
    @Test
    void checkListClientThenSuccess() throws Exception {
        mockMvc.perform(get("/listClient").with(csrf()).with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("clients"))
                .andExpect(view().name("Client/listClient"));
    }

    //  Création d’un client valide → redirection
    @Test
    void checkCreateClientWhenValidThenRedirect() throws Exception {
        mockMvc.perform(post("/createClient")
                        .param("nom", "Dupont")
                        .param("prenom", "Jean")
                        .param("entreprise", "ABB")
                        .param("adresse", "123 Rue Bleue")
                        .param("telephoneFixe", "0123456789")
                        .param("telephoneMobile", "0987654321")
                        .param("email", "jean.dupont@abc.com")
                        .param("active", "true")
                        .param("commentaire", "TestComment")
                        .param("typeClient", "PROFESSIONNEL")
                        .param("statutClient", "CLIENT")
                        .with(csrf()).with(user("admin").roles("ADMIN")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/listClient"));

        assertThat(clientRepository.findAll()).hasSize(2); // un client existant + celui créé
    }

    // Création d’un client invalide → erreur
    @Test
    void checkCreateClientWhenMissingFieldsThenError() throws Exception {
        mockMvc.perform(post("/createClient")
                        .param("nom", "") // manquant
                        .param("prenom", "Jean")
                        .with(csrf()).with(user("admin").roles("ADMIN")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/createClient"));

        assertThat(clientRepository.findAll()).hasSize(1); // aucun nouveau client ajouté
    }

    @Test
    void checkViewClientThenSuccess() throws Exception {
        Client client = clientRepository.findAll().get(0);

        mockMvc.perform(get("/viewClient/" + client.getId())
                        .with(csrf()).with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("client"))
                .andExpect(view().name("Client/viewClient"))
                .andExpect(model().attribute("client", org.hamcrest.Matchers.hasProperty("nom", is("Durand"))));
    }

    //  Mise à jour d’un client
    @Test
    void checkUpdateClientThenSuccess() throws Exception {
        Client client = clientRepository.findAll().get(0);

        mockMvc.perform(post("/updateClient/" + client.getId())
                        .param("nom", "Durand")
                        .param("prenom", "Alicia")
                        .param("entreprise", "UpdatedCorp")
                        .param("email", "alice.updated@exemple.com")
                        .param("typeClient", "PROFESSIONNEL")
                        .param("statutClient", "CLIENT")
                        .param("active", "true")
                        .with(csrf()).with(user("admin").roles("ADMIN")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/listClient"));

        Client updated = clientRepository.findById(client.getId()).orElseThrow();
        assertThat(updated.getPrenom()).isEqualTo("Alicia");
        assertThat(updated.getEmail()).isEqualTo("alice.updated@exemple.com");
    }

    //  Suppression d’un client
    @Test
    void checkDeleteClientThenRedirect() throws Exception {
        Client client = clientRepository.findAll().get(0);

        mockMvc.perform(get("/deleteClient").param("id", client.getId().toString())
                        .with(csrf()).with(user("admin").roles("ADMIN")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/listClient"));

        assertThat(clientRepository.existsById(client.getId())).isFalse();
    }
}
