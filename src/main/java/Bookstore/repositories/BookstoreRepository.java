package Bookstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//import Bookstore.Model.Book;
import Bookstore.Model.Bookstore;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "bookstores", path = "bookstores")
public interface BookstoreRepository extends JpaRepository<Bookstore, Long> {
    Bookstore findById(long id);
    List<Bookstore> findByName(String name);
    
   /* @Modifying
    @Query("update Bookstore c set c.books = ?1 where c.id = ?2")
    void setBookstoreBooks(List<Book> books, Long id); */
}