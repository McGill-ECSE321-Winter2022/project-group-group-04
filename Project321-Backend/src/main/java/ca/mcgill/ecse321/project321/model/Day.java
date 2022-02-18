/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.project321.model;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.Id;

// line 77 "../../../../../../model.ump"
// line 184 "../../../../../../model.ump"
@Entity
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

  public WeekDays getDay()
  {
    return day;
  }

  public Time getStoreStartHour()
  {
    return storeStartHour;
  }

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
  public Integer getID() {
    WeekDays day = getDay();
    int id = 0;
    switch(day) {
      case Monday:
          id = 1;
          break;
      case Tuesday:
          id = 2;
          break;
      case Wednesday:
          id = 3;
          break;
      case Thursday:
          id = 4;
          break;
      case Friday:
          id = 5;
          break;
      case Saturday:
          id = 6;
          break;
      case Sunday:
          id = 7;
          break;
      default:
          break;
    }
    return id;
  }

  // Unused setter: only to please hibernate
  public void setID(Integer id) {
    return;
  }
}