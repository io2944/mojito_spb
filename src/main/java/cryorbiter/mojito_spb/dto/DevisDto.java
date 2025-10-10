package cryorbiter.mojito_spb.dto;

import java.util.Date;

import cryorbiter.mojito_spb.enumerations.StatutDevis;
import cryorbiter.mojito_spb.enumerations.TypeDocument;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DevisDto extends DocumentDto {

	private StatutDevis statut;

	public static final long serialVersionUID = 11L;

	public DevisDto() {
	}
	public DevisDto(Long id, Date date, float poids, float tva, TypeDocument type, String libelle, Double prixKilo, String commentaire, String nomDocument, ClientDto client, StatutDevis statut) {
		super(id, date, poids, tva, type, libelle, prixKilo, commentaire, nomDocument);
		this.client = client;
		this.statut = statut;
	}

}
