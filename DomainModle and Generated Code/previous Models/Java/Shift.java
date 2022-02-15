/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.sql.Time;
import java.sql.Date;

// line 83 "model.ump"
// line 193 "model.ump"
public class Shift
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Shift Attributes
  private Time startHour;
  private Time endHour;
  private Date date;

  //Shift Associations
  private Day day;
  private TheGroceryStoreSystem theGroceryStoreSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Shift(Time aStartHour, Time aEndHour, Date aDate, Day aDay, TheGroceryStoreSystem aTheGroceryStoreSystem)
  {
    startHour = aStartHour;
    endHour = aEndHour;
    date = aDate;
    if (!setDay(aDay))
    {
      throw new RuntimeException("Unable to create Shift due to aDay. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddTheGroceryStoreSystem = setTheGroceryStoreSystem(aTheGroceryStoreSystem);
    if (!didAddTheGroceryStoreSystem)
    {
      throw new RuntimeException("Unable to create shift due to theGroceryStoreSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

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
  public Day getDay()
  {
    return day;
  }
  /* Code from template association_GetOne */
  public TheGroceryStoreSystem getTheGroceryStoreSystem()
  {
    return theGroceryStoreSystem;
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
  /* Code from template association_SetOneToMany */
  public boolean setTheGroceryStoreSystem(TheGroceryStoreSystem aTheGroceryStoreSystem)
  {
    boolean wasSet = false;
    if (aTheGroceryStoreSystem == null)
    {
      return wasSet;
    }

    TheGroceryStoreSystem existingTheGroceryStoreSystem = theGroceryStoreSystem;
    theGroceryStoreSystem = aTheGroceryStoreSystem;
    if (existingTheGroceryStoreSystem != null && !existingTheGroceryStoreSystem.equals(aTheGroceryStoreSystem))
    {
      existingTheGroceryStoreSystem.removeShift(this);
    }
    theGroceryStoreSystem.addShift(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    day = null;
    TheGroceryStoreSystem placeholderTheGroceryStoreSystem = theGroceryStoreSystem;
    this.theGroceryStoreSystem = null;
    if(placeholderTheGroceryStoreSystem != null)
    {
      placeholderTheGroceryStoreSystem.removeShift(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startHour" + "=" + (getStartHour() != null ? !getStartHour().equals(this)  ? getStartHour().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endHour" + "=" + (getEndHour() != null ? !getEndHour().equals(this)  ? getEndHour().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "day = "+(getDay()!=null?Integer.toHexString(System.identityHashCode(getDay())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "theGroceryStoreSystem = "+(getTheGroceryStoreSystem()!=null?Integer.toHexString(System.identityHashCode(getTheGroceryStoreSystem())):"null");
  }
}