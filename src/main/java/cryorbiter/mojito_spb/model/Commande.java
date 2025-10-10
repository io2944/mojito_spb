package cryorbiter.mojito_spb.model;

import java.util.Date;

import jakarta.persistence.*;
import cryorbiter.mojito_spb.enumerations.StatutCommande;
import cryorbiter.mojito_spb.enumerations.TypeDocument;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "commandes")
public class Commande extends Document {

    @Column(name = "orbite")
    private boolean orbite;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    protected StatutCommande statutCommande;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "devis_id")
    private Devis devis;

	public static final long serialVersionUID = 10L;

    public Commande() { }

    public Commande(long id, Date date, float poids, float tva, TypeDocument type, String libelle, Double prixKilo, String commentaire, String nomDocument,
                    boolean orbite, StatutCommande statutCommande) {
        this(id, date, poids, tva, type, libelle, prixKilo, commentaire, nomDocument, null, orbite, statutCommande, null);
    }

    public Commande(long id, Date date, float poids, float tva, TypeDocument type, String libelle, Double prixKilo,
                    String commentaire, String nomDocument, Client client, boolean orbite, StatutCommande statutCommande, Devis devis) {
        super(id, date, poids, tva, type, libelle, prixKilo, commentaire, nomDocument);
        this.client = client;
        this.orbite = orbite;
        this.statutCommande = statutCommande;
        this.devis = devis;
    }

	public Commande(long id, Date date, float poids, float tva, TypeDocument type, String libelle, String commentaire,
			boolean orbite, StatutCommande statutCommande, Devis devis, Client client, Double prixKilo) {
		super(id, date, poids, tva, type, libelle, prixKilo);

		this.orbite = orbite;
		this.statutCommande = statutCommande;
		this.devis = devis;
	}


    public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
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
