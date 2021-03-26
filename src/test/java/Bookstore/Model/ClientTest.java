package Bookstore.Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ClientTest {
    private String name = "Test Customer";
    private String address = "Test Address";
    private String email = "test@email.com";
    private String phoneNumber = "123-4567";
    private String username = "bookstoreUsername";
    private String password = "bookstorePassword";
    private User customer;
    
	@Test
	void testGetUsername() {
		customer = new User();
		customer.setName("Test Customer");
		assertEquals(name, customer.getName());

	}


	@Test
	void testGetPassword() {
		customer = new User();
		customer.setPassword("bookstorePassword");
		assertEquals(password, customer.getPassword());
	}

}
