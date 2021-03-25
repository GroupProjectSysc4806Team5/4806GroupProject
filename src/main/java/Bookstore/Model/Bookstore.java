package Bookstore.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

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
