package cryorbiter.mojito_spb.form;

import jakarta.validation.constraints.NotNull;
import cryorbiter.mojito_spb.dto.ClientDto;
import cryorbiter.mojito_spb.dto.DevisDto;
import cryorbiter.mojito_spb.enumerations.StatutCommande;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class CommandeForm extends DocumentForm {

    private Long id; // <-- utilisé uniquement quand on modifie une commande existante

    private boolean orbite;

    @NotNull(message = "Le statut est obligatoire")
    private StatutCommande statutCommande;

    private DevisDto devis;
    private ClientDto client;

    private Long devisId;

}
