package ca.mcgill.ecse321.project321.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.project321.model.Customer;


public interface CustomerRepository extends CrudRepository<Customer,String> {
    Customer findByEmail(String email);
}
