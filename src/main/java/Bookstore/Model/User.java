package Bookstore.Model;

import static javax.persistence.CascadeType.ALL;
import java.util.Set;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class User {

    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private Long id;
    @JsonIgnore
    private Set<Sale> sales;
    @JsonIgnore
    private Cart cart;
    
    public User() {}

    public User(String n, String e, String add, String num) {
        name = n;
        phoneNumber = num;
        email = e;
        address = add;
        
    }

    @Id
    @GeneratedValue
    public Long getId() {return id;}
   
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getEmail() {
        return email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String number) {
        phoneNumber = number;
    }
    
    @OneToMany(fetch = FetchType.EAGER, cascade=ALL, mappedBy="user")
    public Set<Sale> getSales(){ 
    	return sales;
    }
    
    public void setSales(Set<Sale> s){
        sales = s;
    }
    public void addSale(Sale s){
    	sales.add(s);
    }
    
    @OneToOne(fetch = FetchType.EAGER, cascade=ALL)
    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
    
   
}

