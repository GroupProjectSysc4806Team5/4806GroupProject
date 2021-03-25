package Bookstore.repositories;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import Bookstore.Model.Cart;
import Bookstore.Model.User;

@RepositoryRestResource(collectionResourceRel = "carts", path = "carts")
public interface CartRepository extends PagingAndSortingRepository<Cart, Long> {
	Cart findById(long id);
	Cart findByUser(User user);
}