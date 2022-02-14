/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.project321.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

// line 19 "../../../../../../model.ump"
// line 144 "../../../../../../model.ump"
@Entity
public class StoreOwner extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //StoreOwner Associations
  private TheGroceryStoreSystem theGroceryStoreSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public StoreOwner(String aEmail, String aName, String aPassword, TheGroceryStoreSystem aTheGroceryStoreSystem)
  {
    super(aEmail, aName, aPassword);
    if (aTheGroceryStoreSystem == null || aTheGroceryStoreSystem.getStoreOwner() != null)
    {
      throw new RuntimeException("Unable to create StoreOwner due to aTheGroceryStoreSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    theGroceryStoreSystem = aTheGroceryStoreSystem;
  }

  public StoreOwner(String aEmail, String aName, String aPassword, String aTelephoneForTheGroceryStoreSystem, String aEmailForTheGroceryStoreSystem, String aGrocerystoreIDForTheGroceryStoreSystem, Address aAddressForTheGroceryStoreSystem)
  {
    super(aEmail, aName, aPassword);
    theGroceryStoreSystem = new TheGroceryStoreSystem(aTelephoneForTheGroceryStoreSystem, aEmailForTheGroceryStoreSystem, aGrocerystoreIDForTheGroceryStoreSystem, this, aAddressForTheGroceryStoreSystem);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  @OneToOne(cascade = {CascadeType.ALL})
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
    super.delete();
  }

  @Id
  public String getID() {
    return super.getEmail();
  }

}