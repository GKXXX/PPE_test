package com.src.app.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Equipe.
 */
@Entity
@Table(name = "equipe")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Equipe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "jeu")
    private String jeu;

    @Column(name = "taille")
    private Long taille;

    @NotNull
    @Column(name = "telephone", nullable = false)
    private Long telephone;

    @OneToMany(mappedBy = "equipe")
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

    public Equipe nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getJeu() {
        return jeu;
    }

    public Equipe jeu(String jeu) {
        this.jeu = jeu;
        return this;
    }

    public void setJeu(String jeu) {
        this.jeu = jeu;
    }

    public Long getTaille() {
        return taille;
    }

    public Equipe taille(Long taille) {
        this.taille = taille;
        return this;
    }

    public void setTaille(Long taille) {
        this.taille = taille;
    }

    public Long getTelephone() {
        return telephone;
    }

    public Equipe telephone(Long telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public Set<Reservation> getTelephones() {
        return telephones;
    }

    public Equipe telephones(Set<Reservation> reservations) {
        this.telephones = reservations;
        return this;
    }

    public Equipe addTelephone(Reservation reservation) {
        this.telephones.add(reservation);
        reservation.setEquipe(this);
        return this;
    }

    public Equipe removeTelephone(Reservation reservation) {
        this.telephones.remove(reservation);
        reservation.setEquipe(null);
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
        if (!(o instanceof Equipe)) {
            return false;
        }
        return id != null && id.equals(((Equipe) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Equipe{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", jeu='" + getJeu() + "'" +
            ", taille=" + getTaille() +
            ", telephone=" + getTelephone() +
            "}";
    }
}
