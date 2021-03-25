package Bookstore.repositories;



import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import Bookstore.Model.User;

@Repository
@RepositoryRestResource(collectionResourceRel = "customers", path = "customers")
public interface UserRepository extends PagingAndSortingRepository<User, Long>{
	User findById(long id);
    List<User> findByName(String name);
    List<User> findByAddress(String address);
    List<User> findByEmail(String email);
    List<User> findByPhoneNumber(String phoneNumber);
    User findByUsername(String username);
}