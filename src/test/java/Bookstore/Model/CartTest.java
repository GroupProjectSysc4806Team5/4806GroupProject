package Bookstore.Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
class CartTest {
	Cart cart;
	Book book;
	User user;
	private String bookName;
	private String isbn;
	private String picture;
	private String description;
	private String author;
	private String publisher;

	@BeforeEach
	void setUp() throws Exception {
		this.bookName = "testBook";
		this.isbn = "456456654646";
		this.picture = "picture1.jpeg";
		this.description = "test book";
		this.author = "testOwner";
		this.publisher = "testPublisher";
		
		this.book = new Book(this.bookName, this.isbn, this.picture, this.description, this.author, this.publisher);
	}
	@Test
	void testGetBooks() {
		user =  new User("Babak", "babak@test.com", "xxxx");
		cart = new Cart(user);
		cart.addBook(book);
	//	System.out.println(cart.getBooks().get(0).getBookName());
		assertEquals("testBook", cart.getBooks().get(0).getBookName());

	}

	@Test
	void testGetUser() {
		user =  new User("Babak", "babak@test.com","xxxx");
		cart = new Cart(user);
		
		assertEquals("Babak", cart.getUser().getName());
	}

}
