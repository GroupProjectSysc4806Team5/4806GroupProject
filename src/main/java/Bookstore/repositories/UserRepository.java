package Bookstore.repositories;


import Bookstore.Model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends PagingAndSortingRepository<User,Long>{

    User findById(long id);

    List<User> findByName(String name);

    List<User> findByEmail(String email);

}
