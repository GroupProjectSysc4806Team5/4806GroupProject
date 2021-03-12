package Bookstore.Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {
	private Cart cart;
	private User user;
	String userName; 
	String userEmail; 
	@BeforeEach
	void setUp() throws Exception {
		this.userName = "testUser";
    	this.userEmail = "testEmail";
    	this.user = new User(userName, userEmail);
    	this.cart = new Cart();
	}

	@Test
	void testGetName() {
		assertEquals( "testUser", String.valueOf(user.getName()));
	}

	@Test
	void testGetEmail() {
		assertEquals("testEmail", (String)user.getEmail());
	}


}
