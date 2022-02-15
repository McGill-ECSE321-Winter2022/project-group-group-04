package ca.mcgill.ecse321.project321.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.project321.model.Employee;


public interface EmployeeRepository extends CrudRepository<Employee, String> {
    Employee findByEmail(String email);
}
