package team.GroupProject;

public class Book {


    private String ISBN;

    // This can be a link to a picture to be displayed
    private String picture;
    private String description;
    private String author;
    private String publisher;

    // A book will be liked to a bookstore
    

    public Book(String ISBN, String picture, String description, String author, String publisher) {
        this.ISBN = ISBN;
        this.picture = picture;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
