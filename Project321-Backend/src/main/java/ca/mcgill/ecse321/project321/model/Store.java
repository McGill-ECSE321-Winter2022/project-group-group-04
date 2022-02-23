/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.project321.model;
import java.sql.Time;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

// line 103 "../../../../../../model.ump"
// line 186 "../../../../../../model.ump"
@Entity
public class Store
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Store Attributes
  private String telephone;
  private String email;
  private Time openingHour;
  private Time closingHour;
  private int storeId;

  //Store Associations
  private StoreOwner storeOwner;
  private Address address;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Store(String aTelephone, String aEmail, Time aOpeningHour, Time aClosingHour, StoreOwner aStoreOwner, Address aAddress)
  {
    telephone = aTelephone;
    email = aEmail;
    openingHour = aOpeningHour;
    closingHour = aClosingHour;
    storeId = 0;
    if (!setStoreOwner(aStoreOwner))
    {
      throw new RuntimeException("Unable to create Store due to aStoreOwner. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setAddress(aAddress))
    {
      throw new RuntimeException("Unable to create Store due to aAddress. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Store() {}

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

  public boolean setOpeningHour(Time aOpeningHour)
  {
    boolean wasSet = false;
    openingHour = aOpeningHour;
    wasSet = true;
    return wasSet;
  }

  public boolean setClosingHour(Time aClosingHour)
  {
    boolean wasSet = false;
    closingHour = aClosingHour;
    wasSet = true;
    return wasSet;
  }

  public boolean setStoreId(int aStoreId)
  {
    boolean wasSet = false;
    storeId = aStoreId;
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

  public Time getOpeningHour()
  {
    return openingHour;
  }

  public Time getClosingHour()
  {
    return closingHour;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public int getStoreId()
  {
    return storeId;
  }
  /* Code from template association_GetOne */
  @OneToOne(cascade = CascadeType.MERGE)
  public StoreOwner getStoreOwner()
  {
    return storeOwner;
  }
  /* Code from template association_GetOne */
  @OneToOne(cascade = CascadeType.MERGE)
  public Address getAddress()
  {
    return address;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setStoreOwner(StoreOwner aNewStoreOwner)
  {
    boolean wasSet = false;
    if (aNewStoreOwner != null)
    {
      storeOwner = aNewStoreOwner;
      wasSet = true;
    }
    return wasSet;
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
    storeOwner = null;
    address = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "telephone" + ":" + getTelephone()+ "," +
            "email" + ":" + getEmail()+ "," +
            "storeId" + ":" + getStoreId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "openingHour" + "=" + (getOpeningHour() != null ? !getOpeningHour().equals(this)  ? getOpeningHour().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "closingHour" + "=" + (getClosingHour() != null ? !getClosingHour().equals(this)  ? getClosingHour().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "storeOwner = "+(getStoreOwner()!=null?Integer.toHexString(System.identityHashCode(getStoreOwner())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "address = "+(getAddress()!=null?Integer.toHexString(System.identityHashCode(getAddress())):"null");
  }
}