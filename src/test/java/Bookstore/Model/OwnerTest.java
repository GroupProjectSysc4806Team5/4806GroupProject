package Bookstore.Model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OwnerTest {
	Owner owner;
	Bookstore store1;
	List<Bookstore> stores;
	
	@BeforeEach
	void setUp() throws Exception {
		this.owner = new Owner();
		store1 = new Bookstore(owner);
		stores = new ArrayList<Bookstore>();
	}

	@Test
	void testGetName() {
		owner.setName("name");
		assertEquals("name", owner.getName());
	}

	@Test
	void testGetStore() {
		stores.add(store1);
		owner.setStores(stores);
		List<Bookstore> storess = owner.getStore();
		assertEquals(owner, storess.get(0).getOwner());
	}


}
