/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/



// line 15 "model.ump"
// line 132 "model.ump"
public class StoreOwner extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //StoreOwner Associations
  private StoreInfo storeInfo;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public StoreOwner(String aEmail, String aName, String aPassword, TheGroceryStoreSystem aTheGroceryStoreSystem, StoreInfo aStoreInfo)
  {
    super(aEmail, aName, aPassword, aTheGroceryStoreSystem);
    if (aStoreInfo == null || aStoreInfo.getStoreOwner() != null)
    {
      throw new RuntimeException("Unable to create StoreOwner due to aStoreInfo. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    storeInfo = aStoreInfo;
  }

  public StoreOwner(String aEmail, String aName, String aPassword, TheGroceryStoreSystem aTheGroceryStoreSystem, String aTelephoneForStoreInfo, String aEmailForStoreInfo, Address aAddressForStoreInfo, TheGroceryStoreSystem aTheGroceryStoreSystemForStoreInfo)
  {
    super(aEmail, aName, aPassword, aTheGroceryStoreSystem);
    storeInfo = new StoreInfo(aTelephoneForStoreInfo, aEmailForStoreInfo, this, aAddressForStoreInfo, aTheGroceryStoreSystemForStoreInfo);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public StoreInfo getStoreInfo()
  {
    return storeInfo;
  }

  public void delete()
  {
    StoreInfo existingStoreInfo = storeInfo;
    storeInfo = null;
    if (existingStoreInfo != null)
    {
      existingStoreInfo.delete();
    }
    super.delete();
  }

}