/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.sql.Time;

// line 75 "model.ump"
// line 199 "model.ump"
// line 205 "model.ump"
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

  //Day Associations
  private TheGroceryStoreSystem theGroceryStoreSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Day(WeekDays aDay, Time aStoreStartHour, Time aStoreEndHour, TheGroceryStoreSystem aTheGroceryStoreSystem)
  {
    day = aDay;
    storeStartHour = aStoreStartHour;
    storeEndHour = aStoreEndHour;
    boolean didAddTheGroceryStoreSystem = setTheGroceryStoreSystem(aTheGroceryStoreSystem);
    if (!didAddTheGroceryStoreSystem)
    {
      throw new RuntimeException("Unable to create day due to theGroceryStoreSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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
  /* Code from template association_GetOne */
  public TheGroceryStoreSystem getTheGroceryStoreSystem()
  {
    return theGroceryStoreSystem;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setTheGroceryStoreSystem(TheGroceryStoreSystem aTheGroceryStoreSystem)
  {
    boolean wasSet = false;
    //Must provide theGroceryStoreSystem to day
    if (aTheGroceryStoreSystem == null)
    {
      return wasSet;
    }

    //theGroceryStoreSystem already at maximum (7)
    if (aTheGroceryStoreSystem.numberOfDaies() >= TheGroceryStoreSystem.maximumNumberOfDaies())
    {
      return wasSet;
    }
    
    TheGroceryStoreSystem existingTheGroceryStoreSystem = theGroceryStoreSystem;
    theGroceryStoreSystem = aTheGroceryStoreSystem;
    if (existingTheGroceryStoreSystem != null && !existingTheGroceryStoreSystem.equals(aTheGroceryStoreSystem))
    {
      boolean didRemove = existingTheGroceryStoreSystem.removeDay(this);
      if (!didRemove)
      {
        theGroceryStoreSystem = existingTheGroceryStoreSystem;
        return wasSet;
      }
    }
    theGroceryStoreSystem.addDay(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    TheGroceryStoreSystem placeholderTheGroceryStoreSystem = theGroceryStoreSystem;
    this.theGroceryStoreSystem = null;
    if(placeholderTheGroceryStoreSystem != null)
    {
      placeholderTheGroceryStoreSystem.removeDay(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "day" + "=" + (getDay() != null ? !getDay().equals(this)  ? getDay().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "storeStartHour" + "=" + (getStoreStartHour() != null ? !getStoreStartHour().equals(this)  ? getStoreStartHour().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "storeEndHour" + "=" + (getStoreEndHour() != null ? !getStoreEndHour().equals(this)  ? getStoreEndHour().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "theGroceryStoreSystem = "+(getTheGroceryStoreSystem()!=null?Integer.toHexString(System.identityHashCode(getTheGroceryStoreSystem())):"null");
  }
}