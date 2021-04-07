package Bookstore.repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import Bookstore.Model.User;

@Repository
@RepositoryRestResource(collectionResourceRel = "customers", path = "customers")
public interface UserRepository extends JpaRepository<User, Long>{
	User findById(long id);
    List<User> findByName(String name);
    List<User> findByEmail(String email);

}