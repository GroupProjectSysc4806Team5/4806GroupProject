package Bookstore.Model;

import java.util.Set;

import javax.persistence.*;

@Entity
public class User {

	private String name;
	private String email;
	private String phoneNumber;
	private String address;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	public Cart cart;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "customer")
	private Set<Sale> sales;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public User() {
	}

	public User(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Set<Sale> getSales() {
		return this.sales;
	}

	public void setSales(Set<Sale> sales) {
		this.sales = sales;
	}

	public void addSale(Sale sale) {
		this.sales.add(sale);
	}
}
