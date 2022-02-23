/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.project321.model;
import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

// line 31 "../../../../../../model.ump"
// line 139 "../../../../../../model.ump"
@Entity
public class Cart
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum ShoppingType { Delivery, Pickup }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Cart Attributes
  private ShoppingType type;
  private int cartId;

  //Cart Associations
  private Customer customer;
  private List<CartItem> cartItems;
  private TimeSlot timeSlot;
  private Order order;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Cart(ShoppingType aType, Customer aCustomer)
  {
    type = aType;
    cartId = 0;
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create cart due to customer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    cartItems = new ArrayList<CartItem>();
  }

  public Cart() {
    cartItems = new ArrayList<CartItem>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setType(ShoppingType aType)
  {
    boolean wasSet = false;
    type = aType;
    wasSet = true;
    return wasSet;
  }

  public boolean setCartId(int aCartId)
  {
    boolean wasSet = false;
    cartId = aCartId;
    wasSet = true;
    return wasSet;
  }

  public ShoppingType getType()
  {
    return type;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public int getCartId()
  {
    return cartId;
  }
  /* Code from template association_GetOne */
  @ManyToOne()
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_GetMany */
  public CartItem getCartItem(int index)
  {
    CartItem aCartItem = cartItems.get(index);
    return aCartItem;
  }

  @OneToMany(cascade = CascadeType.ALL)
  public List<CartItem> getCartItems()
  {
    List<CartItem> newCartItems = Collections.unmodifiableList(cartItems);
    return newCartItems;
  }

  public boolean setCartItems(List<CartItem> cartItems) {
    this.cartItems = new ArrayList<CartItem>(cartItems);
    return true;
  }

  public int numberOfCartItems()
  {
    int number = cartItems.size();
    return number;
  }

  public boolean hasCartItems()
  {
    boolean has = cartItems.size() > 0;
    return has;
  }

  public int indexOfCartItem(CartItem aCartItem)
  {
    int index = cartItems.indexOf(aCartItem);
    return index;
  }
  /* Code from template association_GetOne */
  @ManyToOne(cascade = CascadeType.MERGE)
  public TimeSlot getTimeSlot()
  {
    return timeSlot;
  }

  public boolean hasTimeSlot()
  {
    boolean has = timeSlot != null;
    return has;
  }
  /* Code from template association_GetOne */
  @OneToOne(cascade = CascadeType.ALL)
  public Order getOrder()
  {
    return order;
  }

  public boolean hasOrder()
  {
    boolean has = order != null;
    return has;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCustomer(Customer aCustomer)
  {
    boolean wasSet = false;
    if (aCustomer == null)
    {
      return wasSet;
    }

    Customer existingCustomer = customer;
    customer = aCustomer;
    if (existingCustomer != null && !existingCustomer.equals(aCustomer))
    {
      existingCustomer.removeCart(this);
    }
    customer.addCart(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCartItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addCartItem(CartItem aCartItem)
  {
    boolean wasAdded = false;
    if (cartItems.contains(aCartItem)) { return false; }
    Cart existingCart = aCartItem.getCart();
    if (existingCart == null)
    {
      aCartItem.setCart(this);
    }
    else if (!this.equals(existingCart))
    {
      existingCart.removeCartItem(aCartItem);
      addCartItem(aCartItem);
    }
    else
    {
      cartItems.add(aCartItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCartItem(CartItem aCartItem)
  {
    boolean wasRemoved = false;
    if (cartItems.contains(aCartItem))
    {
      cartItems.remove(aCartItem);
      aCartItem.setCart(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCartItemAt(CartItem aCartItem, int index)
  {  
    boolean wasAdded = false;
    if(addCartItem(aCartItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCartItems()) { index = numberOfCartItems() - 1; }
      cartItems.remove(aCartItem);
      cartItems.add(index, aCartItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCartItemAt(CartItem aCartItem, int index)
  {
    boolean wasAdded = false;
    if(cartItems.contains(aCartItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCartItems()) { index = numberOfCartItems() - 1; }
      cartItems.remove(aCartItem);
      cartItems.add(index, aCartItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCartItemAt(aCartItem, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setTimeSlot(TimeSlot aNewTimeSlot)
  {
    boolean wasSet = false;
    timeSlot = aNewTimeSlot;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setOrder(Order aNewOrder)
  {
    boolean wasSet = false;
    if (order != null && !order.equals(aNewOrder) && equals(order.getCart()))
    {
      //Unable to setOrder, as existing order would become an orphan
      return wasSet;
    }

    order = aNewOrder;
    Cart anOldCart = aNewOrder != null ? aNewOrder.getCart() : null;

    if (!this.equals(anOldCart))
    {
      if (anOldCart != null)
      {
        anOldCart.order = null;
      }
      if (order != null)
      {
        order.setCart(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Customer placeholderCustomer = customer;
    this.customer = null;
    if(placeholderCustomer != null)
    {
      placeholderCustomer.removeCart(this);
    }
    while (cartItems.size() > 0)
    {
      CartItem aCartItem = cartItems.get(cartItems.size() - 1);
      aCartItem.delete();
      cartItems.remove(aCartItem);
    }
    
    timeSlot = null;
    Order existingOrder = order;
    order = null;
    if (existingOrder != null)
    {
      existingOrder.delete();
      existingOrder.setCart(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "cartId" + ":" + getCartId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "type" + "=" + (getType() != null ? !getType().equals(this)  ? getType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "timeSlot = "+(getTimeSlot()!=null?Integer.toHexString(System.identityHashCode(getTimeSlot())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "order = "+(getOrder()!=null?Integer.toHexString(System.identityHashCode(getOrder())):"null");
  }
}