package com.src.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Reservation.
 */
@Entity
@Table(name = "reservation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "numero_id", nullable = false)
    private Long numeroID;

    @Column(name = "equipe")
    private String equipe;

    @Column(name = "coach")
    private String coach;

    @Column(name = "local")
    private String local;

    @Column(name = "duree")
    private Long duree;

    @Column(name = "prix_total")
    private Long prixTotal;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("telephones")
    private Locaux locaux;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("telephones")
    private Equipe equipe;

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

    public Long getNumeroID() {
        return numeroID;
    }

    public Reservation numeroID(Long numeroID) {
        this.numeroID = numeroID;
        return this;
    }

    public void setNumeroID(Long numeroID) {
        this.numeroID = numeroID;
    }

    public String getEquipe() {
        return equipe;
    }

    public Reservation equipe(String equipe) {
        this.equipe = equipe;
        return this;
    }

    public void setEquipe(String equipe) {
        this.equipe = equipe;
    }

    public String getCoach() {
        return coach;
    }

    public Reservation coach(String coach) {
        this.coach = coach;
        return this;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getLocal() {
        return local;
    }

    public Reservation local(String local) {
        this.local = local;
        return this;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Long getDuree() {
        return duree;
    }

    public Reservation duree(Long duree) {
        this.duree = duree;
        return this;
    }

    public void setDuree(Long duree) {
        this.duree = duree;
    }

    public Long getPrixTotal() {
        return prixTotal;
    }

    public Reservation prixTotal(Long prixTotal) {
        this.prixTotal = prixTotal;
        return this;
    }

    public void setPrixTotal(Long prixTotal) {
        this.prixTotal = prixTotal;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public Reservation dateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Locaux getLocaux() {
        return locaux;
    }

    public Reservation locaux(Locaux locaux) {
        this.locaux = locaux;
        return this;
    }

    public void setLocaux(Locaux locaux) {
        this.locaux = locaux;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public Reservation equipe(Equipe equipe) {
        this.equipe = equipe;
        return this;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public Coach getCoach() {
        return coach;
    }

    public Reservation coach(Coach coach) {
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
        if (!(o instanceof Reservation)) {
            return false;
        }
        return id != null && id.equals(((Reservation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Reservation{" +
            "id=" + getId() +
            ", numeroID=" + getNumeroID() +
            ", equipe='" + getEquipe() + "'" +
            ", coach='" + getCoach() + "'" +
            ", local='" + getLocal() + "'" +
            ", duree=" + getDuree() +
            ", prixTotal=" + getPrixTotal() +
            ", dateDebut='" + getDateDebut() + "'" +
            "}";
    }
}
