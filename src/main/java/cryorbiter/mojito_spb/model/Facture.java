package cryorbiter.mojito_spb.model;

import java.util.Date;

import jakarta.persistence.*;
import cryorbiter.mojito_spb.enumerations.StatutFacture;
import cryorbiter.mojito_spb.enumerations.TypeDocument;

@Entity
@Table(name = "factures")
public class Facture extends Document {

    public static final long serialVersionUID = 13L;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut_facture", nullable = false)
    private StatutFacture statutFacture;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_id")
    private Commande commande;

    public Facture() {}

    public Facture(long id, Date date, float poids, float tva, TypeDocument type, String libelle, Double prixKilo,
            StatutFacture statutFacture, Commande commande) {
        super(id, date, poids, tva, type, libelle, prixKilo);
        this.statutFacture = statutFacture;
        this.commande = commande;
    }

    public StatutFacture getStatutFacture() {
        return statutFacture;
    }

    public void setStatutFacture(StatutFacture statutFacture) {
        this.statutFacture = statutFacture;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Facture{" +
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
                '}';

    }
}
