package com.src.app.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Coach.
 */
@Entity
@Table(name = "coach")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Coach implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "jeu")
    private String jeu;

    @Column(name = "prix_jour")
    private Long prixJour;

    @NotNull
    @Column(name = "telephone", nullable = false)
    private Long telephone;

    @Column(name = "dispo")
    private Boolean dispo;

    @OneToMany(mappedBy = "coach")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Coaching> telephones = new HashSet<>();

    @OneToMany(mappedBy = "coach")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Reservation> telephones = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Coach nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getJeu() {
        return jeu;
    }

    public Coach jeu(String jeu) {
        this.jeu = jeu;
        return this;
    }

    public void setJeu(String jeu) {
        this.jeu = jeu;
    }

    public Long getPrixJour() {
        return prixJour;
    }

    public Coach prixJour(Long prixJour) {
        this.prixJour = prixJour;
        return this;
    }

    public void setPrixJour(Long prixJour) {
        this.prixJour = prixJour;
    }

    public Long getTelephone() {
        return telephone;
    }

    public Coach telephone(Long telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public Boolean isDispo() {
        return dispo;
    }

    public Coach dispo(Boolean dispo) {
        this.dispo = dispo;
        return this;
    }

    public void setDispo(Boolean dispo) {
        this.dispo = dispo;
    }

    public Set<Coaching> getTelephones() {
        return telephones;
    }

    public Coach telephones(Set<Coaching> coachings) {
        this.telephones = coachings;
        return this;
    }

    public Coach addTelephone(Coaching coaching) {
        this.telephones.add(coaching);
        coaching.setCoach(this);
        return this;
    }

    public Coach removeTelephone(Coaching coaching) {
        this.telephones.remove(coaching);
        coaching.setCoach(null);
        return this;
    }

    public void setTelephones(Set<Coaching> coachings) {
        this.telephones = coachings;
    }

    public Set<Reservation> getTelephones() {
        return telephones;
    }

    public Coach telephones(Set<Reservation> reservations) {
        this.telephones = reservations;
        return this;
    }

    public Coach addTelephone(Reservation reservation) {
        this.telephones.add(reservation);
        reservation.setCoach(this);
        return this;
    }

    public Coach removeTelephone(Reservation reservation) {
        this.telephones.remove(reservation);
        reservation.setCoach(null);
        return this;
    }

    public void setTelephones(Set<Reservation> reservations) {
        this.telephones = reservations;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Coach)) {
            return false;
        }
        return id != null && id.equals(((Coach) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Coach{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", jeu='" + getJeu() + "'" +
            ", prixJour=" + getPrixJour() +
            ", telephone=" + getTelephone() +
            ", dispo='" + isDispo() + "'" +
            "}";
    }
}
