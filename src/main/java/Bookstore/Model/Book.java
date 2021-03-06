package Bookstore.Model;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Book {

	private String isbn;

	private String bookName;

	private String picture;
	private String description;
	private String author;
	private String publisher;
	private boolean isAvailable;
	private double price;
	private Integer quantity;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Bookstore store;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "books")
	private List<Cart> carts;

	@ManyToMany(mappedBy = "books")
	private List<Sale> sales;

	public Book() {

	}

	public Book(String bookName, String ISBN, String picture, String description, String author, String publisher, double price, Integer quantity) {
		this.bookName = bookName;
		this.isbn = ISBN;
		this.picture = picture;
		this.description = description;
		this.author = author;
		this.publisher = publisher;
		this.isAvailable = true;
		this.price = price;
		this.quantity = quantity;
		this.sales = new ArrayList<Sale>();
	}

	public String getISBN() {
		return isbn;
	}

	public void setISBN(String ISBN) {
		this.isbn = ISBN;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPrice(double price) { this.price = price; }

	public double getPrice() { return price; }

	public Bookstore getStore() {
		return store;
	}

	public void setStore(Bookstore store) {
		this.store = store;
	}

	public List<Cart> getCarts() {
		return carts;
	}

	public void setCarts(List<Cart> carts) {
		this.carts = carts;
	}

	public void addCarts(Cart cart) {
		carts.add(cart);
	}

	public List<Sale> getSales() { return this.sales; }

	public void setSales(List<Sale> sales) { this.sales = sales; }

	public void addSales(Sale sale) { this.sales.add(sale); }

	public Long getId() {
		return id;
	}

	public boolean equals(Book book) {
		if (this.bookName.equals(book.getBookName()) && this.isbn.equals(book.getISBN())
				&& this.picture == book.getPicture() && this.description == book.getDescription()
				&& this.author == book.getAuthor() && this.publisher == book.getPublisher()) {
			return true;
		}
		return false;
	}

	public boolean getIsAvailable() {
		return this.isAvailable;
	};

	public void setAvailable(boolean available) {
		this.isAvailable = available;
	}

	@Override
	public String toString() {
		return "Book{" +
				"isbn='" + isbn + '\'' +
				", bookName='" + bookName + '\'' +
				", picture='" + picture + '\'' +
				", description='" + description + '\'' +
				", author='" + author + '\'' +
				", publisher='" + publisher + '\'' +
				", isAvailable='" + isAvailable + '\'' +
				", quantity='" + quantity +
				", id=" + id +
				", store=" + store +
				", carts=" + carts +
				'}';
	}

	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


}
