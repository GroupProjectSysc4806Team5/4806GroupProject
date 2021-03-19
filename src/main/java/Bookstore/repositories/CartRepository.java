package Bookstore.repositories;


import Bookstore.Model.Cart;
import Bookstore.Model.User;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "carts", path = "carts")
public interface CartRepository extends PagingAndSortingRepository<Cart,Long>{
    Cart findById(long id);
    User findByCustomer(User customer);
}
