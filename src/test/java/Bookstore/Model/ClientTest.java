package Bookstore.Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ClientTest {
    private String name = "Test Customer";
    private String password = "bookstorePassword";
    private String role = "myrole";
    
    Client client = new Client(name,password,role);
    
	@Test
	void testGetUsername() {
		assertEquals(name, client.getUsername());

	}
	@Test
	void testGetPassword() {
		assertEquals(password, client.getPassword());
	}
	
	@Test
	void testGetRole() {
		assertEquals(role, client.getRole());
	}

}
