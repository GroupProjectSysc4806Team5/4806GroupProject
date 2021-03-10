package team.GroupProject;

import java.util.ArrayList;

public class Bookstore {

    private Owner owner;

    private ArrayList<Book> books;

    public Bookstore(Owner owner){
        this.owner = owner;
        books = new ArrayList<>();
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }
}
