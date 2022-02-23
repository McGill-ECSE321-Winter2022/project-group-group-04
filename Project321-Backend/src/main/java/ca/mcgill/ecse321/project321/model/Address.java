/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.project321.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// line 86 "../../../../../../model.ump"
// line 175 "../../../../../../model.ump"
@Entity
public class Address
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Address Attributes
  private String town;
  private String street;
  private String postalCode;
  private int unit;
  private int addressId;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Address(String aTown, String aStreet, String aPostalCode, int aUnit)
  {
    town = aTown;
    street = aStreet;
    postalCode = aPostalCode;
    unit = aUnit;

    addressId = 0;
  }

  public Address() {}

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTown(String aTown)
  {
    boolean wasSet = false;
    town = aTown;
    wasSet = true;
    return wasSet;
  }

  public boolean setStreet(String aStreet)
  {
    boolean wasSet = false;
    street = aStreet;
    wasSet = true;
    return wasSet;
  }

  public boolean setPostalCode(String aPostalCode)
  {
    boolean wasSet = false;
    postalCode = aPostalCode;
    wasSet = true;
    return wasSet;
  }

  public boolean setUnit(int aUnit)
  {
    boolean wasSet = false;
    unit = aUnit;
    wasSet = true;
    return wasSet;
  }

  public boolean setAddressId(int aAddressId)
  {
    boolean wasSet = false;
    addressId = aAddressId;
    wasSet = true;
    return wasSet;
  }

  public String getTown()
  {
    return town;
  }

  public String getStreet()
  {
    return street;
  }

  public String getPostalCode()
  {
    return postalCode;
  }

  public int getUnit()
  {
    return unit;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public int getAddressId()
  {
    return addressId;
  }

  public void delete()
  {}

  public String toString()
  {
    return super.toString() + "["+
            "town" + ":" + getTown()+ "," +
            "street" + ":" + getStreet()+ "," +
            "postalCode" + ":" + getPostalCode()+ "," +
            "unit" + ":" + getUnit()+ "," +
            "addressId" + ":" + getAddressId()+ "]";
  }
}