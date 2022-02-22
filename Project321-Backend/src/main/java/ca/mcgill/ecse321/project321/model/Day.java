/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.project321.model;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// line 77 "../../../../../../model.ump"
// line 184 "../../../../../../model.ump"
@Entity
@Table(name = "days")
public class Day
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum WeekDays { Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Day Attributes
  private long id;
  private WeekDays day;
  private Time storeStartHour;
  private Time storeEndHour;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Day(WeekDays aDay, Time aStoreStartHour, Time aStoreEndHour)
  {
    day = aDay;
    storeStartHour = aStoreStartHour;
    storeEndHour = aStoreEndHour;
  }

  public Day() {}

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDay(WeekDays aDay)
  {
    boolean wasSet = false;
    day = aDay;
    wasSet = true;
    return wasSet;
  }

  public boolean setStoreStartHour(Time aStoreStartHour)
  {
    boolean wasSet = false;
    storeStartHour = aStoreStartHour;
    wasSet = true;
    return wasSet;
  }

  public boolean setStoreEndHour(Time aStoreEndHour)
  {
    boolean wasSet = false;
    storeEndHour = aStoreEndHour;
    wasSet = true;
    return wasSet;
  }

  @Column(name = "day")
  public WeekDays getDay()
  {
    return day;
  }

  @Column(name = "start_hour")
  public Time getStoreStartHour()
  {
    return storeStartHour;
  }

  @Column(name = "end_hour")
  public Time getStoreEndHour()
  {
    return storeEndHour;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "day" + "=" + (getDay() != null ? !getDay().equals(this)  ? getDay().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "storeStartHour" + "=" + (getStoreStartHour() != null ? !getStoreStartHour().equals(this)  ? getStoreStartHour().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "storeEndHour" + "=" + (getStoreEndHour() != null ? !getStoreEndHour().equals(this)  ? getStoreEndHour().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator");
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "day_id")
  public long getID() {
    return id;
  }

  // Unused setter: only to please hibernate
  public boolean setID(long id) {
    this.id = id;
    return true;
  }
}