package cryorbiter.mojito_spb.dto;

import java.util.Date;

import cryorbiter.mojito_spb.enumerations.StatutFacture;
import cryorbiter.mojito_spb.enumerations.TypeDocument;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FactureDto extends DocumentDto {

    public static final long serialVersionUID = 13L;

    private StatutFacture statutFacture;
    private CommandeDto commande;

    public FactureDto() {}

    public FactureDto(Long id, Date date, float poids, float tva, TypeDocument type, String libelle,
                      Double prixKilo, String commentaire, String nomDocument, StatutFacture statutFacture, CommandeDto commande, ClientDto client) {
        super(id, date, poids, tva, type, libelle, prixKilo, commentaire, nomDocument);
        this.statutFacture = statutFacture;
        this.commande = commande;
        this.client = client;
    }
}
