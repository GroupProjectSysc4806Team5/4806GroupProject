package team.GroupProject;

import java.util.ArrayList;

public class Cart {

    private ArrayList<Book> books;
    private User user;

    public Cart (User user) {
        this.user = user;
        books = new ArrayList<Book>();
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
