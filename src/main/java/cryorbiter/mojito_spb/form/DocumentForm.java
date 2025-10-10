package cryorbiter.mojito_spb.form;

import jakarta.validation.constraints.*;
import cryorbiter.mojito_spb.enumerations.TypeDocument;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

@Setter
@Getter
@Component
public class DocumentForm {

    @NotNull(message = "Le client est obligatoire")
    private Long clientId; // on ne met pas l'objet complet, juste son id

    @NotNull(message = "La date est obligatoire")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Positive(message = "Le poids doit être supérieur à 0")
    private Float poids;

    @DecimalMin(value = "0.0", inclusive = true, message = "La TVA doit être positive")
    private Float tva;

    @NotNull(message = "Le type de document est obligatoire")
    private TypeDocument type;

    @NotNull(message = "Le libellé est obligatoire")
    @Size(min = 2, max = 255, message = "Le libellé doit faire entre 2 et 255 caractères")
    private String libelle;

    @Size(max = 255, message = "Le commentaire ne peut pas dépasser 255 caractères")
    private String commentaire;

    @DecimalMin(value = "0.0", inclusive = true, message = "Le prix au kilo doit être positif")
    private Double prixKilo;

    private String nomDocument;

    // Getters et setters

}
