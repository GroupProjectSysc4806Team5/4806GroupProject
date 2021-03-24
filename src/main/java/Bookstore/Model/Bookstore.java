package Bookstore.Model;

import java.util.ArrayList;
import java.util.List;


import javax.persistence.*;

@Entity
public class Bookstore {

    @Column
    private String name;

    @ManyToOne
    private Owner owner;

    @OneToMany(mappedBy = "store",cascade = CascadeType.ALL)
    private List<Book> books;

    //private List orders;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Bookstore(){ }

    public Bookstore(Owner owner){
        this.owner = owner;
        this.books = new ArrayList<Book>();
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addBook(Book book){
        books.add(book);
    }
}
