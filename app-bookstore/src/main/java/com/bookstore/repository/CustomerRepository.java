package com.bookstore.repository;

import com.bookstore.domain.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Customer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("select customer from Customer customer where customer.user.login =:username")
    Optional<Customer> findByUserLogin(@Param("username") String username);
}
