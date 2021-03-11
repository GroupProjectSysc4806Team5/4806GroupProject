package team.GroupProject;

import java.util.ArrayList;

import antlr.collections.List;

public class Bookstore {

    private Owner owner;

    private ArrayList<Book> books;
    private List orders;

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

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }
}