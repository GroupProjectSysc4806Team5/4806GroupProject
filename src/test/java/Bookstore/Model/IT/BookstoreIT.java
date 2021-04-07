package Bookstore.Model.IT;



import Bookstore.Model.Book;
import Bookstore.Model.Bookstore;
import Bookstore.Model.Cart;
import Bookstore.Model.Owner;
import Bookstore.Model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class BookstoreIT {
	
	private Bookstore store1;
	private Bookstore store2;
	private Owner owner;
	private String ownerName;
	private List<Book> books;
	private Book book1;
	private Book book2;
	Cart cart;
	
	private String bookName;
	private String isbn;
	private String picture;
	private String description;
	private String author;
	private String publisher;
	private double price;
	private Integer quantity;
	private List<Bookstore> stores;

	private User user;
	private String userName; 
	private String userEmail; 
	private String password;
	


	@BeforeEach
	void setUp() throws Exception {
		
		this.ownerName = "testOwner";
    	this.owner = new Owner();
    	owner.setName(ownerName);
    	
    	this.store1 = new Bookstore(owner);
    	this.store1.setOwner(owner);
    	this.stores = new ArrayList<Bookstore>();
    	stores.add(store1);
    	stores.add(store2);
    	
    	this.bookName = "testBook";
		this.isbn = "456456654646";
		this.picture = "picture1.jpeg";
		this.description = "test book";
		this.author = "testOwner";
		this.publisher = "testPublisher";
		this.price = 25.00;
		this.quantity = 4;
		
		this.book1 = new Book(this.bookName, this.isbn, this.picture, this.description, this.author, this.publisher, this.price, quantity);
		this.book2 = new Book(this.bookName, this.isbn, this.picture, this.description, this.author, this.publisher, this.price, quantity);
		books = new ArrayList<Book>();
		books.add(book1);
		books.add(book2);
		
		
		this.userName = "testUser";
    	this.userEmail = "testEmail";
		this.password = "testPassword";
    	this.user = new User(userName,userEmail,password);
	}

	//BOOKSTORE TESTS
	@Test
	public void testBookstoreGetOwner() {
		assertEquals( "testOwner", (store1.getOwner()).getName());
	}

	@Test
	public void testBookstoreGetBooks() {
		assertEquals(2, books.size());
	}
	
	//OWNER TESTS
	@Test
	public void testOwnerGetName() {
		assertEquals("testOwner", owner.getName());
	}

	@Test
	public void testOwnerGetStore() {
		
		owner.setStores(stores);
		List<Bookstore> storess = owner.getStores();
		assertEquals(owner, storess.get(0).getOwner());
	}
	
	//USER TESTS
	@Test
	public void testUserGetName() {
		assertEquals( "testUser", user.getName());
	}
	@Test
	public void testUserGetEmail() {
		assertEquals("testEmail", user.getEmail());
	}
	
	//BOOK TESTS
	@Test
	public void testBookGetISBN() {
		assertEquals(this.isbn, book1.getISBN());
	}

	@Test
	public void testBookGetBookName() {
		assertEquals(this.bookName, book1.getBookName());
	}

	@Test
	public void testBookGetPicture() {
		assertEquals(this.picture, book1.getPicture());
	}

	@Test
	public void testBookGetDescription() {
		assertEquals(this.description, book1.getDescription());
	}

	@Test
	public void testBookGetAuthor() {
		assertEquals(this.author, book1.getAuthor());
	}

	@Test
	public void testBookGetPublisher() {
		assertEquals(this.publisher, book1.getPublisher());
	}

	@Test
	public void testBookEquals() {
		Book sameBook = new Book(this.bookName, this.isbn, this.picture, this.description, this.author, this.publisher, this.price, quantity);
		Book differentBook = new Book("Different Test Book", "987654321", "different_picture.jpeg",
				"book for testing purposes", "Mark Twain", "96024 publishing", 20.00, quantity);
		assert (this.book1.equals(sameBook));
		assert (!this.book1.equals(differentBook));
	}
	
	//CART TEST	
	@Test
	public void testCartGetBooks() {
		cart = new Cart(user);
		cart.addBook(book1);
		assertEquals(this.bookName, cart.getBooks().get(0).getBookName());
	}
	
	@Test
	public void testCartGetUser() {
		cart = new Cart(user);
		assertEquals("testUser", cart.getUser().getName());
	}
	
	//CLIENT TESTS
	
	@Test
	public void testClientGetUsername() {
		assertEquals(userName, user.getName());
	}

	@Test
	public void testClientGetPassword() {
		assertEquals(password, user.getPassword());
	}
	
	
	
	

}
