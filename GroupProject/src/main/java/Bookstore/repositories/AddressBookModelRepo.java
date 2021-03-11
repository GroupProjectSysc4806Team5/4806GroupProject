package Bookstore.repositories;

import Bookstore.Model.AddressBookModel;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "addressbooks", path = "addressbooks")
public interface AddressBookModelRepo extends PagingAndSortingRepository<AddressBookModel, Long> {
    AddressBookModel findById(long id);
}