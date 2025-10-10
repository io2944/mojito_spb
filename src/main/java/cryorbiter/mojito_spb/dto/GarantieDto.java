package cryorbiter.mojito_spb.dto;

import java.io.Serializable;
import java.util.Date;

public class GarantieDto implements Serializable {
    private long id;
    private Date date;
    private String commentaire;

    public static final long serialVersionUID = 14L;

    public GarantieDto() {}

    public GarantieDto(long id, Date date, String commentaire) {
        this.id = id;
        this.date = date;
        this.commentaire = commentaire;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}
