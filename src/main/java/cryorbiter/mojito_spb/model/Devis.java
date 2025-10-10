package cryorbiter.mojito_spb.model;

import java.util.Date;

import jakarta.persistence.*;
import cryorbiter.mojito_spb.enumerations.StatutDevis;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "devis")
public class Devis extends Document {

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutDevis statut;

	public static final long serialVersionUID = 11L;

	public Devis() {}

	public void nommerDevis() {
        if (this.date != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd_HHmm");
            this.nomDocument = "DEV_" + sdf.format(this.date);
        } else {
            this.nomDocument = "DEV_SANS_DATE";
        }
    }
}
