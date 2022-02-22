/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.project321.model;
import java.sql.Time;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.sql.Date;

// line 67 "../../../../../../model.ump"
// line 178 "../../../../../../model.ump"
@Entity
public class TimeSlot
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TimeSlot Attributes
  private long id;
  private Time startTime;
  private Time endTime;
  private Date date;
  private int maxOrderPerSlot;

  //TimeSlot Associations
  private Day day;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TimeSlot(Time aStartTime, Time aEndTime, Date aDate, int aMaxOrderPerSlot, Day aDay)
  {
    startTime = aStartTime;
    endTime = aEndTime;
    date = aDate;
    maxOrderPerSlot = aMaxOrderPerSlot;
    if (!setDay(aDay))
    {
      throw new RuntimeException("Unable to create TimeSlot due to aDay. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public TimeSlot() {}

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

  public boolean setStartTime(Time aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(Time aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
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

  public boolean setMaxOrderPerSlot(int aMaxOrderPerSlot)
  {
    boolean wasSet = false;
    maxOrderPerSlot = aMaxOrderPerSlot;
    wasSet = true;
    return wasSet;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }

  public Date getDate()
  {
    return date;
  }

  public int getMaxOrderPerSlot()
  {
    return maxOrderPerSlot;
  }
  /* Code from template association_GetOne */
  @ManyToOne()
  @JoinColumn(name = "_day_id_")
  public Day getDay()
  {
    return day;
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
  

  public void delete()
  {
    day = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "maxOrderPerSlot" + ":" + getMaxOrderPerSlot()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "day = "+(getDay()!=null?Integer.toHexString(System.identityHashCode(getDay())):"null") + System.getProperties().getProperty("line.separator");
  }
}