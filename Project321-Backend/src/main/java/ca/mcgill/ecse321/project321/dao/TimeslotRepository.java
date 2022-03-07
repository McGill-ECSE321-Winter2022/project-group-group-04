package ca.mcgill.ecse321.project321.dao;

import ca.mcgill.ecse321.project321.model.TimeSlot;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TimeslotRepository extends CrudRepository<TimeSlot, Integer>{
    TimeSlot findByDateAndStartTimeAndEndTime(Date date, Time startTime, Time endTime);
    List<TimeSlot> findByDate(Date date);
    List<TimeSlot> findByStartTimeBetween(Time lowerThreshold, Time upperThreshold);
    List<TimeSlot> findByEndTimeBetween(Time lowerThreshold, Time upperThreshold);
}
