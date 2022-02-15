package ca.mcgill.ecse321.project321.dao;

import java.sql.Date;
import java.sql.Time;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.project321.model.TimeSlot;


public interface TimeslotRepository extends CrudRepository<TimeSlot, String> {
    TimeSlot findByDateAndStartTime(Date date, Time startTime);
}
