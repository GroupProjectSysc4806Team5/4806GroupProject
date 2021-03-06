package Bookstore.repositories;


import Bookstore.Model.Book;
import Bookstore.Model.Bookstore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource(collectionResourceRel = "books", path = "books")
public interface BookRepository extends JpaRepository<Book, Long>{

    Book findById(long id);
    List<Book> findByBookName(String bookName);
    List<Book> findByDescription(String description);
    List<Book> findByIsbn(String isbn);
    List<Book> findByAuthor(String author);
    List<Book> findByPublisher(String publisher);
    List<Book> findByStore(Bookstore store);
    List<Book> findByQuantity(Long quantity);
}
