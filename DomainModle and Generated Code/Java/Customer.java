/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/



// line 9 "model.ump"
// line 126 "model.ump"
public class Customer extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Attributes
  private String phone;

  //Customer Associations
  private Cart cart;
  private Address address;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer(String aEmail, String aName, String aPassword, TheGroceryStoreSystem aTheGroceryStoreSystem, String aPhone, Cart aCart, Address aAddress)
  {
    super(aEmail, aName, aPassword, aTheGroceryStoreSystem);
    phone = aPhone;
    if (aCart == null || aCart.getCustomer() != null)
    {
      throw new RuntimeException("Unable to create Customer due to aCart. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    cart = aCart;
    boolean didAddAddress = setAddress(aAddress);
    if (!didAddAddress)
    {
      throw new RuntimeException("Unable to create customer due to address. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Customer(String aEmail, String aName, String aPassword, TheGroceryStoreSystem aTheGroceryStoreSystem, String aPhone, int aCartIDForCart, ShoppingType aTypeForCart, TimeSlot aTimeSlotForCart, Address aAddress)
  {
    super(aEmail, aName, aPassword, aTheGroceryStoreSystem);
    phone = aPhone;
    cart = new Cart(aCartIDForCart, aTypeForCart, this, aTimeSlotForCart);
    boolean didAddAddress = setAddress(aAddress);
    if (!didAddAddress)
    {
      throw new RuntimeException("Unable to create customer due to address. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
  public Cart getCart()
  {
    return cart;
  }
  /* Code from template association_GetOne */
  public Address getAddress()
  {
    return address;
  }
  /* Code from template association_SetOneToMany */
  public boolean setAddress(Address aAddress)
  {
    boolean wasSet = false;
    if (aAddress == null)
    {
      return wasSet;
    }

    Address existingAddress = address;
    address = aAddress;
    if (existingAddress != null && !existingAddress.equals(aAddress))
    {
      existingAddress.removeCustomer(this);
    }
    address.addCustomer(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Cart existingCart = cart;
    cart = null;
    if (existingCart != null)
    {
      existingCart.delete();
    }
    Address placeholderAddress = address;
    this.address = null;
    if(placeholderAddress != null)
    {
      placeholderAddress.removeCustomer(this);
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "phone" + ":" + getPhone()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "cart = "+(getCart()!=null?Integer.toHexString(System.identityHashCode(getCart())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "address = "+(getAddress()!=null?Integer.toHexString(System.identityHashCode(getAddress())):"null");
  }
}