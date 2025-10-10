package cryorbiter.mojito_spb.form;

import jakarta.validation.constraints.*;
import cryorbiter.mojito_spb.enumerations.StatutClient;
import cryorbiter.mojito_spb.enumerations.TypeClient;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientForm {

	private Long id;

	@NotNull(message = "Le nom est obligatoire")
	@Size(min = 2, max = 100, message = "Le nom doit faire entre 2 et 100 caractères")
	private String nom;

	@NotNull(message = "Le prénom est obligatoire")
	@Size(min = 2, max = 100, message = "Le prénom doit faire entre 2 et 100 caractères")
	private String prenom;

	@Size(max = 200, message = "L'entreprise ne peut dépasser 200 caractères")
	private String entreprise;

	@Size(max = 500, message = "L'adresse ne peut dépasser 500 caractères")
	private String adresse;

	@Pattern(regexp = "[0-9- ()+]*", message = "Merci de renseigner un vrai numéro de téléphone")
	private String telephoneFixe;

	@Pattern(regexp = "[0-9- ()+]*", message = "Merci de renseigner un vrai numéro de téléphone")
	private String telephoneMobile;

	@Size(max = 255, message = "Le commentaire ne peut dépasser 255 caractères")
	private String commentaire;

	@Email(message = "Email invalide")
	@Size(max = 100, message = "L'email ne peut dépasser 100 caractères")
	private String email;

	@NotNull(message = "L'état actif est obligatoire")
	private Boolean active = false;

	@NotNull(message = "Le type de client est obligatoire")
	private TypeClient typeClient;

	@NotNull(message = "Le statut du client est obligatoire")
	private StatutClient statutClient;

}
