package Bookstore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import Bookstore.Model.Owner;


@RepositoryRestResource(collectionResourceRel = "bookstoreowners", path = "bookstoreowners")
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Owner findById(long id);
    List<Owner> findByName(String name);
}