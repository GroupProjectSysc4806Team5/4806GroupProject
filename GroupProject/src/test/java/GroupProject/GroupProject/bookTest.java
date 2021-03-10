package GroupProject.GroupProject;

import org.junit.Before;

import junit.framework.TestCase;
import team.GroupProject.Book;

public class bookTest extends TestCase {
	private Book book;
	private String bookName;
    private String isbn;
    private String picture;
    private String description;
    private String author;
    private String publisher;

    @Before
    public void setUp() {
    	this.bookName = "testBook";
        this.isbn = "456456654646";
        this.picture = "picture1.jpeg";
        this.description = "test book";
        this.author = "testOwner";
        this.publisher = "testPublisher";
        this.book = new Book(this.bookName, this.isbn, this.picture, this.description, this.author, this.publisher);
    }
    
    public void testGetBookName() {
    	assertEquals("The bookName should be 456456654646", "testBook", book.getBookName());
    }
    public void testGetISBN() {
    	assertEquals("The isbn should be 456456654646", "456456654646", book.getISBN());
    }
    public void testGetPicture() {
    	assertEquals("The description should be test book", "test book", book.getDescription());
    }
    public void testGetDescription() {
    	assertEquals("The description should be 456456654646", "456456654646", book.getISBN());
    }
    public void testGetAuthor() {
    	assertEquals("The author should be testOwner", "testOwner", book.getAuthor());
    }
    public void testGetPublisher() {
    	assertEquals("The publisher should be testPublisher", "testPublisher", book.getPublisher());
    }
    
}
