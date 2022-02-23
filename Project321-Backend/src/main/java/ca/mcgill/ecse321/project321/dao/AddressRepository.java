package ca.mcgill.ecse321.project321.dao;

import ca.mcgill.ecse321.project321.model.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Integer>{
    Address findByUnitAndStreetAndTownAndPostalCode(int unit, String street, String town, String postalCode);
}
