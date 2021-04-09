package Bookstore.Model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Cart {

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Book> books;

    @OneToOne(mappedBy = "cart")
    private User user;

    @ElementCollection
    private Map<Long, Integer> quantities;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Cart() {
        this.books = new ArrayList<>();
        this.quantities = new HashMap<>();
    }

    public Cart(User user) {
        this.user = user;
        this.books = new ArrayList<>();
        this.quantities = new HashMap<>();
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        this.quantities = new HashMap<>();
    }

    public void addBook(Book book){
        this.books.add(book);
        this.quantities.put(book.getId(), 1);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        this.quantities.remove(book.getId());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double calculateTotal() {
        if (!books.isEmpty())
            return books.stream().map(book -> book.getPrice() * this.getQuantity(book.getId())).mapToDouble(p -> p).sum();
        return 0;
    }

    public void increaseQuantity(Long bookId) {
        Book book = this.books.stream().filter(_book -> Objects.equals(_book.getId(), bookId)).findFirst().orElse(null);
        if (this.quantities.get(bookId) + 1 <= book.getQuantity()) {
            this.quantities.put(bookId, this.quantities.get(bookId) + 1);
        }
    }

    public void decreaseQuantity(Long bookId) {
        Book book = this.books.stream().filter(_book -> Objects.equals(_book.getId(), bookId)).findFirst().orElse(null);
        if (this.quantities.get(bookId) - 1 >= 0) {
            this.quantities.put(bookId, this.quantities.get(bookId) - 1);

            if (quantities.get(bookId) == 0) {
                this.quantities.remove(bookId);
                this.books.remove(book);
            }
        }
    }

    public Map<Long, Integer> getQuantities() {
        return this.quantities;
    }

    public Integer getQuantity(Long bookId) {
        return this.quantities.get(bookId);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}