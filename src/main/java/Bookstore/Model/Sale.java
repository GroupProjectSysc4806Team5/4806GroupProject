package Bookstore.Model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Sale {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToMany(cascade = CascadeType.ALL)
	private List<Book> books;

	@ManyToOne
	private User user;

	@ElementCollection
	private Map<Long, Integer> quantities;

	public Sale() {
	}

	public Sale(User user) {
		this.user = user;
		this.books = new ArrayList<>(user.getCart().getBooks());
		this.quantities = new HashMap<>(user.getCart().getQuantities());
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Book> getBooks() {
		return this.books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}


	public User getCustomer() {
		return this.user;
	}

	public void setCustomer(User customer) {
		this.user = customer;
	}

	public Integer getQuantity(Long bookId) {
		return this.quantities.get(bookId);
	}

	public double calculateTotal() {
		if (!books.isEmpty())
			return books.stream().map(book -> book.getPrice() * this.getQuantity(book.getId())).mapToDouble(p -> p).sum();
		return 0;
	}

	public double calculateTax() {
		return this.calculateTotal() * 0.13;
	}

	public double calculateTotalWithTax() {
		return this.calculateTotal() + this.calculateTax();
	}
}