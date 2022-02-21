/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.project321.model;
import java.sql.Time;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import java.sql.Date;

// line 86 "../../../../../../model.ump"
// line 177 "../../../../../../model.ump"
@Entity
public class Shift
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Shift Attributes
  private long id;
  private Time startHour;
  private Time endHour;
  private Date date;

  //Shift Associations
  private Day day;
  private Employee employee;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Shift(Time aStartHour, Time aEndHour, Date aDate, Day aDay, Employee aEmployee)
  {
    startHour = aStartHour;
    endHour = aEndHour;
    date = aDate;
    if (!setDay(aDay))
    {
      throw new RuntimeException("Unable to create Shift due to aDay. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setEmployee(aEmployee))
    {
      throw new RuntimeException("Unable to create Shift due to aEmployee. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Shift() {}

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(long id) {
    this.id = id;
    return true;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public long getId() {
    return id;
  }

  public boolean setStartHour(Time aStartHour)
  {
    boolean wasSet = false;
    startHour = aStartHour;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndHour(Time aEndHour)
  {
    boolean wasSet = false;
    endHour = aEndHour;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public Time getStartHour()
  {
    return startHour;
  }

  public Time getEndHour()
  {
    return endHour;
  }

  public Date getDate()
  {
    return date;
  }
  /* Code from template association_GetOne */
  @ManyToOne(cascade = {CascadeType.ALL})
  public Day getDay()
  {
    return day;
  }
  /* Code from template association_GetOne */
  @ManyToOne(cascade = CascadeType.ALL)
  public Employee getEmployee()
  {
    return employee;
  }
  
  /* Code from template association_SetUnidirectionalOne */
  public boolean setDay(Day aNewDay)
  {
    boolean wasSet = false;
    if (aNewDay != null)
    {
      day = aNewDay;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setEmployee(Employee aNewEmployee)
  {
    boolean wasSet = false;
    if (aNewEmployee != null)
    {
      employee = aNewEmployee;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    day = null;
    employee = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startHour" + "=" + (getStartHour() != null ? !getStartHour().equals(this)  ? getStartHour().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endHour" + "=" + (getEndHour() != null ? !getEndHour().equals(this)  ? getEndHour().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "day = "+(getDay()!=null?Integer.toHexString(System.identityHashCode(getDay())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "employee = "+(getEmployee()!=null?Integer.toHexString(System.identityHashCode(getEmployee())):"null") + System.getProperties().getProperty("line.separator");
  }
}