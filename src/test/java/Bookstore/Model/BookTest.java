package Bookstore.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
class BookTest {
	private Book book;
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
	void testGetISBN() {
		assertEquals("456456654646", book.getISBN());
	}

	@Test
	void testGetBookName() {
		assertEquals("testBook", book.getBookName());
	}

	@Test
	void testGetPicture() {
		assertEquals(picture, book.getPicture());
	}

	@Test
	void testGetDescription() {
		assertEquals("test book", book.getDescription());
	}

	@Test
	void testGetAuthor() {
		assertEquals("testOwner", book.getAuthor());
	}

	@Test
	void testGetPublisher() {
		assertEquals("testPublisher", book.getPublisher());
	}

	@Test
	public void testEquals() {
		Book sameBook = new Book(this.bookName, this.isbn, this.picture, this.description, this.author, this.publisher);
		Book differentBook = new Book("Different Test Book", "987654321", "different_picture.jpeg",
				"book for testing purposes", "Mark Twain", "96024 publishing");

		assert (this.book.equals(sameBook));
		assert (!this.book.equals(differentBook));
	}
}