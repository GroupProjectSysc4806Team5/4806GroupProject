package team.GroupProject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {

    @ManyToMany
    private List<Book> books;

    @OneToOne
    private User user;

    @Id
    @GeneratedValue
    private Long id;

    public Cart() {}

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

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }
}
