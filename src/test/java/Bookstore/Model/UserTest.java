package Bookstore.Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {
	private Cart cart;
	private User user;
	String userName; 
	String userEmail; 
	String address;
	String phoneNumber;
	@BeforeEach
	void setUp() throws Exception {
		this.userName = "testUser";
    	this.userEmail = "testEmail";
    	this.address = "xx-street";
    	this.phoneNumber = "xxx-xxx-xxxx";
    	this.user = new User(userName, "xx-street", "testEmail", "xxx-xxx-xxxx" );
    	this.cart = new Cart();
	}

	@Test
	void testGetName() {
		assertEquals( "testUser", user.getName());
	}

	@Test
	void testGetAddress() {
		assertEquals("xx-street", user.getAddress());
	}

	@Test
	void testGetPhoneNumber() {
		assertEquals("xxx-xxx-xxxx", user.getPhoneNumber());
	}
	@Test
	void testGetEmail() {
		assertEquals("testEmail", user.getEmail());
	}

}
