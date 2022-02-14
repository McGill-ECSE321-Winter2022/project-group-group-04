/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.project321.model;

// line 93 "../../../../../../model.ump"
// line 187 "../../../../../../model.ump"
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

  //Address Associations
  private TheGroceryStoreSystem theGroceryStoreSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Address(String aTown, String aStreet, String aPostalCode, int aUnit, TheGroceryStoreSystem aTheGroceryStoreSystem)
  {
    town = aTown;
    street = aStreet;
    postalCode = aPostalCode;
    unit = aUnit;
    if (aTheGroceryStoreSystem == null || aTheGroceryStoreSystem.getAddress() != null)
    {
      throw new RuntimeException("Unable to create Address due to aTheGroceryStoreSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    theGroceryStoreSystem = aTheGroceryStoreSystem;
  }

  public Address(String aTown, String aStreet, String aPostalCode, int aUnit, String aTelephoneForTheGroceryStoreSystem, String aEmailForTheGroceryStoreSystem, String aGrocerystoreIDForTheGroceryStoreSystem, StoreOwner aStoreOwnerForTheGroceryStoreSystem)
  {
    town = aTown;
    street = aStreet;
    postalCode = aPostalCode;
    unit = aUnit;
    theGroceryStoreSystem = new TheGroceryStoreSystem(aTelephoneForTheGroceryStoreSystem, aEmailForTheGroceryStoreSystem, aGrocerystoreIDForTheGroceryStoreSystem, aStoreOwnerForTheGroceryStoreSystem, this);
  }

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
  /* Code from template association_GetOne */
  public TheGroceryStoreSystem getTheGroceryStoreSystem()
  {
    return theGroceryStoreSystem;
  }

  public void delete()
  {
    TheGroceryStoreSystem existingTheGroceryStoreSystem = theGroceryStoreSystem;
    theGroceryStoreSystem = null;
    if (existingTheGroceryStoreSystem != null)
    {
      existingTheGroceryStoreSystem.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "town" + ":" + getTown()+ "," +
            "street" + ":" + getStreet()+ "," +
            "postalCode" + ":" + getPostalCode()+ "," +
            "unit" + ":" + getUnit()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "theGroceryStoreSystem = "+(getTheGroceryStoreSystem()!=null?Integer.toHexString(System.identityHashCode(getTheGroceryStoreSystem())):"null");
  }
}