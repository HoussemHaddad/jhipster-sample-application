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
 * A TypeDeNotification.
 */
@Entity
@Table(name = "type_de_notification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeDeNotification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "i_d_type")
    private Long iDType;

    @Column(name = "nom_type")
    private String nomType;

    @OneToMany(mappedBy = "typeDeNotification")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Notification> notifications = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getiDType() {
        return iDType;
    }

    public TypeDeNotification iDType(Long iDType) {
        this.iDType = iDType;
        return this;
    }

    public void setiDType(Long iDType) {
        this.iDType = iDType;
    }

    public String getNomType() {
        return nomType;
    }

    public TypeDeNotification nomType(String nomType) {
        this.nomType = nomType;
        return this;
    }

    public void setNomType(String nomType) {
        this.nomType = nomType;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public TypeDeNotification notifications(Set<Notification> notifications) {
        this.notifications = notifications;
        return this;
    }

    public TypeDeNotification addNotification(Notification notification) {
        this.notifications.add(notification);
        notification.setTypeDeNotification(this);
        return this;
    }

    public TypeDeNotification removeNotification(Notification notification) {
        this.notifications.remove(notification);
        notification.setTypeDeNotification(null);
        return this;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
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
        TypeDeNotification typeDeNotification = (TypeDeNotification) o;
        if (typeDeNotification.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), typeDeNotification.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TypeDeNotification{" +
            "id=" + getId() +
            ", iDType=" + getiDType() +
            ", nomType='" + getNomType() + "'" +
            "}";
    }
}
