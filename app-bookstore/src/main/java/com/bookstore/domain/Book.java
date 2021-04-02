package com.bookstore.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Book.
 */
@Entity
@Table(name = "book")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "author")
    private String author;

    @Column(name = "available")
    private Boolean available;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "description")
    private String description;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "picture")
    private String picture;

    @Column(name = "publisher")
    private String publisher;

    @ManyToOne
    @JsonIgnoreProperties(value = { "owners" }, allowSetters = true)
    private Bookstore bookstore;

    @ManyToMany(mappedBy = "books")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "books" }, allowSetters = true)
    private Set<Cart> carts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book id(Long id) {
        this.id = id;
        return this;
    }

    public String getAuthor() {
        return this.author;
    }

    public Book author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Boolean getAvailable() {
        return this.available;
    }

    public Book available(Boolean available) {
        this.available = available;
        return this;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getBookName() {
        return this.bookName;
    }

    public Book bookName(String bookName) {
        this.bookName = bookName;
        return this;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getDescription() {
        return this.description;
    }

    public Book description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public Book isbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPicture() {
        return this.picture;
    }

    public Book picture(String picture) {
        this.picture = picture;
        return this;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public Book publisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Bookstore getBookstore() {
        return this.bookstore;
    }

    public Book bookstore(Bookstore bookstore) {
        this.setBookstore(bookstore);
        return this;
    }

    public void setBookstore(Bookstore bookstore) {
        this.bookstore = bookstore;
    }

    public Set<Cart> getCarts() {
        return this.carts;
    }

    public Book carts(Set<Cart> carts) {
        this.setCarts(carts);
        return this;
    }

    public Book addCart(Cart cart) {
        this.carts.add(cart);
        cart.getBooks().add(this);
        return this;
    }

    public Book removeCart(Cart cart) {
        this.carts.remove(cart);
        cart.getBooks().remove(this);
        return this;
    }

    public void setCarts(Set<Cart> carts) {
        if (this.carts != null) {
            this.carts.forEach(i -> i.removeBook(this));
        }
        if (carts != null) {
            carts.forEach(i -> i.addBook(this));
        }
        this.carts = carts;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        return id != null && id.equals(((Book) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Book{" +
            "id=" + getId() +
            ", author='" + getAuthor() + "'" +
            ", available='" + getAvailable() + "'" +
            ", bookName='" + getBookName() + "'" +
            ", description='" + getDescription() + "'" +
            ", isbn='" + getIsbn() + "'" +
            ", picture='" + getPicture() + "'" +
            ", publisher='" + getPublisher() + "'" +
            "}";
    }
}
