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
 * A Utilisateur.
 */
@Entity
@Table(name = "utilisateur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "i_duser")
    private Long iDuser;

    @ManyToOne
    @JsonIgnoreProperties("utilisateurs")
    private Utilisateur utilisateur;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "utilisateur_role",
               joinColumns = @JoinColumn(name = "utilisateurs_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "utilisateur")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Utilisateur> utilisateurs = new HashSet<>();
    @OneToMany(mappedBy = "utilisateur")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Commentaire> commentaires = new HashSet<>();
    @OneToMany(mappedBy = "utilisateur")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Formulaire> formulaires = new HashSet<>();
    @OneToMany(mappedBy = "utilisateur")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Notification> notifications = new HashSet<>();
    @OneToMany(mappedBy = "utilisateur")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Reservation> reservations = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getiDuser() {
        return iDuser;
    }

    public Utilisateur iDuser(Long iDuser) {
        this.iDuser = iDuser;
        return this;
    }

    public void setiDuser(Long iDuser) {
        this.iDuser = iDuser;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public Utilisateur utilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        return this;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Utilisateur roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Utilisateur addRole(Role role) {
        this.roles.add(role);
        role.getUtilisateurs().add(this);
        return this;
    }

    public Utilisateur removeRole(Role role) {
        this.roles.remove(role);
        role.getUtilisateurs().remove(this);
        return this;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public Utilisateur utilisateurs(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
        return this;
    }

    public Utilisateur addUtilisateur(Utilisateur utilisateur) {
        this.utilisateurs.add(utilisateur);
        utilisateur.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeUtilisateur(Utilisateur utilisateur) {
        this.utilisateurs.remove(utilisateur);
        utilisateur.setUtilisateur(null);
        return this;
    }

    public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public Set<Commentaire> getCommentaires() {
        return commentaires;
    }

    public Utilisateur commentaires(Set<Commentaire> commentaires) {
        this.commentaires = commentaires;
        return this;
    }

    public Utilisateur addCommentaire(Commentaire commentaire) {
        this.commentaires.add(commentaire);
        commentaire.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeCommentaire(Commentaire commentaire) {
        this.commentaires.remove(commentaire);
        commentaire.setUtilisateur(null);
        return this;
    }

    public void setCommentaires(Set<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    public Set<Formulaire> getFormulaires() {
        return formulaires;
    }

    public Utilisateur formulaires(Set<Formulaire> formulaires) {
        this.formulaires = formulaires;
        return this;
    }

    public Utilisateur addFormulaire(Formulaire formulaire) {
        this.formulaires.add(formulaire);
        formulaire.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeFormulaire(Formulaire formulaire) {
        this.formulaires.remove(formulaire);
        formulaire.setUtilisateur(null);
        return this;
    }

    public void setFormulaires(Set<Formulaire> formulaires) {
        this.formulaires = formulaires;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public Utilisateur notifications(Set<Notification> notifications) {
        this.notifications = notifications;
        return this;
    }

    public Utilisateur addNotification(Notification notification) {
        this.notifications.add(notification);
        notification.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeNotification(Notification notification) {
        this.notifications.remove(notification);
        notification.setUtilisateur(null);
        return this;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public Utilisateur reservations(Set<Reservation> reservations) {
        this.reservations = reservations;
        return this;
    }

    public Utilisateur addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
        reservation.setUtilisateur(null);
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
        Utilisateur utilisateur = (Utilisateur) o;
        if (utilisateur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), utilisateur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
            "id=" + getId() +
            ", iDuser=" + getiDuser() +
            "}";
    }
}
