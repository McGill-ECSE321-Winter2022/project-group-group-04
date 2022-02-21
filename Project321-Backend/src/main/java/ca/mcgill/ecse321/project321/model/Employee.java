/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.project321.model;

import javax.persistence.Entity;

// line 24 "../../../../../../model.ump"
// line 138 "../../../../../../model.ump"
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

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Employee(String aEmail, String aName, String aPassword, EmployeeStatus aStatus)
  {
    super(aEmail, aName, aPassword);
    status = aStatus;
  }

  public Employee() {}

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

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator");
  }
}