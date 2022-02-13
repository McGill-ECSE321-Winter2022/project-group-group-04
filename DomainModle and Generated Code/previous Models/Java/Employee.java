/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/



// line 21 "model.ump"
// line 138 "model.ump"
public class Employee extends User
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum EmployeeStatus { Sick, Inactive, Working }
  public enum Shift { Daytime, Night }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Employee Attributes
  private EmployeeStatus status;
  private Shift shift;

  //Employee Associations
  private Shifts shifts;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Employee(String aEmail, String aName, String aPassword, TheGroceryStoreSystem aTheGroceryStoreSystem, EmployeeStatus aStatus, Shift aShift, Shifts aShifts)
  {
    super(aEmail, aName, aPassword, aTheGroceryStoreSystem);
    status = aStatus;
    shift = aShift;
    if (!setShifts(aShifts))
    {
      throw new RuntimeException("Unable to create Employee due to aShifts. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  public boolean setShift(Shift aShift)
  {
    boolean wasSet = false;
    shift = aShift;
    wasSet = true;
    return wasSet;
  }

  public EmployeeStatus getStatus()
  {
    return status;
  }

  public Shift getShift()
  {
    return shift;
  }
  /* Code from template association_GetOne */
  public Shifts getShifts()
  {
    return shifts;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setShifts(Shifts aNewShifts)
  {
    boolean wasSet = false;
    if (aNewShifts != null)
    {
      shifts = aNewShifts;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    shifts = null;
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "shift" + "=" + (getShift() != null ? !getShift().equals(this)  ? getShift().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "shifts = "+(getShifts()!=null?Integer.toHexString(System.identityHashCode(getShifts())):"null");
  }
}