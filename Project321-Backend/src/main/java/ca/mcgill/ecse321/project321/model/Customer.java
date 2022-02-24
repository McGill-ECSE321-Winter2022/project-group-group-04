/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.project321.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

// line 14 "../../../../../../model.ump"
// line 158 "../../../../../../model.ump"
@Entity
public class Customer extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Attributes
  private String phone;

  //Customer Associations
  private Address address;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer(String aEmail, String aName, String aPassword, String aPhone, Address aAddress)
  {
    super(aEmail, aName, aPassword);
    phone = aPhone;
    if (!setAddress(aAddress))
    {
      throw new RuntimeException("Unable to create Customer due to aAddress. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPhone(String aPhone)
  {
    boolean wasSet = false;
    phone = aPhone;
    wasSet = true;
    return wasSet;
  }

  public String getPhone()
  {
    return phone;
  }
  /* Code from template association_GetOne */
  @ManyToOne(cascade = CascadeType.MERGE)
  public Address getAddress()
  {
    return address;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setAddress(Address aNewAddress)
  {
    boolean wasSet = false;
    if (aNewAddress != null)
    {
      address = aNewAddress;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    address = null;
    super.delete();
  }

  // line 25 "../../../../../../model.ump"
   public  Customer(){
    super();
  }


  public String toString()
  {
    return super.toString() + "["+
            "phone" + ":" + getPhone()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "address = "+(getAddress()!=null?Integer.toHexString(System.identityHashCode(getAddress())):"null");
  }
}