package Bookstore.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookstoreTest {
	private Bookstore bookStore;
	private Owner owner;
	private String ownerName;
	private List<Book> books;
	private Book book1;
	private Book book2;
	
	private String bookName;
	private String isbn;
	private String picture;
	private String description;
	private String author;
	private String publisher;

	@BeforeEach
	void setUp() throws Exception {
		this.ownerName = "testOwner";
    	this.owner = new Owner();
    	owner.setName(ownerName);
    	this.bookStore = new Bookstore(owner);
    	this.bookStore.setOwner(owner);
    	
    	
    	this.bookName = "testBook";
		this.isbn = "456456654646";
		this.picture = "picture1.jpeg";
		this.description = "test book";
		this.author = "testOwner";
		this.publisher = "testPublisher";
		this.book1 = new Book(this.bookName, this.isbn, this.picture, this.description, this.author, this.publisher);
		
		this.book2 = new Book(this.bookName, this.isbn, this.picture, this.description, this.author, this.publisher);
		books = new ArrayList<Book>();
		books.add(book1);
		books.add(book2);
	}

	@Test
	void testGetOwner() {
		assertEquals( "testOwner", (bookStore.getOwner()).getName());
	}

	@Test
	void testGetBooks() {
		assertEquals(2, books.size());
	}
}
