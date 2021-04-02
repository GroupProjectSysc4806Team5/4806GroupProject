package com.bookstore.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Bookstore.
 */
@Entity
@Table(name = "bookstore")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Bookstore implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "bookstore")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "bookstore" }, allowSetters = true)
    private Set<Owner> owners = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bookstore id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Bookstore name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Owner> getOwners() {
        return this.owners;
    }

    public Bookstore owners(Set<Owner> owners) {
        this.setOwners(owners);
        return this;
    }

    public Bookstore addOwner(Owner owner) {
        this.owners.add(owner);
        owner.setBookstore(this);
        return this;
    }

    public Bookstore removeOwner(Owner owner) {
        this.owners.remove(owner);
        owner.setBookstore(null);
        return this;
    }

    public void setOwners(Set<Owner> owners) {
        if (this.owners != null) {
            this.owners.forEach(i -> i.setBookstore(null));
        }
        if (owners != null) {
            owners.forEach(i -> i.setBookstore(this));
        }
        this.owners = owners;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bookstore)) {
            return false;
        }
        return id != null && id.equals(((Bookstore) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bookstore{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
