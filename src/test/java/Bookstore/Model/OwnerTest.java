package Bookstore.Model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;


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
		List<Bookstore> storess = owner.getStores();
		assertEquals(owner, storess.get(0).getOwner());
	}


}
