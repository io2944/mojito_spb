package cryorbiter.mojito_spb.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "garanties")
public class Garantie implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;
    
    @Column(name = "commentaire", columnDefinition = "TEXT")
    private String commentaire;

    @Column(name = "libelle", length = 200)
    private String libelle;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    public static final long serialVersionUID = 14L;

    public Garantie() {}

    public Garantie(long id, Date date, String commentaire) {
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
