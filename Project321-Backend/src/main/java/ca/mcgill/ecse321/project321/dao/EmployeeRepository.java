package ca.mcgill.ecse321.project321.dao;

import ca.mcgill.ecse321.project321.model.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, String>{
    Employee findByEmail(String email);
}
