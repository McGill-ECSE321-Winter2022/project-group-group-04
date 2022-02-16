package ca.mcgill.ecse321.project321.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.project321.model.Address;


public interface AddressRepository extends CrudRepository<Address, String> {
    List<Address> findByStreet(String street);
}
