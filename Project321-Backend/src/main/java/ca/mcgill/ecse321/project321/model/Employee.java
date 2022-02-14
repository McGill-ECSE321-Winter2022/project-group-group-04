/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.project321.model;
import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

// line 24 "../../../../../../model.ump"
// line 149 "../../../../../../model.ump"
@Entity
public class Employee extends User
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum EmployeeStatus { Sick, Inactive, Active }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Employee Attributes
  private EmployeeStatus status;

  //Employee Associations
  private List<Shift> shifts;
  private TheGroceryStoreSystem theGroceryStoreSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Employee(String aEmail, String aName, String aPassword, EmployeeStatus aStatus, TheGroceryStoreSystem aTheGroceryStoreSystem)
  {
    super(aEmail, aName, aPassword);
    status = aStatus;
    shifts = new ArrayList<Shift>();
    boolean didAddTheGroceryStoreSystem = setTheGroceryStoreSystem(aTheGroceryStoreSystem);
    if (!didAddTheGroceryStoreSystem)
    {
      throw new RuntimeException("Unable to create employee due to theGroceryStoreSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStatus(EmployeeStatus aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public EmployeeStatus getStatus()
  {
    return status;
  }
  /* Code from template association_GetMany */
  public Shift getShift(int index)
  {
    Shift aShift = shifts.get(index);
    return aShift;
  }

  @OneToMany(cascade = {CascadeType.ALL})
  public List<Shift> getShifts()
  {
    List<Shift> newShifts = Collections.unmodifiableList(shifts);
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

  public int indexOfShift(Shift aShift)
  {
    int index = shifts.indexOf(aShift);
    return index;
  }
  /* Code from template association_GetOne */
  @ManyToOne(cascade = {CascadeType.ALL})
  public TheGroceryStoreSystem getTheGroceryStoreSystem()
  {
    return theGroceryStoreSystem;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfShifts()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addShift(Shift aShift)
  {
    boolean wasAdded = false;
    if (shifts.contains(aShift)) { return false; }
    shifts.add(aShift);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeShift(Shift aShift)
  {
    boolean wasRemoved = false;
    if (shifts.contains(aShift))
    {
      shifts.remove(aShift);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addShiftAt(Shift aShift, int index)
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

  public boolean addOrMoveShiftAt(Shift aShift, int index)
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
      existingTheGroceryStoreSystem.removeEmployee(this);
    }
    theGroceryStoreSystem.addEmployee(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    shifts.clear();
    TheGroceryStoreSystem placeholderTheGroceryStoreSystem = theGroceryStoreSystem;
    this.theGroceryStoreSystem = null;
    if(placeholderTheGroceryStoreSystem != null)
    {
      placeholderTheGroceryStoreSystem.removeEmployee(this);
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "theGroceryStoreSystem = "+(getTheGroceryStoreSystem()!=null?Integer.toHexString(System.identityHashCode(getTheGroceryStoreSystem())):"null");
  }

  @Id
  public String getID() {
    return super.getEmail();
  }
}