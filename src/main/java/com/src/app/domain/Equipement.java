package com.src.app.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Equipement.
 */
@Entity
@Table(name = "equipement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Equipement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prix_jour")
    private Long prixJour;

    @OneToMany(mappedBy = "equipement")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Locaux> noms = new HashSet<>();

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

    public Equipement type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public Equipement nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getPrixJour() {
        return prixJour;
    }

    public Equipement prixJour(Long prixJour) {
        this.prixJour = prixJour;
        return this;
    }

    public void setPrixJour(Long prixJour) {
        this.prixJour = prixJour;
    }

    public Set<Locaux> getNoms() {
        return noms;
    }

    public Equipement noms(Set<Locaux> locauxes) {
        this.noms = locauxes;
        return this;
    }

    public Equipement addNom(Locaux locaux) {
        this.noms.add(locaux);
        locaux.setEquipement(this);
        return this;
    }

    public Equipement removeNom(Locaux locaux) {
        this.noms.remove(locaux);
        locaux.setEquipement(null);
        return this;
    }

    public void setNoms(Set<Locaux> locauxes) {
        this.noms = locauxes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Equipement)) {
            return false;
        }
        return id != null && id.equals(((Equipement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Equipement{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", nom='" + getNom() + "'" +
            ", prixJour=" + getPrixJour() +
            "}";
    }
}
