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
    @JsonIgnoreProperties(value = { "bookstore", "carts" }, allowSetters = true)
    private Set<Book> books = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "bookstores" }, allowSetters = true)
    private Owner owner;

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

    public Set<Book> getBooks() {
        return this.books;
    }

    public Bookstore books(Set<Book> books) {
        this.setBooks(books);
        return this;
    }

    public Bookstore addBook(Book book) {
        this.books.add(book);
        book.setBookstore(this);
        return this;
    }

    public Bookstore removeBook(Book book) {
        this.books.remove(book);
        book.setBookstore(null);
        return this;
    }

    public void setBooks(Set<Book> books) {
        if (this.books != null) {
            this.books.forEach(i -> i.setBookstore(null));
        }
        if (books != null) {
            books.forEach(i -> i.setBookstore(this));
        }
        this.books = books;
    }

    public Owner getOwner() {
        return this.owner;
    }

    public Bookstore owner(Owner owner) {
        this.setOwner(owner);
        return this;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
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
