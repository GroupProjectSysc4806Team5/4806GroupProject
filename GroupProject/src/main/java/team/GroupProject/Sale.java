package team.GroupProject;

import java.util.HashSet;
import java.util.Set;

public class Sale {

	private Set<Book> books;

	private Customer customer;

	private Set<Bookstore> bookstores;

	public Sale() {
	}

	public Sale(Set<Book> books, Customer customer) {
		this.books = books;
		this.customer = customer;
		this.bookstores = new HashSet<Bookstore>();
	}

	public Set<Book> getBooks() {
		return this.books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

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
