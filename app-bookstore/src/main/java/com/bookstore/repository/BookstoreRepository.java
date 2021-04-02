package com.bookstore.repository;

import com.bookstore.domain.Bookstore;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Bookstore entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookstoreRepository extends JpaRepository<Bookstore, Long> {}
