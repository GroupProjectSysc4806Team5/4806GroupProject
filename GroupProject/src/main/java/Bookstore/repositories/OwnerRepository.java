package Bookstore.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import team.GroupProject.Owner;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "owners", path = "owners")
public interface OwnerRepository {
    Owner findById(long id);
    List<Owner> findByName(String name);
}
