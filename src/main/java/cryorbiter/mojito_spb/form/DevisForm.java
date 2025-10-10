package cryorbiter.mojito_spb.form;

import jakarta.validation.constraints.NotNull;
import cryorbiter.mojito_spb.dto.ClientDto;
import cryorbiter.mojito_spb.enumerations.StatutDevis;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class DevisForm extends DocumentForm {

    private Long id; // <-- utilisé uniquement quand on modifie un devis existant

    @NotNull(message = "Le statut du devis est obligatoire")
    private StatutDevis statut; // correspond à StatutDevis côté DTO

    private ClientDto client;

}
