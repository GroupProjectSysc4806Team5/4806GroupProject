package com.bookstore.repository;

import com.bookstore.domain.Cart;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Cart entity.
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query(
        value = "select distinct cart from Cart cart left join fetch cart.books",
        countQuery = "select count(distinct cart) from Cart cart"
    )
    Page<Cart> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct cart from Cart cart left join fetch cart.books")
    List<Cart> findAllWithEagerRelationships();

    @Query("select cart from Cart cart left join fetch cart.books where cart.id =:id")
    Optional<Cart> findOneWithEagerRelationships(@Param("id") Long id);
}
