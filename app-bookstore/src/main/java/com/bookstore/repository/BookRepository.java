package com.bookstore.repository;

import com.bookstore.domain.Book;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Book entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select book from Book book where book.bookstore.id =:id")
    List<Book> findAllByBookstore(@Param("id") Long id);
}
