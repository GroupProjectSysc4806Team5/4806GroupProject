package Bookstore.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Book{
    private Long id;
    private String name;
    private String isbn;
    private String picture;
    private String description;
    private String author;
    private String publisher;
    private boolean available;

    private Bookstore bookstore;
    @JsonIgnore
    private List<Cart> shoppingCarts;
    @JsonIgnore
    private Sale sale;

    public Book(){ this.shoppingCarts = new ArrayList<Cart>(); }
    public Book(String name, String isbn, String picture, String description, String author, String publisher){
        this.name = name;
        this.isbn = isbn;
        this.picture = picture;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
        this.available = true;
        this.shoppingCarts = new ArrayList<Cart>();
    }

    @Id
    @GeneratedValue
    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = id; }

    @ManyToOne
    public Bookstore getBookstore(){ return this.bookstore; }
    public void setBookstore(Bookstore bookstore){ this.bookstore = bookstore; }
    public void removeBookstore(){this.bookstore = null;}

    @ManyToOne
    public Sale getSale(){ return this.sale; }
    public void setSale(Sale sale){ this.sale = sale; }
    public void removeSale(){this.sale = null;}

    @ManyToMany
    public List<Cart> getShoppingCarts(){ return this.shoppingCarts; }
    public void setShoppingCarts(List<Cart> shoppingCarts){ this.shoppingCarts = shoppingCarts; }
    public void addShoppingCart(Cart shoppingCart){this.shoppingCarts.add(shoppingCart);}
    public void removeShoppingCarts(){
        //The reason this does not call .clear() and changes the reference to a new ArrayList is because of the
        //Hibernate layer. If it is cleared here, it is possible a situation could arise that would  causes
        //issues when Hibernate tries to persist (this would occur if the reference to shoppingCarts was copied
        //somewhere else).
        this.shoppingCarts = new ArrayList<Cart>();;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getISBN() { return this.isbn; }
    public void setISBN(String isbn) { this.isbn = isbn; }

    public String getPicture() { return this.picture; }
    public void setPicture(String picture) { this.picture = picture; }

    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }

    public String getAuthor() { return this.author; }
    public void setAuthor(String author) { this.author = author; }

    public String getPublisher() { return this.publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public boolean equals(Book book){
        if (this.name.equals(book.getName()) && this.isbn.equals(book.getISBN()) && this.picture.equals(book.getPicture()) && this.description.equals(book.getDescription()) && this.author.equals(book.getAuthor()) && this.publisher.equals(book.getPublisher())){
            return true;
        }
        return false;
    }

    public boolean getAvailable(){return this.available;};
    public void setAvailable(boolean available) { this.available = available; }
}