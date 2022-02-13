/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/



// line 18 "model.ump"
// line 128 "model.ump"
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

  public StoreOwner(String aEmail, String aName, String aPassword, String aTelephoneForTheGroceryStoreSystem, String aEmailForTheGroceryStoreSystem, Address aAddressForTheGroceryStoreSystem)
  {
    super(aEmail, aName, aPassword);
    theGroceryStoreSystem = new TheGroceryStoreSystem(aTelephoneForTheGroceryStoreSystem, aEmailForTheGroceryStoreSystem, this, aAddressForTheGroceryStoreSystem);
  }

  //------------------------
  // INTERFACE
  //------------------------
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
    super.delete();
  }

}