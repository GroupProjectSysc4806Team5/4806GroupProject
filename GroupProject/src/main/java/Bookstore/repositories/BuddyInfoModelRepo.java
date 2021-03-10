package Bookstore.repositories;

import Bookstore.Model.BuddyInfoModel;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "buddies", path = "buddies")
public interface BuddyInfoModelRepo extends PagingAndSortingRepository<BuddyInfoModel, Long> {
    List<BuddyInfoModel> findByName(String name);
    List<BuddyInfoModel> findByPhoneNumber(String phoneNumber);
    BuddyInfoModel findById(long id);
}