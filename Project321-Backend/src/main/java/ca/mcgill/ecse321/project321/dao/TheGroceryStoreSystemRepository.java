package ca.mcgill.ecse321.project321.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.project321.model.TheGroceryStoreSystem;


public interface TheGroceryStoreSystemRepository extends CrudRepository<TheGroceryStoreSystem, String> {
    TheGroceryStoreSystem findByEmail(String email);
}
