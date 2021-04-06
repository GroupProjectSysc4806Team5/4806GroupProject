package Bookstore.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Book> books;

    @OneToOne(mappedBy = "cart")
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Cart() {
        books = new ArrayList<Book>();
    }

    public Cart(User user) {
        this.user = user;
        books = new ArrayList<Book>();
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBook(Book book){
        books.add(book);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double calculateTotal() {
        if (!books.isEmpty())
            return books.stream().map(Book::getPrice).mapToDouble(p -> p).sum();
        return 0;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}