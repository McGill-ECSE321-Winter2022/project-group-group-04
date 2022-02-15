package ca.mcgill.ecse321.project321.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.project321.model.Day;
import ca.mcgill.ecse321.project321.model.Day.WeekDays;


public interface DayRepository extends CrudRepository<Day, Integer> {
    Day findByWeekDay(WeekDays day);
}
