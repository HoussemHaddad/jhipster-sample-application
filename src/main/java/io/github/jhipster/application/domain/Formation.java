package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Formation.
 */
@Entity
@Table(name = "formation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Formation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "i_d_formation")
    private Long iDFormation;

    @Column(name = "nom_formation")
    private String nomFormation;

    @Column(name = "information")
    private String information;

    @ManyToOne
    @JsonIgnoreProperties("formations")
    private CentreDeFormation centreDeFormation;

    @ManyToOne
    @JsonIgnoreProperties("formations")
    private CategorieFormation categorieFormation;

    @OneToMany(mappedBy = "formation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Formulaire> formulaires = new HashSet<>();
    @OneToMany(mappedBy = "formation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Reservation> reservations = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getiDFormation() {
        return iDFormation;
    }

    public Formation iDFormation(Long iDFormation) {
        this.iDFormation = iDFormation;
        return this;
    }

    public void setiDFormation(Long iDFormation) {
        this.iDFormation = iDFormation;
    }

    public String getNomFormation() {
        return nomFormation;
    }

    public Formation nomFormation(String nomFormation) {
        this.nomFormation = nomFormation;
        return this;
    }

    public void setNomFormation(String nomFormation) {
        this.nomFormation = nomFormation;
    }

    public String getInformation() {
        return information;
    }

    public Formation information(String information) {
        this.information = information;
        return this;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public CentreDeFormation getCentreDeFormation() {
        return centreDeFormation;
    }

    public Formation centreDeFormation(CentreDeFormation centreDeFormation) {
        this.centreDeFormation = centreDeFormation;
        return this;
    }

    public void setCentreDeFormation(CentreDeFormation centreDeFormation) {
        this.centreDeFormation = centreDeFormation;
    }

    public CategorieFormation getCategorieFormation() {
        return categorieFormation;
    }

    public Formation categorieFormation(CategorieFormation categorieFormation) {
        this.categorieFormation = categorieFormation;
        return this;
    }

    public void setCategorieFormation(CategorieFormation categorieFormation) {
        this.categorieFormation = categorieFormation;
    }

    public Set<Formulaire> getFormulaires() {
        return formulaires;
    }

    public Formation formulaires(Set<Formulaire> formulaires) {
        this.formulaires = formulaires;
        return this;
    }

    public Formation addFormulaire(Formulaire formulaire) {
        this.formulaires.add(formulaire);
        formulaire.setFormation(this);
        return this;
    }

    public Formation removeFormulaire(Formulaire formulaire) {
        this.formulaires.remove(formulaire);
        formulaire.setFormation(null);
        return this;
    }

    public void setFormulaires(Set<Formulaire> formulaires) {
        this.formulaires = formulaires;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public Formation reservations(Set<Reservation> reservations) {
        this.reservations = reservations;
        return this;
    }

    public Formation addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setFormation(this);
        return this;
    }

    public Formation removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
        reservation.setFormation(null);
        return this;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Formation formation = (Formation) o;
        if (formation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), formation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Formation{" +
            "id=" + getId() +
            ", iDFormation=" + getiDFormation() +
            ", nomFormation='" + getNomFormation() + "'" +
            ", information='" + getInformation() + "'" +
            "}";
    }
}
