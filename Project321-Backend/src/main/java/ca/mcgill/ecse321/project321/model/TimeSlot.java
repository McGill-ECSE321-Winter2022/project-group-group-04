/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.project321.model;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.sql.Date;

// line 68 "../../../../../../model.ump"
// line 164 "../../../../../../model.ump"
@Entity
public class TimeSlot
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TimeSlot Attributes

  private Time startTime;
  private Time endTime;
  private Date date;
  private int maxOrderPerSlot;
  private int timeSlotId;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TimeSlot(Time aStartTime, Time aEndTime, Date aDate, int aMaxOrderPerSlot)
  {
    startTime = aStartTime;
    endTime = aEndTime;
    date = aDate;
    maxOrderPerSlot = aMaxOrderPerSlot;
    timeSlotId = 0;
  }

  public TimeSlot() {}

  //------------------------
  // INTERFACE
  //------------------------

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

  public boolean setTimeSlotId(int aTimeSlotId)
  {
    boolean wasSet = false;
    timeSlotId = aTimeSlotId;
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


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public int getTimeSlotId()
  {
    return timeSlotId;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "maxOrderPerSlot" + ":" + getMaxOrderPerSlot()+ "," +
            "timeSlotId" + ":" + getTimeSlotId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}