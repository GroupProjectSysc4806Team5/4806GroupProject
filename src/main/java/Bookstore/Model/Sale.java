package Bookstore.Model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Sale {
	@Id
	@GeneratedValue
	private Long id;
	@OneToMany(mappedBy = "sale")
	private Set<Book> books;
	@ManyToOne
	private User customer;
	@ManyToMany
	private Set<Bookstore> bookstores;

	public Sale() {
	}

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

	public User getCustomer() {
		return this.customer;
	}

	public void setCustomer(User user) {
		this.customer = user;
	}


	public Set<Bookstore> getBookstores() {
		return this.bookstores;
	}

	public void setBookstores(Set<Bookstore> bookstores) {
		this.bookstores = bookstores;
	}

	public void addstore(Bookstore bookstore) {
		this.bookstores.add(bookstore);
	}
}