package cryorbiter.mojito_spb;

import cryorbiter.mojito_spb.dto.ClientDto;
import cryorbiter.mojito_spb.dto.DevisDto;
import cryorbiter.mojito_spb.enumerations.StatutClient;
import cryorbiter.mojito_spb.enumerations.StatutDevis;
import cryorbiter.mojito_spb.enumerations.TypeClient;
import cryorbiter.mojito_spb.enumerations.TypeDocument;
import cryorbiter.mojito_spb.service.DevisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class DevisControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DevisService devisService;


    ClientDto client = new ClientDto("Dupont", "Jean", "Entreprise ABC", "123 Rue de la Paix, Paris",
                "0123456789", "0678901234", "jean.dupont@abc.com", "Client VIP", true,
                TypeClient.PROFESSIONNEL, StatutClient.CLIENT);


    @Test
    void checkListeDevisThenSuccess() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        String dateStr = "24-01-2025";
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = formatter.parse(dateStr);
        List<DevisDto> listDevis = List.of(
                new DevisDto(1L, date, 1.2f, 20.00f, TypeDocument.ALIMENTAIRES,
                        "Documents administratifs", 300.00, "Transport standard", "DEV_20240125_1500", client, StatutDevis.ARCHIVEE));

        Page<DevisDto> devisPage = new PageImpl<>(listDevis, pageable, listDevis.size());
        when(devisService.getAllDevis(pageable)).thenReturn(devisPage);

        Page<DevisDto> result = devisService.getAllDevis(pageable);
        assertEquals(1, result.getTotalElements());
        assertEquals("Documents administratifs", result.getContent().get(0).getLibelle());
    }

    @Test
    void checkCreateDevisWhenValidThenRedirect() throws Exception {
        mockMvc.perform(post("/createDevis")
                        .param("clientId", "1")
                        .param("nomDocument", "DevisTest")
                        .param("libelle", "LibelleTest")
                        .param("date", "2025-09-17")
                        .param("poids", "10")
                        .param("type", "ALIMENTAIRES")
                        .param("prixKilo", "5.0")
                        .param("tva", "20.0")
                        .param("commentaire", "TestComment")
                        .param("statut", "ANNULEE")
                        .with(csrf()).with(user("admin").roles("ADMIN")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/listeDevis"));
    }

    @Test
    void checkUpdateDevisThenSuccess() throws Exception {
        mockMvc.perform(post("/updateDevis/1")
                        .param("clientId", "1")
                        .param("nomDocument", "DevisTest")
                        .param("libelle", "LibelleTest")
                        .param("date", "2025-09-17")
                        .param("poids", "10")
                        .param("type", "ALIMENTAIRES")
                        .param("prixKilo", "5.0")
                        .param("tva", "20.0")
                        .param("commentaire", "TestComment")
                        .param("statut", "ANNULEE")
                        .with(csrf()).with(user("admin").roles("ADMIN")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/listeDevis"));
    }
}
