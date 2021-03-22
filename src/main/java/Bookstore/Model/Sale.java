package Bookstore.Model;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.CascadeType.ALL;

@Entity
public class Sale {
	private Long id;
//	private Set<Book> books;
	private User user;
	private Set<Bookstore> bookstores;

	public Sale() {
	}

	public Sale(Set<Book> books, User user) {
//		this.books = books;
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

//	@ManyToMany(fetch = FetchType.EAGER, cascade = ALL, mappedBy = "sale")
//	public Set<Book> getBooks() {
//		return this.books;
//	}
//
//	public void setBooks(Set<Book> books) {
//		this.books = books;
//	}

	@ManyToOne
	public User getCustomer() {
		return this.user;
	}

	public void setCustomer(User customer) {
		this.user = customer;
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