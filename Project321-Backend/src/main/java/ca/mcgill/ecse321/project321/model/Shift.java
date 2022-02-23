/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.project321.model;
import java.sql.Time;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import java.sql.Date;

// line 77 "../../../../../../model.ump"
// line 169 "../../../../../../model.ump"
@Entity
public class Shift
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Shift Attributes
  private Time startHour;
  private Time endHour;
  private Date date;
  private int shiftId;

  //Shift Associations
  private Employee employee;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Shift(Time aStartHour, Time aEndHour, Date aDate, Employee aEmployee)
  {
    startHour = aStartHour;
    endHour = aEndHour;
    date = aDate;
    shiftId = 0;
    if (!setEmployee(aEmployee))
    {
      throw new RuntimeException("Unable to create Shift due to aEmployee. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Shift() {}

  //------------------------
  // INTERFACE
  //------------------------

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

  public boolean setShiftId(int aShiftId)
  {
    boolean wasSet = false;
    shiftId = aShiftId;
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

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public int getShiftId()
  {
    return shiftId;
  }
  /* Code from template association_GetOne */
  @OneToOne(cascade = CascadeType.MERGE)
  public Employee getEmployee()
  {
    return employee;
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
    employee = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "shiftId" + ":" + getShiftId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startHour" + "=" + (getStartHour() != null ? !getStartHour().equals(this)  ? getStartHour().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endHour" + "=" + (getEndHour() != null ? !getEndHour().equals(this)  ? getEndHour().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "employee = "+(getEmployee()!=null?Integer.toHexString(System.identityHashCode(getEmployee())):"null");
  }
}