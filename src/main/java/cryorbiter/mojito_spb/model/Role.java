package cryorbiter.mojito_spb.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role implements Serializable {

    private static final long serialVersionUID = 2L;

    //TODO a supprimer lorsque l'ID sera géré par la base de données
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    protected String name;

    @ManyToMany(mappedBy = "roles")
    protected List<User> users;

    public Role() {
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
