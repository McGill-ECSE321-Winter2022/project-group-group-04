package ca.mcgill.ecse321.project321.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.project321.model.StoreOwner;


public interface StoreOwnerRepository extends CrudRepository<StoreOwner, String> {
    StoreOwner findByEmailAddress(String email);
}
