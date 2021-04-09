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
	private double price;
	private Integer quantity;

	
	@BeforeEach
	void setUp() throws Exception {
		this.bookName = "testBook";
		this.isbn = "456456654646";
		this.picture = "picture1.jpeg";
		this.description = "test book";
		this.author = "testOwner";
		this.publisher = "testPublisher";
		this.price = 25.00;
		this.quantity = 4;
		this.book = new Book(
				this.bookName,
				this.isbn,
				this.picture,
				this.description,
				this.author,
				this.publisher,
				this.price, 
				this.quantity
		);
		user =  new User("Babak", "babak@test.com", "xxxx");
		cart = new Cart(user);
	}
	@Test
	void testGetBooks() {
		cart.addBook(book);
		assertEquals("testBook", cart.getBooks().get(0).getBookName());
	}

	@Test
	void testGetUser() {
		user =  new User("Babak", "babak@test.com","xxxx");
		cart = new Cart(user);
		assertEquals("Babak", cart.getUser().getName());
	}

	@Test
	void testCalculateTotal() {
		Book book2 = new Book();
		Book book3 = new Book();
		book2.setId(1L);
		book3.setId(2L);
		book2.setPrice(31.00);
		book3.setPrice(69.00);
		book2.setQuantity(5);
		book3.setQuantity(5);
		book.setId(0L);

		cart = new Cart();
		cart.addBook(book);
		cart.addBook(book2);
		cart.addBook(book3);

		cart.increaseQuantity(book2.getId());
		cart.increaseQuantity(book3.getId());

		assertEquals(cart.calculateTotal(), 31.00 * 2 + 69.00 * 2 + 25.00);
	}

}
