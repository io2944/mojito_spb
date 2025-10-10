package cryorbiter.mojito_spb.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cryorbiter.mojito_spb.enumerations.StatutClient;
import cryorbiter.mojito_spb.enumerations.TypeClient;
import lombok.Setter;

@Setter
public class ClientDto implements Serializable {

    private static final long serialVersionUID = 3L;

    //TODO a supprimer lorsque l'ID sera géré par la base de données
    private Long id;
    private String nom;
    private String prenom;
    private String entreprise;
    private String adresse;
    private String telephoneFixe;
    private String telephoneMobile;
    private String commentaire;
    private String email;
    private boolean active;
    private TypeClient typeClient;
    private StatutClient statutClient;
    private List<DevisDto> devisList;
    private List<CommandeDto> commandesList;
    private List<FactureDto> facturesList;

    public ClientDto() {
    }

    public ClientDto(String nom, String prenom, String entreprise, String telephoneFixe, String telephoneMobile,
            String commentaire, String email, boolean isActive, TypeClient typeClient, StatutClient statutClient) {

        this.nom = nom;
        this.prenom = prenom;
        this.entreprise = entreprise;
        this.telephoneFixe = telephoneFixe;
        this.telephoneMobile = telephoneMobile;
        this.commentaire = commentaire;
        this.email = email;
        this.active = isActive;
        this.typeClient = typeClient;
        this.statutClient = statutClient;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEntreprise() {
        return entreprise;
    }

    public String getTelephoneFixe() {
        return telephoneFixe;
    }

    public String getTelephoneMobile() {
        return telephoneMobile;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public String getEmail() {
        return email;
    }

    public boolean getActive() {
        return active;
    }

    public TypeClient getTypeClient() {
        return typeClient;
    }

    public StatutClient getStatutClient() {
        return statutClient;
    }

    public List<DevisDto> getDevisList() {
        return devisList;
    }

    public List<CommandeDto> getCommandesList() {
        return commandesList;
    }

    public List<FactureDto> getFacturesList() {
        return facturesList;
    }

	public String getAdresse() {
		return adresse;
	}

}
