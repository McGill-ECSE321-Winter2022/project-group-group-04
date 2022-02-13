/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;
import java.sql.Time;

// line 31 "model.ump"
// line 144 "model.ump"
public class StoreInfo
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //StoreInfo Attributes
  private String telephone;
  private String email;

  //StoreInfo Associations
  private StoreOwner storeOwner;
  private List<Days> days;
  private Address address;
  private TheGroceryStoreSystem theGroceryStoreSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public StoreInfo(String aTelephone, String aEmail, StoreOwner aStoreOwner, Address aAddress, TheGroceryStoreSystem aTheGroceryStoreSystem)
  {
    telephone = aTelephone;
    email = aEmail;
    if (aStoreOwner == null || aStoreOwner.getStoreInfo() != null)
    {
      throw new RuntimeException("Unable to create StoreInfo due to aStoreOwner. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    storeOwner = aStoreOwner;
    days = new ArrayList<Days>();
    if (aAddress == null || aAddress.getStoreInfo() != null)
    {
      throw new RuntimeException("Unable to create StoreInfo due to aAddress. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    address = aAddress;
    boolean didAddTheGroceryStoreSystem = setTheGroceryStoreSystem(aTheGroceryStoreSystem);
    if (!didAddTheGroceryStoreSystem)
    {
      throw new RuntimeException("Unable to create storeInfo due to theGroceryStoreSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public StoreInfo(String aTelephone, String aEmail, String aEmailForStoreOwner, String aNameForStoreOwner, String aPasswordForStoreOwner, TheGroceryStoreSystem aTheGroceryStoreSystemForStoreOwner, String aTownForAddress, String aStreetForAddress, String aPostalCodeForAddress, int aApartmentOrUnitForAddress, TheGroceryStoreSystem aTheGroceryStoreSystem)
  {
    telephone = aTelephone;
    email = aEmail;
    storeOwner = new StoreOwner(aEmailForStoreOwner, aNameForStoreOwner, aPasswordForStoreOwner, aTheGroceryStoreSystemForStoreOwner, this);
    days = new ArrayList<Days>();
    address = new Address(aTownForAddress, aStreetForAddress, aPostalCodeForAddress, aApartmentOrUnitForAddress, this);
    boolean didAddTheGroceryStoreSystem = setTheGroceryStoreSystem(aTheGroceryStoreSystem);
    if (!didAddTheGroceryStoreSystem)
    {
      throw new RuntimeException("Unable to create storeInfo due to theGroceryStoreSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTelephone(String aTelephone)
  {
    boolean wasSet = false;
    telephone = aTelephone;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public String getTelephone()
  {
    return telephone;
  }

  public String getEmail()
  {
    return email;
  }
  /* Code from template association_GetOne */
  public StoreOwner getStoreOwner()
  {
    return storeOwner;
  }
  /* Code from template association_GetMany */
  public Days getDay(int index)
  {
    Days aDay = days.get(index);
    return aDay;
  }

  public List<Days> getDays()
  {
    List<Days> newDays = Collections.unmodifiableList(days);
    return newDays;
  }

  public int numberOfDays()
  {
    int number = days.size();
    return number;
  }

  public boolean hasDays()
  {
    boolean has = days.size() > 0;
    return has;
  }

  public int indexOfDay(Days aDay)
  {
    int index = days.indexOf(aDay);
    return index;
  }
  /* Code from template association_GetOne */
  public Address getAddress()
  {
    return address;
  }
  /* Code from template association_GetOne */
  public TheGroceryStoreSystem getTheGroceryStoreSystem()
  {
    return theGroceryStoreSystem;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDays()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Days addDay(Days.WeekDays aDays, Time aStoreStartHour, Time aStoreEndHour, TheGroceryStoreSystem aTheGroceryStoreSystem)
  {
    return new Days(aDays, aStoreStartHour, aStoreEndHour, this, aTheGroceryStoreSystem);
  }

  public boolean addDay(Days aDay)
  {
    boolean wasAdded = false;
    if (days.contains(aDay)) { return false; }
    StoreInfo existingStoreInfo = aDay.getStoreInfo();
    boolean isNewStoreInfo = existingStoreInfo != null && !this.equals(existingStoreInfo);
    if (isNewStoreInfo)
    {
      aDay.setStoreInfo(this);
    }
    else
    {
      days.add(aDay);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeDay(Days aDay)
  {
    boolean wasRemoved = false;
    //Unable to remove aDay, as it must always have a storeInfo
    if (!this.equals(aDay.getStoreInfo()))
    {
      days.remove(aDay);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addDayAt(Days aDay, int index)
  {  
    boolean wasAdded = false;
    if(addDay(aDay))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDays()) { index = numberOfDays() - 1; }
      days.remove(aDay);
      days.add(index, aDay);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveDayAt(Days aDay, int index)
  {
    boolean wasAdded = false;
    if(days.contains(aDay))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDays()) { index = numberOfDays() - 1; }
      days.remove(aDay);
      days.add(index, aDay);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addDayAt(aDay, index);
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
      existingTheGroceryStoreSystem.removeStoreInfo(this);
    }
    theGroceryStoreSystem.addStoreInfo(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    StoreOwner existingStoreOwner = storeOwner;
    storeOwner = null;
    if (existingStoreOwner != null)
    {
      existingStoreOwner.delete();
    }
    for(int i=days.size(); i > 0; i--)
    {
      Days aDay = days.get(i - 1);
      aDay.delete();
    }
    Address existingAddress = address;
    address = null;
    if (existingAddress != null)
    {
      existingAddress.delete();
    }
    TheGroceryStoreSystem placeholderTheGroceryStoreSystem = theGroceryStoreSystem;
    this.theGroceryStoreSystem = null;
    if(placeholderTheGroceryStoreSystem != null)
    {
      placeholderTheGroceryStoreSystem.removeStoreInfo(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "telephone" + ":" + getTelephone()+ "," +
            "email" + ":" + getEmail()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "storeOwner = "+(getStoreOwner()!=null?Integer.toHexString(System.identityHashCode(getStoreOwner())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "address = "+(getAddress()!=null?Integer.toHexString(System.identityHashCode(getAddress())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "theGroceryStoreSystem = "+(getTheGroceryStoreSystem()!=null?Integer.toHexString(System.identityHashCode(getTheGroceryStoreSystem())):"null");
  }
}