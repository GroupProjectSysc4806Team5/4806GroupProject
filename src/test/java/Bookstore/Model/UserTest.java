package Bookstore.Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {
	private User user;
	String userName; 
	String userEmail; 
	String password;
	//Cart cart;
	
	@BeforeEach
	void setUp() throws Exception {
		this.userName = "testUser";
    	this.userEmail = "testEmail";
		this.password = "testPassword";
    	this.user = new User(userName, userEmail,password );
    	//cart = new Cart();
	}

	@Test
	void testGetName() {
		assertEquals( "testUser", user.getName());
	}

	@Test
	void testGetEmail() {
		assertEquals("testEmail", user.getEmail());
	}
	
	@Test
	void testGetPassword() {
		assertEquals(this.password, user.getPassword());
	}
	

}
