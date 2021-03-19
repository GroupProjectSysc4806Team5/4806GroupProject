package Bookstore.Model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;

import static javax.persistence.CascadeType.ALL;

@Entity
public class Sale {
	private Long id;
	@JsonIgnore
	private Set<Book> books;
	@JsonIgnore
	private User user;
	@JsonIgnore
	private Set<Bookstore> bookstores;

	public Sale() {
	}

	public Sale(Set<Book> books, User user) {
		this.books = books;
		this.user = user;
		this.bookstores = new HashSet<>();
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = ALL, mappedBy = "sale")
	public Set<Book> getBooks() {
		return this.books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	@ManyToOne
	public user getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = ALL)
	public Set<Bookstore> getBookstores() {
		return this.bookstores;
	}

	public void setBookstores(Set<Bookstore> bookstores) {
		this.bookstores = bookstores;
	}

	public void addBookstore(Bookstore bookstore) {
		this.bookstores.add(bookstore);
	}
}