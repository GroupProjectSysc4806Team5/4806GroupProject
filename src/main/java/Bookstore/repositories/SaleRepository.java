package Bookstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import Bookstore.Model.Sale;
import Bookstore.Model.User;

@RepositoryRestResource(collectionResourceRel = "sales", path = "sales")
public interface SaleRepository extends JpaRepository<Sale, Long> {
    Sale findById(long id);
    Iterable<Sale> findByUser(User user);
}