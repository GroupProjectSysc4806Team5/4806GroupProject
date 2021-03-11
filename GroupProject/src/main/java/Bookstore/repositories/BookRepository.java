package Bookstore.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import team.GroupProject.Book;
import team.GroupProject.Bookstore;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "books", path = "books")
public interface BookRepository extends PagingAndSortingRepository<Book, Long>{
    Book findById(long id);
    List<Book> findByName(String name);
    List<Book> findByIsbn(String isbn);
    List<Book> findByDescription(String description);
    List<Book> findByAuthor(String author);
    List<Book> findByPublisher(String publisher);
    Iterable<Book> findByBookstore(Bookstore bookstore);
}
