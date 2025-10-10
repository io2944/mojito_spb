package cryorbiter.mojito_spb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import cryorbiter.mojito_spb.enumerations.StatutClient;
import cryorbiter.mojito_spb.enumerations.TypeClient;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "clients")
public class Client implements Serializable {

    private static final long serialVersionUID = 3L;

    //TODO a supprimer lorsque l'ID sera géré par la base de données
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nom", nullable = false, length = 100)
    private String nom;
    
    @Column(name = "prenom", length = 100)
    private String prenom;
    
    @Column(name = "entreprise", length = 200)
    private String entreprise;
    
    @Column(name = "adresse", length = 500)
    private String adresse;
    
    @Column(name = "telephone_fixe", length = 20)
    private String telephoneFixe;
    
    @Column(name = "telephone_mobile", length = 20)
    private String telephoneMobile;
    
    @Column(name = "commentaire", columnDefinition = "TEXT")
    private String commentaire;
    
    @Column(name = "email", length = 100)
    private String email;
    
    @Column(name = "is_active")
    private boolean active;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type_client", nullable = false, length = 20)
    private TypeClient typeClient;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "statut_client", nullable = false, length = 20)
    private StatutClient statutClient;
    
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Devis> devisList;
    
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Commande> commandesList;
    
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Facture> facturesList;

    public Client() {
    }

    public Client(String nom, String prenom, String entreprise, String telephoneFixe, String telephoneMobile,
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

    public void addDevis(Devis devis) {
        if (this.devisList == null) {
            this.devisList = new ArrayList<>();
        }
        this.devisList.add(devis);
    }

    public void addCommande(Commande commande) {
        if (this.commandesList == null) {
            this.commandesList = new ArrayList<>();
        }
        this.commandesList.add(commande);
    }

    public void addFacture(Facture facture) {
        if (this.facturesList == null) {
            this.facturesList = new ArrayList<>();
        }
        this.facturesList.add(facture);
    }

}
