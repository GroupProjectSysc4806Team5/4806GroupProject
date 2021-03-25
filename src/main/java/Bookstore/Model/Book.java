package Bookstore.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Book {

	private String isbn;

	private String bookName;

	private String picture;
	private String description;
	private String author;
	private String publisher;
	private boolean available;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Bookstore store;

	@ManyToMany(mappedBy = "books")
	private List<Cart> carts;
//	@ManyToMany(mappedBy = "sale")
//	private Sale sale;

	public Book() {

	}

	public Book(String bookName, String ISBN, String picture, String description, String author, String publisher) {
		this.bookName = bookName;
		this.isbn = ISBN;
		this.picture = picture;
		this.description = description;
		this.author = author;
		this.publisher = publisher;
		this.available = true;

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

//	public Sale getSale() {
//		return this.sale;
//	}
//
//	public void setSale(Sale sale) {
//		this.sale = sale;
//	}

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

	public boolean getAvailable() {
		return this.available;
	};

	public void setAvailable(boolean available) {
		this.available = available;
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
				", available=" + available +
				", id=" + id +
				", store=" + store +
				", carts=" + carts +
				'}';
	}
}
