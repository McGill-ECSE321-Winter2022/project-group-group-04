/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/



// line 11 "model.ump"
// line 122 "model.ump"
public class Customer extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Attributes
  private String phone;

  //Customer Associations
  private Address address;
  private Cart cart;
  private TheGroceryStoreSystem theGroceryStoreSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer(String aEmail, String aName, String aPassword, String aPhone, Address aAddress, Cart aCart, TheGroceryStoreSystem aTheGroceryStoreSystem)
  {
    super(aEmail, aName, aPassword);
    phone = aPhone;
    if (!setAddress(aAddress))
    {
      throw new RuntimeException("Unable to create Customer due to aAddress. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (aCart == null || aCart.getCustomer() != null)
    {
      throw new RuntimeException("Unable to create Customer due to aCart. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    cart = aCart;
    boolean didAddTheGroceryStoreSystem = setTheGroceryStoreSystem(aTheGroceryStoreSystem);
    if (!didAddTheGroceryStoreSystem)
    {
      throw new RuntimeException("Unable to create customer due to theGroceryStoreSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Customer(String aEmail, String aName, String aPassword, String aPhone, Address aAddress, ShoppingType aTypeForCart, TimeSlot aTimeSlotForCart, TheGroceryStoreSystem aTheGroceryStoreSystem)
  {
    super(aEmail, aName, aPassword);
    phone = aPhone;
    boolean didAddAddress = setAddress(aAddress);
    if (!didAddAddress)
    {
      throw new RuntimeException("Unable to create Customer due to address. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    cart = new Cart(aTypeForCart, this, aTimeSlotForCart);
    boolean didAddTheGroceryStoreSystem = setTheGroceryStoreSystem(aTheGroceryStoreSystem);
    if (!didAddTheGroceryStoreSystem)
    {
      throw new RuntimeException("Unable to create customer due to theGroceryStoreSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
  public Address getAddress()
  {
    return address;
  }
  /* Code from template association_GetOne */
  public Cart getCart()
  {
    return cart;
  }
  /* Code from template association_GetOne */
  public TheGroceryStoreSystem getTheGroceryStoreSystem()
  {
    return theGroceryStoreSystem;
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
      existingTheGroceryStoreSystem.removeCustomer(this);
    }
    theGroceryStoreSystem.addCustomer(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    address = null;
    Cart existingCart = cart;
    cart = null;
    if (existingCart != null)
    {
      existingCart.delete();
    }
    TheGroceryStoreSystem placeholderTheGroceryStoreSystem = theGroceryStoreSystem;
    this.theGroceryStoreSystem = null;
    if(placeholderTheGroceryStoreSystem != null)
    {
      placeholderTheGroceryStoreSystem.removeCustomer(this);
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "phone" + ":" + getPhone()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "address = "+(getAddress()!=null?Integer.toHexString(System.identityHashCode(getAddress())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "cart = "+(getCart()!=null?Integer.toHexString(System.identityHashCode(getCart())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "theGroceryStoreSystem = "+(getTheGroceryStoreSystem()!=null?Integer.toHexString(System.identityHashCode(getTheGroceryStoreSystem())):"null");
  }
}