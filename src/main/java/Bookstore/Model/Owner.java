package Bookstore.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Owner {

	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<Bookstore> stores;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Owner(String name) {
		this.name = name;
		stores = new ArrayList<Bookstore>();
	}

	public Owner() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Bookstore> getStore() {
		return stores;
	}

	public void addBookstore(Bookstore bookstore) {
		bookstore.setOwner(this);
		this.stores.add(bookstore);
	}

	public void removeBookstore(long bookstoreId) {
		Bookstore bookstoreFound = null;
		for (Bookstore bookstore : this.stores) {
			if (bookstore.getId() == bookstoreId) {
				bookstoreFound = bookstore;
				break;
			}
		}
		if (bookstoreFound != null) {
			this.stores.remove(bookstoreFound);
			bookstoreFound.removeBookstoreOwner();
		}
	}

	public void setStores(List<Bookstore> stores) {
		this.stores = stores;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
