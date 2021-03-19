package Bookstore.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Cart {

	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Book> books;

	@OneToOne(mappedBy = "cart")
	private User user;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Cart() {
	}

	public Set<Book> getBooks() {
		return this.books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	public void addBook(Book book) {
		this.books.add(book);
	}

	public void removeBooks() {
		this.books = null;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public Sale checkout() {
		Sale sale = new Sale(this.books, this.user);
		for (Book book : this.books) {
			book.setAvailable(false);
			sale.addstore(book.getStore());
			book.removeShoppingCart();
			book.setSale(sale);
		}
		this.removeBooks();

		return sale;
	}
}
