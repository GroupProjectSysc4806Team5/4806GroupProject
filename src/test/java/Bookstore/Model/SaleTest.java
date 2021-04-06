package Bookstore.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SaleTest {
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

	@BeforeEach
	void setUp() throws Exception {
		this.bookName = "testBook";
		this.isbn = "456456654646";
		this.picture = "picture1.jpeg";
		this.description = "test book";
		this.author = "testOwner";
		this.publisher = "testPublisher";
		this.price = 25.00;
		this.book = new Book(this.bookName, this.isbn, this.picture, this.description, this.author, this.publisher, this.price);

		user =  new User("Babak", "babak@test.com", "xxxx");
		cart = new Cart(user);
		cart.addBook(book);
		user.setCart(cart);
	}

	@Test
	void testConstructors() {
		Sale sale = new Sale(user);
		assertEquals(user, sale.getCustomer());
		assertEquals(user.getCart().getBooks(), sale.getBooks());
	}

	@Test
	void testGetUser() {
		Sale sale = new Sale(user);
		assertEquals("Babak", sale.getCustomer().getName());
	}

	@Test
	void testCalculations() {
		Sale sale = new Sale(user);
		assertEquals(sale.calculateTotal(), 25.00);
		assertEquals(sale.calculateTax(), 25.00 * 0.13);
		assertEquals(sale.calculateTotalWithTax(), 28.25);
	}

}
