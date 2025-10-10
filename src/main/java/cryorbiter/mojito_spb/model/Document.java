package cryorbiter.mojito_spb.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.*;
import cryorbiter.mojito_spb.enumerations.TypeDocument;

@Entity
@Table(name = "documents")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Document implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "date_creation")
    protected Date date;
    
    @Column(name = "poids")
    protected float poids;
    
    @Column(name = "tva")
    protected float tva = 20;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type_document")
    protected TypeDocument type;
    
    @Column(name = "libelle")
    protected String libelle; //dans le formulaire (ex : Bières artisanales et Kimchi)

    @Column(name = "prix_kilo")
    protected Double prixKilo = (double) 500;
    
    @Column(name = "commentaire", columnDefinition = "TEXT")
    protected String commentaire;
    
    @Column(name = "nom_document")
    protected String nomDocument; //automatisé (mélange DEV + dat)
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    protected Client client;

	public static final long serialVersionUID = 12L;

	public Document() {
	}

	public Document(long id, Date date, float poids, float tva, TypeDocument type, String libelle, Double prixKilo) {
		this.id = id;
		this.date = date;
		this.poids = poids;
		this.tva = tva;
		this.type = type;
		this.libelle = libelle;
		this.prixKilo = prixKilo;
	}

    public Document(long id, Date date, float poids, float tva, TypeDocument type, String libelle, Double prixKilo, String commentaire, String nomDocument) {
        this.id = id;
        this.date = date;
        this.poids = poids;
        this.tva = tva;
        this.type = type;
        this.libelle = libelle;
        this.prixKilo = prixKilo;
        this.commentaire = commentaire;
        this.nomDocument = nomDocument;
    }

    /* Retourne le montant HT sinon null */
    public Double getMontantHT(){
        return (this.prixKilo != null && this.poids > 0) ? this.prixKilo * this.poids : null;
    }

	/* Retourne le montant TTC sinon null */
	public Double getMontantTTC() {
		Double montantHT = getMontantHT();
		return (montantHT != null) ? montantHT * (1 + this.tva / 100) : null;
	}

	// Retourne le montant total de la TVA sinon null
	public Double getMontantTVA() {
		Double montantHT = getMontantHT();
		Double montantTTC = getMontantTTC();
		Double montantTVA = montantTTC - montantHT;
		return (montantHT != null && montantTTC != null) ? montantTVA : null;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getPoids() {
		return poids;
	}

	public void setPoids(float poids) {
		this.poids = poids;
	}

	public float getTva() {
		return tva;
	}

	public void setTva(float tva) {
		this.tva = tva;
	}

	public TypeDocument getType() {
		return this.type;
	}

	public void setType(TypeDocument type) {
		this.type = type;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Double getPrixKilo() {
		return prixKilo;
	}

	public void setPrixKilo(Double prixKilo) {
		this.prixKilo = prixKilo;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public String getNomDocument() {
		return nomDocument;
	}

	public void setNomDocument(String nomDocument) {
		this.nomDocument = nomDocument;

	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true; // même objet en mémoire
		if (!(o instanceof Document)) return false; // pas la même classe
		Document other = (Document) o;

		// Si les deux ont un id non null, on compare les ids
		if (id != null && other.id != null) {
			return Objects.equals(id, other.id);
		}

		// Sinon (objets pas encore persistés), on compare sur un champ métier stable
		return Objects.equals(nomDocument, other.nomDocument)
				&& Objects.equals(date, other.date);
	}

	@Override
	public int hashCode() {
		// Si id existe, il domine
		return id != null ? id.hashCode() : Objects.hash(nomDocument, date);
	}

}
