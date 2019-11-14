package com.src.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Locaux.
 */
@Entity
@Table(name = "locaux")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Locaux implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "ville")
    private String ville;

    @NotNull
    @Column(name = "telephone", nullable = false)
    private Long telephone;

    @Column(name = "taille")
    private Long taille;

    @Column(name = "prix_jour")
    private Long prixJour;

    @OneToMany(mappedBy = "locaux")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Reservation> telephones = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("noms")
    private Equipement equipement;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public Locaux adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public Locaux ville(String ville) {
        this.ville = ville;
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Long getTelephone() {
        return telephone;
    }

    public Locaux telephone(Long telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public Long getTaille() {
        return taille;
    }

    public Locaux taille(Long taille) {
        this.taille = taille;
        return this;
    }

    public void setTaille(Long taille) {
        this.taille = taille;
    }

    public Long getPrixJour() {
        return prixJour;
    }

    public Locaux prixJour(Long prixJour) {
        this.prixJour = prixJour;
        return this;
    }

    public void setPrixJour(Long prixJour) {
        this.prixJour = prixJour;
    }

    public Set<Reservation> getTelephones() {
        return telephones;
    }

    public Locaux telephones(Set<Reservation> reservations) {
        this.telephones = reservations;
        return this;
    }

    public Locaux addTelephone(Reservation reservation) {
        this.telephones.add(reservation);
        reservation.setLocaux(this);
        return this;
    }

    public Locaux removeTelephone(Reservation reservation) {
        this.telephones.remove(reservation);
        reservation.setLocaux(null);
        return this;
    }

    public void setTelephones(Set<Reservation> reservations) {
        this.telephones = reservations;
    }

    public Equipement getEquipement() {
        return equipement;
    }

    public Locaux equipement(Equipement equipement) {
        this.equipement = equipement;
        return this;
    }

    public void setEquipement(Equipement equipement) {
        this.equipement = equipement;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Locaux)) {
            return false;
        }
        return id != null && id.equals(((Locaux) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Locaux{" +
            "id=" + getId() +
            ", adresse='" + getAdresse() + "'" +
            ", ville='" + getVille() + "'" +
            ", telephone=" + getTelephone() +
            ", taille=" + getTaille() +
            ", prixJour=" + getPrixJour() +
            "}";
    }
}
