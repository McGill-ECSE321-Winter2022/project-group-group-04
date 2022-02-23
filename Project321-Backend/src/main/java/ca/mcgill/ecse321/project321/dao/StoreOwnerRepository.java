package ca.mcgill.ecse321.project321.dao;

import ca.mcgill.ecse321.project321.model.StoreOwner;
import org.springframework.data.repository.CrudRepository;

public interface StoreOwnerRepository extends CrudRepository<StoreOwner, String>{
    StoreOwner findByEmail(String email);
}
