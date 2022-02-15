package ca.mcgill.ecse321.project321.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.project321.model.Shift;


public interface ShiftRepository extends CrudRepository<Shift, String> {
    List<Shift> findByDate(Date date);
}
