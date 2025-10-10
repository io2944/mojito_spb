package cryorbiter.mojito_spb.form;

import jakarta.validation.constraints.NotNull;
import cryorbiter.mojito_spb.dto.ClientDto;
import cryorbiter.mojito_spb.dto.CommandeDto;
import cryorbiter.mojito_spb.dto.FactureDto;
import cryorbiter.mojito_spb.enumerations.StatutFacture;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Setter
@Getter
@Component
public class FactureForm extends DocumentForm {

    private Long id; // <-- utilisé uniquement quand on modifie une facture existante

    @NotNull(message = "Le statut est obligatoire")
    private StatutFacture statutFacture;

    private CommandeDto commande;

    private ClientDto client;

    public Long getCommandeId() {
        return  commande.getId();
    }

}