package Bookstore.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Bookstore {

	@ManyToOne
	private Owner owner;

	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
	private List<Book> books;
	private String name;
	// private List orders;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "bookstores")
	private Set<Sale> sales;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Bookstore() {
	}

	public Bookstore(Owner owner) {
		this.owner = owner;
		this.books = new ArrayList<Book>();
	}

	public Set<Sale> getSales() {
		return this.sales;
	}

	public void setSales(Set<Sale> sales) {
		this.sales = sales;
	}

	public void addSale(Sale sale) {
		this.sales.add(sale);
	}

	public void removeSale(Sale sale) {
		this.sales.remove(sale);
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public void removeBookstoreOwner() {
		this.owner = null;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(ArrayList<Book> books) {
		this.books = books;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void removeBook(long bookId) {
		Book bookFound = null;
		for (Book book : this.books) {
			if (book.getId() == bookId) {
				bookFound = book;
				break;
			}
		}
		if (bookFound != null) {
			this.books.remove(bookFound);
			bookFound.removeBookstore();
		}
	}

	public Long getId() {
		return id;
	}
}
