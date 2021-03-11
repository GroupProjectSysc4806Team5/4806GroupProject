package Bookstore.repositories;

import Bookstore.Model.Owner;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "owners", path = "owners")
public interface OwnerRepository extends PagingAndSortingRepository<Owner,Long> {
    Owner findById(long id);
    List<Owner> findByName(String name);
}
