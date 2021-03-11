package team.GroupProject;
import Bookstore.Model.Book;

import java.util.List;

import javax.persistence.*;

public class Order {
	
    @Id
    @GeneratedValue
    private int id;
    
    @OneToMany(fetch=FetchType.EAGER, targetEntity = Book.class)
    
    private List<Book> books;
    
    private Customer customer;
    
    public Order(int id, List<Book> books, Customer customer) {
    	
        this.id = id;
        this.books = books;
        this.customer = customer;
    }
    
    public int getId() { 
    	
    	return this.id; }
    
       public void setId(int id) {
    	this.id = id; }

     public void setBooks(List<Book> books) {
    	 
        this.books = books;
    }
 
    public List<Book> getBooks() { 
    	return this.books; }
    
    //one customer could place multiple orders
    @ManyToOne
    public Customer getCustomer() {
        return this.customer;
    }
    
    public void setCustomer(Customer customer) {
    	
        this.customer = customer;
    }
    
    
}