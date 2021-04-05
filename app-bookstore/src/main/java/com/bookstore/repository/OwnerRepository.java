package com.bookstore.repository;

import com.bookstore.domain.Customer;
import com.bookstore.domain.Owner;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Owner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    @Query("select owner from Owner owner where owner.user.login =:username")
    Optional<Owner> findByUserLogin(@Param("username") String username);
}
