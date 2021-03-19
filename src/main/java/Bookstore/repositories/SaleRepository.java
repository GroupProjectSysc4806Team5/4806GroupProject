package Bookstore.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import Bookstore.Model.Sale;
import Bookstore.Model.User;

@RepositoryRestResource(collectionResourceRel = "sales", path = "sales")
public interface SaleRepository extends PagingAndSortingRepository<Sale, Long> {

	Sale findById(long id);

	Iterable<Sale> findByCustomer(User user);
}
