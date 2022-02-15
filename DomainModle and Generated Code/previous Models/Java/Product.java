/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/



// line 50 "model.ump"
// line 154 "model.ump"
public class Product
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextProductID = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Product Attributes
  private String isAvailableOnline;
  private int price;
  private int stock;

  //Autounique Attributes
  private int productID;

  //Product Associations
  private TheGroceryStoreSystem theGroceryStoreSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Product(String aIsAvailableOnline, int aPrice, int aStock, TheGroceryStoreSystem aTheGroceryStoreSystem)
  {
    isAvailableOnline = aIsAvailableOnline;
    price = aPrice;
    stock = aStock;
    productID = nextProductID++;
    boolean didAddTheGroceryStoreSystem = setTheGroceryStoreSystem(aTheGroceryStoreSystem);
    if (!didAddTheGroceryStoreSystem)
    {
      throw new RuntimeException("Unable to create product due to theGroceryStoreSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsAvailableOnline(String aIsAvailableOnline)
  {
    boolean wasSet = false;
    isAvailableOnline = aIsAvailableOnline;
    wasSet = true;
    return wasSet;
  }

  public boolean setPrice(int aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public boolean setStock(int aStock)
  {
    boolean wasSet = false;
    stock = aStock;
    wasSet = true;
    return wasSet;
  }

  public String getIsAvailableOnline()
  {
    return isAvailableOnline;
  }

  public int getPrice()
  {
    return price;
  }

  public int getStock()
  {
    return stock;
  }

  public int getProductID()
  {
    return productID;
  }
  /* Code from template association_GetOne */
  public TheGroceryStoreSystem getTheGroceryStoreSystem()
  {
    return theGroceryStoreSystem;
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
      existingTheGroceryStoreSystem.removeProduct(this);
    }
    theGroceryStoreSystem.addProduct(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    TheGroceryStoreSystem placeholderTheGroceryStoreSystem = theGroceryStoreSystem;
    this.theGroceryStoreSystem = null;
    if(placeholderTheGroceryStoreSystem != null)
    {
      placeholderTheGroceryStoreSystem.removeProduct(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "productID" + ":" + getProductID()+ "," +
            "isAvailableOnline" + ":" + getIsAvailableOnline()+ "," +
            "price" + ":" + getPrice()+ "," +
            "stock" + ":" + getStock()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "theGroceryStoreSystem = "+(getTheGroceryStoreSystem()!=null?Integer.toHexString(System.identityHashCode(getTheGroceryStoreSystem())):"null");
  }
}