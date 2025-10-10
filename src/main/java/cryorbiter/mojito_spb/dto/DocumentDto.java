package cryorbiter.mojito_spb.dto;

import java.io.Serializable;
import java.util.Date;

import cryorbiter.mojito_spb.enumerations.TypeDocument;

public abstract class DocumentDto implements Serializable {
	protected Long id;
	protected Date date;
	protected float poids;
	protected float tva = 20;
	protected TypeDocument type;
	protected String libelle; //dans le formulaire (ex : Bières artisanales et Kimchi)

	protected Double prixKilo = (double) 500;
	protected String commentaire;
	protected String nomDocument; //automatisé (mélange DEV + dat)
	protected ClientDto client;

	public static final long serialVersionUID = 12L;

	public DocumentDto() {
	}

	public DocumentDto(Long id, Date date, float poids, float tva, TypeDocument type, String libelle, Double prixKilo) {
		this.id = id;
		this.date = date;
		this.poids = poids;
		this.tva = tva;
		this.type = type;
		this.libelle = libelle;
		this.prixKilo = prixKilo;
	}

    public DocumentDto(Long id, Date date, float poids, float tva, TypeDocument type, String libelle, Double prixKilo, String commentaire, String nomDocument) {
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

	public ClientDto getClient() {
		return client;
	}

	public void setClient(ClientDto client) {
		this.client = client;
	}

}
