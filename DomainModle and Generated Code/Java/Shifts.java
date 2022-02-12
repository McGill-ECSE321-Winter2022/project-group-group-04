/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.sql.Time;

// line 88 "model.ump"
// line 187 "model.ump"
public class Shifts
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Shifts Attributes
  private Time startHour;
  private Time endHour;

  //Shifts Associations
  private Days days;
  private TheGroceryStoreSystem theGroceryStoreSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Shifts(Time aStartHour, Time aEndHour, Days aDays, TheGroceryStoreSystem aTheGroceryStoreSystem)
  {
    startHour = aStartHour;
    endHour = aEndHour;
    boolean didAddDays = setDays(aDays);
    if (!didAddDays)
    {
      throw new RuntimeException("Unable to create shift due to days. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  public Time getStartHour()
  {
    return startHour;
  }

  public Time getEndHour()
  {
    return endHour;
  }
  /* Code from template association_GetOne */
  public Days getDays()
  {
    return days;
  }
  /* Code from template association_GetOne */
  public TheGroceryStoreSystem getTheGroceryStoreSystem()
  {
    return theGroceryStoreSystem;
  }
  /* Code from template association_SetOneToMany */
  public boolean setDays(Days aDays)
  {
    boolean wasSet = false;
    if (aDays == null)
    {
      return wasSet;
    }

    Days existingDays = days;
    days = aDays;
    if (existingDays != null && !existingDays.equals(aDays))
    {
      existingDays.removeShift(this);
    }
    days.addShift(this);
    wasSet = true;
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
    Days placeholderDays = days;
    this.days = null;
    if(placeholderDays != null)
    {
      placeholderDays.removeShift(this);
    }
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
            "  " + "days = "+(getDays()!=null?Integer.toHexString(System.identityHashCode(getDays())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "theGroceryStoreSystem = "+(getTheGroceryStoreSystem()!=null?Integer.toHexString(System.identityHashCode(getTheGroceryStoreSystem())):"null");
  }
}