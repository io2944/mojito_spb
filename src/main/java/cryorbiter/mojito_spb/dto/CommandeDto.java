package cryorbiter.mojito_spb.dto;

import java.util.Date;

import cryorbiter.mojito_spb.enumerations.StatutCommande;
import cryorbiter.mojito_spb.enumerations.TypeDocument;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommandeDto extends DocumentDto {

    private boolean orbite;
    protected StatutCommande statutCommande;
    private DevisDto devis;

	public static final long serialVersionUID = 10L;

    public CommandeDto() { }

    public CommandeDto(long id, Date date, float poids, float tva, TypeDocument type, String libelle, Double prixKilo, String commentaire, String nomDocument,
                    boolean orbite, StatutCommande statutCommande) {
        this(id, date, poids, tva, type, libelle, prixKilo, commentaire, nomDocument, null, orbite, statutCommande, null);
    }

    public CommandeDto(long id, Date date, float poids, float tva, TypeDocument type, String libelle, Double prixKilo,
                    String commentaire, String nomDocument, ClientDto client, boolean orbite, StatutCommande statutCommande, DevisDto devis) {
        super(id, date, poids, tva, type, libelle, prixKilo, commentaire, nomDocument);
        this.client = client;
        this.orbite = orbite;
        this.statutCommande = statutCommande;
        this.devis = devis;
    }

	public CommandeDto(long id, Date date, float poids, float tva, TypeDocument type, String libelle, String commentaire,
			boolean orbite, StatutCommande statutCommande, DevisDto devis, ClientDto client, Double prixKilo) {
		super(id, date, poids, tva, type, libelle, prixKilo);

		this.orbite = orbite;
		this.statutCommande = statutCommande;
		this.devis = devis;
	}


    //    @Override
//    public String getNomDocument() {
//        return "commande" + this.date;
//    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + getId() +
                ", date=" + getDate() +
                ", poids=" + getPoids() +
                ", tva=" + getTva() +
                ", type=" + getType() +
                ", libelle='" + getLibelle() + '\'' +
                ", prixKilo=" + getPrixKilo() +
                ", commentaire='" + getCommentaire() + '\'' +
                ", nomDocument='" + getNomDocument() + '\'' +
                ", client=" + getClient() +
                ", orbite=" + isOrbite() +
                ", statut=" + getStatutCommande() +
                ", devis=" + getDevis() +
                '}';
    }

}
