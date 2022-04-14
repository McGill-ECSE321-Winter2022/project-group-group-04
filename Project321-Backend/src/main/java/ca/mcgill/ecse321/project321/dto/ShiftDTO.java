package ca.mcgill.ecse321.project321.dto;

import java.sql.Date;
import java.sql.Time;

public class ShiftDTO {
	  private Time startHour;
	  private Time endHour;
	  private Date date;
	  private EmployeeDTO employee;
	  
	  public ShiftDTO(Time startHour, Time endHour, Date date, EmployeeDTO employee) {
		  this.startHour = startHour;
		  this.endHour = endHour;
		  this.date = date;
		  this.employee = employee;
	  }
	  
	  public Time getStartHour() {
		return startHour;
	  }
	  
	  public Time getEndHour() {
		return endHour;
	  }
	  
	  public Date getDate() {
		return date;
	  }
	  
	  public EmployeeDTO getEmployee() {
		return employee;
	  }
}
