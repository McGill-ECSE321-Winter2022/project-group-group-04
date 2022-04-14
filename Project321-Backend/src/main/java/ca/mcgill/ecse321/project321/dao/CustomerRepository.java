package ca.mcgill.ecse321.project321.dao;

import ca.mcgill.ecse321.project321.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, String> {
    Customer findByEmail(String email);
}
