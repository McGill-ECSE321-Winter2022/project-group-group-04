package ca.mcgill.ecse321.project321.dao;

import ca.mcgill.ecse321.project321.model.Employee;
import ca.mcgill.ecse321.project321.model.Shift;

import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ShiftRepository extends CrudRepository<Shift, Integer>{
    List<Shift> findByEmployee(Employee employee);
    List<Shift> findByDate(Date date);
    Shift findByDateAndEmployee(Employee employee, Date date);
}
