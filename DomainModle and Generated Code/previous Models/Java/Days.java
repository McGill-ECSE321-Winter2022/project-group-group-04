/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.sql.Time;
import java.util.*;

// line 78 "model.ump"
// line 180 "model.ump"
public class Days
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum WeekDays { Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Days Attributes
  private WeekDays days;
  private Time storeStartHour;
  private Time storeEndHour;

  //Days Associations
  private StoreInfo storeInfo;
  private List<Shifts> shifts;
  private TheGroceryStoreSystem theGroceryStoreSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Days(WeekDays aDays, Time aStoreStartHour, Time aStoreEndHour, StoreInfo aStoreInfo, TheGroceryStoreSystem aTheGroceryStoreSystem)
  {
    days = aDays;
    storeStartHour = aStoreStartHour;
    storeEndHour = aStoreEndHour;
    boolean didAddStoreInfo = setStoreInfo(aStoreInfo);
    if (!didAddStoreInfo)
    {
      throw new RuntimeException("Unable to create day due to storeInfo. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    shifts = new ArrayList<Shifts>();
    boolean didAddTheGroceryStoreSystem = setTheGroceryStoreSystem(aTheGroceryStoreSystem);
    if (!didAddTheGroceryStoreSystem)
    {
      throw new RuntimeException("Unable to create day due to theGroceryStoreSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDays(WeekDays aDays)
  {
    boolean wasSet = false;
    days = aDays;
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

  public WeekDays getDays()
  {
    return days;
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
  public StoreInfo getStoreInfo()
  {
    return storeInfo;
  }
  /* Code from template association_GetMany */
  public Shifts getShift(int index)
  {
    Shifts aShift = shifts.get(index);
    return aShift;
  }

  public List<Shifts> getShifts()
  {
    List<Shifts> newShifts = Collections.unmodifiableList(shifts);
    return newShifts;
  }

  public int numberOfShifts()
  {
    int number = shifts.size();
    return number;
  }

  public boolean hasShifts()
  {
    boolean has = shifts.size() > 0;
    return has;
  }

  public int indexOfShift(Shifts aShift)
  {
    int index = shifts.indexOf(aShift);
    return index;
  }
  /* Code from template association_GetOne */
  public TheGroceryStoreSystem getTheGroceryStoreSystem()
  {
    return theGroceryStoreSystem;
  }
  /* Code from template association_SetOneToMany */
  public boolean setStoreInfo(StoreInfo aStoreInfo)
  {
    boolean wasSet = false;
    if (aStoreInfo == null)
    {
      return wasSet;
    }

    StoreInfo existingStoreInfo = storeInfo;
    storeInfo = aStoreInfo;
    if (existingStoreInfo != null && !existingStoreInfo.equals(aStoreInfo))
    {
      existingStoreInfo.removeDay(this);
    }
    storeInfo.addDay(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfShifts()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Shifts addShift(Time aStartHour, Time aEndHour, TheGroceryStoreSystem aTheGroceryStoreSystem)
  {
    return new Shifts(aStartHour, aEndHour, this, aTheGroceryStoreSystem);
  }

  public boolean addShift(Shifts aShift)
  {
    boolean wasAdded = false;
    if (shifts.contains(aShift)) { return false; }
    Days existingDays = aShift.getDays();
    boolean isNewDays = existingDays != null && !this.equals(existingDays);
    if (isNewDays)
    {
      aShift.setDays(this);
    }
    else
    {
      shifts.add(aShift);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeShift(Shifts aShift)
  {
    boolean wasRemoved = false;
    //Unable to remove aShift, as it must always have a days
    if (!this.equals(aShift.getDays()))
    {
      shifts.remove(aShift);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addShiftAt(Shifts aShift, int index)
  {  
    boolean wasAdded = false;
    if(addShift(aShift))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfShifts()) { index = numberOfShifts() - 1; }
      shifts.remove(aShift);
      shifts.add(index, aShift);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveShiftAt(Shifts aShift, int index)
  {
    boolean wasAdded = false;
    if(shifts.contains(aShift))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfShifts()) { index = numberOfShifts() - 1; }
      shifts.remove(aShift);
      shifts.add(index, aShift);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addShiftAt(aShift, index);
    }
    return wasAdded;
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
      existingTheGroceryStoreSystem.removeDay(this);
    }
    theGroceryStoreSystem.addDay(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    StoreInfo placeholderStoreInfo = storeInfo;
    this.storeInfo = null;
    if(placeholderStoreInfo != null)
    {
      placeholderStoreInfo.removeDay(this);
    }
    for(int i=shifts.size(); i > 0; i--)
    {
      Shifts aShift = shifts.get(i - 1);
      aShift.delete();
    }
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
            "  " + "days" + "=" + (getDays() != null ? !getDays().equals(this)  ? getDays().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "storeStartHour" + "=" + (getStoreStartHour() != null ? !getStoreStartHour().equals(this)  ? getStoreStartHour().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "storeEndHour" + "=" + (getStoreEndHour() != null ? !getStoreEndHour().equals(this)  ? getStoreEndHour().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "storeInfo = "+(getStoreInfo()!=null?Integer.toHexString(System.identityHashCode(getStoreInfo())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "theGroceryStoreSystem = "+(getTheGroceryStoreSystem()!=null?Integer.toHexString(System.identityHashCode(getTheGroceryStoreSystem())):"null");
  }
}