package team.GroupProject;

import Bookstore.repositories.Cart;

import java.util.Set;

public class Customer {

	private String customerName;
	private String customerAddress;
	private String customerEmail;
	private String customerContactNumber;
	private Cart customerCart;

	private Set<Sale> sales;

	public Customer() {
		this.customerName = "";
		this.customerAddress = "";
		this.customerCart = null;
		this.customerEmail = "";
		this.customerContactNumber = "";

	}

	public Customer(String customerName, String customerAddress, String customerEmail, String customerContactNumber) {
		this.customerName = customerName;
		this.customerAddress = customerAddress;
		this.customerEmail = customerEmail;
		this.customerContactNumber = customerContactNumber;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String name) {
		this.customerName = name;
	}

	public String getAddress() {
		return this.customerAddress;
	}

	public void setCustomerAddress(String address) {
		this.customerAddress = address;
	}

	public String getCustomerEmail() {
		return this.customerEmail;
	}

	public void setCustomerEmail(String email) {
		this.customerEmail = email;
	}

	public String getCustomerContactNumber() {
		return this.customerContactNumber;
	}

	public void setCustomerContactNumber(String customerContactNumber) {
		this.customerContactNumber = customerContactNumber;
	}

	public Cart getCustomerCart() {
		return this.customerCart;
	}

	public void setCustomerCart(Cart shoppingCart) {
		this.customerCart = shoppingCart;
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
