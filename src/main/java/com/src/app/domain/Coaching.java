package com.src.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Coaching.
 */
@Entity
@Table(name = "coaching")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Coaching implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "jeu")
    private String jeu;

    @Column(name = "prix")
    private Long prix;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("telephones")
    private Coach coach;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public Coaching type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJeu() {
        return jeu;
    }

    public Coaching jeu(String jeu) {
        this.jeu = jeu;
        return this;
    }

    public void setJeu(String jeu) {
        this.jeu = jeu;
    }

    public Long getPrix() {
        return prix;
    }

    public Coaching prix(Long prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Long prix) {
        this.prix = prix;
    }

    public Coach getCoach() {
        return coach;
    }

    public Coaching coach(Coach coach) {
        this.coach = coach;
        return this;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Coaching)) {
            return false;
        }
        return id != null && id.equals(((Coaching) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Coaching{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", jeu='" + getJeu() + "'" +
            ", prix=" + getPrix() +
            "}";
    }
}
