/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.project321.model;
import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

// line 12 "../../../../../../model.ump"
// line 123 "../../../../../../model.ump"
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
  private List<Cart> carts;

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
    carts = new ArrayList<Cart>();
  }

  public Customer() {
    super();
    carts = new ArrayList<Cart>();
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
  @OneToOne(cascade = CascadeType.ALL)
  public Address getAddress()
  {
    return address;
  }
  /* Code from template association_GetMany */
  public Cart getCart(int index)
  {
    Cart aCart = carts.get(index);
    return aCart;
  }

  @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
  public List<Cart> getCarts()
  {
    List<Cart> newCarts = new ArrayList<Cart>(carts);
    return newCarts;
  }

  public boolean setCarts(List<Cart> carts) {
    this.carts = new ArrayList<Cart>(carts);
    return true;
  }

  public int numberOfCarts()
  {
    int number = carts.size();
    return number;
  }

  public boolean hasCarts()
  {
    boolean has = carts.size() > 0;
    return has;
  }

  public int indexOfCart(Cart aCart)
  {
    int index = carts.indexOf(aCart);
    return index;
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCarts()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Cart addCart(Cart.ShoppingType aType)
  {
    return new Cart(aType, this);
  }

  public boolean addCart(Cart aCart)
  {
    boolean wasAdded = false;
    if (carts.contains(aCart)) { return false; }
    Customer existingCustomer = aCart.getCustomer();
    boolean isNewCustomer = existingCustomer != null && !this.equals(existingCustomer);
    if (isNewCustomer)
    {
      aCart.setCustomer(this);
    }
    else
    {
      carts.add(aCart);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCart(Cart aCart)
  {
    boolean wasRemoved = false;
    //Unable to remove aCart, as it must always have a customer
    if (!this.equals(aCart.getCustomer()))
    {
      carts.remove(aCart);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCartAt(Cart aCart, int index)
  {  
    boolean wasAdded = false;
    if(addCart(aCart))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCarts()) { index = numberOfCarts() - 1; }
      carts.remove(aCart);
      carts.add(index, aCart);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCartAt(Cart aCart, int index)
  {
    boolean wasAdded = false;
    if(carts.contains(aCart))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCarts()) { index = numberOfCarts() - 1; }
      carts.remove(aCart);
      carts.add(index, aCart);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCartAt(aCart, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    address = null;
    while (carts.size() > 0)
    {
      Cart aCart = carts.get(carts.size() - 1);
      aCart.delete();
      carts.remove(aCart);
    }
    
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "phone" + ":" + getPhone()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "address = "+(getAddress()!=null?Integer.toHexString(System.identityHashCode(getAddress())):"null");
  }
}