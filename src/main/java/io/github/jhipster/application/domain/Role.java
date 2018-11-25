package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Role.
 */
@Entity
@Table(name = "role")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "i_d_role")
    private Long iDRole;

    @Column(name = "nom_role")
    private String nomRole;

    @ManyToMany(mappedBy = "roles")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Utilisateur> utilisateurs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getiDRole() {
        return iDRole;
    }

    public Role iDRole(Long iDRole) {
        this.iDRole = iDRole;
        return this;
    }

    public void setiDRole(Long iDRole) {
        this.iDRole = iDRole;
    }

    public String getNomRole() {
        return nomRole;
    }

    public Role nomRole(String nomRole) {
        this.nomRole = nomRole;
        return this;
    }

    public void setNomRole(String nomRole) {
        this.nomRole = nomRole;
    }

    public Set<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public Role utilisateurs(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
        return this;
    }

    public Role addUtilisateur(Utilisateur utilisateur) {
        this.utilisateurs.add(utilisateur);
        utilisateur.getRoles().add(this);
        return this;
    }

    public Role removeUtilisateur(Utilisateur utilisateur) {
        this.utilisateurs.remove(utilisateur);
        utilisateur.getRoles().remove(this);
        return this;
    }

    public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
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
        Role role = (Role) o;
        if (role.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), role.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Role{" +
            "id=" + getId() +
            ", iDRole=" + getiDRole() +
            ", nomRole='" + getNomRole() + "'" +
            "}";
    }
}
