package ca.mcgill.ecse321.project321.dao;

import ca.mcgill.ecse321.project321.model.Address;
import ca.mcgill.ecse321.project321.model.Store;
import org.springframework.data.repository.CrudRepository;

public interface StoreRepository extends CrudRepository<Store, Integer>{
    Store findByAddress(Address address);
}
