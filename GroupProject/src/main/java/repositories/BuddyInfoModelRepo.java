package repositories;

import models.BuddyInfoModel;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.PagingAndSortingRepository;


import java.util.List;
@RepositoryRestResource(collectionResourceRel = "buddies", path = "buddies")

public interface BuddyInfoModelRepo extends PagingAndSortingRepository<BuddyInfoModel, Long> {
	BuddyInfoModel findById(long id);
	
    List<BuddyInfoModel> findByName(String name);
    
    List<BuddyInfoModel> findByPhoneNumber(String phoneNumber);
    
    
}