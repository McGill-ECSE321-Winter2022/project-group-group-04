/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;
import java.sql.Date;

// line 31 "model.ump"
// line 140 "model.ump"
public class Cart
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextCartID = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Cart Attributes
  private ShoppingType type;

  //Autounique Attributes
  private int cartID;

  //Cart Associations
  private Customer customer;
  private List<CartItem> cartItems;
  private TimeSlot timeSlot;
  private List<Order> orders;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Cart(ShoppingType aType, Customer aCustomer, TimeSlot aTimeSlot)
  {
    type = aType;
    cartID = nextCartID++;
    if (aCustomer == null || aCustomer.getCart() != null)
    {
      throw new RuntimeException("Unable to create Cart due to aCustomer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    customer = aCustomer;
    cartItems = new ArrayList<CartItem>();
    if (!setTimeSlot(aTimeSlot))
    {
      throw new RuntimeException("Unable to create Cart due to aTimeSlot. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    orders = new ArrayList<Order>();
  }

  public Cart(ShoppingType aType, String aEmailForCustomer, String aNameForCustomer, String aPasswordForCustomer, String aPhoneForCustomer, Address aAddressForCustomer, TheGroceryStoreSystem aTheGroceryStoreSystemForCustomer, TimeSlot aTimeSlot)
  {
    type = aType;
    cartID = nextCartID++;
    customer = new Customer(aEmailForCustomer, aNameForCustomer, aPasswordForCustomer, aPhoneForCustomer, aAddressForCustomer, this, aTheGroceryStoreSystemForCustomer);
    cartItems = new ArrayList<CartItem>();
    boolean didAddTimeSlot = setTimeSlot(aTimeSlot);
    if (!didAddTimeSlot)
    {
      throw new RuntimeException("Unable to create cart due to timeSlot. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    orders = new ArrayList<Order>();
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

  public ShoppingType getType()
  {
    return type;
  }

  public int getCartID()
  {
    return cartID;
  }
  /* Code from template association_GetOne */
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

  public List<CartItem> getCartItems()
  {
    List<CartItem> newCartItems = Collections.unmodifiableList(cartItems);
    return newCartItems;
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
  public TimeSlot getTimeSlot()
  {
    return timeSlot;
  }
  /* Code from template association_GetMany */
  public Order getOrder(int index)
  {
    Order aOrder = orders.get(index);
    return aOrder;
  }

  public List<Order> getOrders()
  {
    List<Order> newOrders = Collections.unmodifiableList(orders);
    return newOrders;
  }

  public int numberOfOrders()
  {
    int number = orders.size();
    return number;
  }

  public boolean hasOrders()
  {
    boolean has = orders.size() > 0;
    return has;
  }

  public int indexOfOrder(Order aOrder)
  {
    int index = orders.indexOf(aOrder);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCartItems()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addCartItem(CartItem aCartItem)
  {
    boolean wasAdded = false;
    if (cartItems.contains(aCartItem)) { return false; }
    cartItems.add(aCartItem);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCartItem(CartItem aCartItem)
  {
    boolean wasRemoved = false;
    if (cartItems.contains(aCartItem))
    {
      cartItems.remove(aCartItem);
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
  /* Code from template association_SetUnidirectionalOne */
  public boolean setTimeSlot(TimeSlot aNewTimeSlot)
  {
    boolean wasSet = false;
    if (aNewTimeSlot != null)
    {
      timeSlot = aNewTimeSlot;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOrders()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Order addOrder(boolean aCompleted, Date aOrderDate, int aTotal, String aPayment)
  {
    return new Order(aCompleted, aOrderDate, aTotal, aPayment, this);
  }

  public boolean addOrder(Order aOrder)
  {
    boolean wasAdded = false;
    if (orders.contains(aOrder)) { return false; }
    Cart existingCart = aOrder.getCart();
    boolean isNewCart = existingCart != null && !this.equals(existingCart);
    if (isNewCart)
    {
      aOrder.setCart(this);
    }
    else
    {
      orders.add(aOrder);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOrder(Order aOrder)
  {
    boolean wasRemoved = false;
    //Unable to remove aOrder, as it must always have a cart
    if (!this.equals(aOrder.getCart()))
    {
      orders.remove(aOrder);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOrderAt(Order aOrder, int index)
  {  
    boolean wasAdded = false;
    if(addOrder(aOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrders()) { index = numberOfOrders() - 1; }
      orders.remove(aOrder);
      orders.add(index, aOrder);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOrderAt(Order aOrder, int index)
  {
    boolean wasAdded = false;
    if(orders.contains(aOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrders()) { index = numberOfOrders() - 1; }
      orders.remove(aOrder);
      orders.add(index, aOrder);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOrderAt(aOrder, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Customer existingCustomer = customer;
    customer = null;
    if (existingCustomer != null)
    {
      existingCustomer.delete();
    }
    cartItems.clear();
    timeSlot = null;
    while (orders.size() > 0)
    {
      Order aOrder = orders.get(orders.size() - 1);
      aOrder.delete();
      orders.remove(aOrder);
    }
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "cartID" + ":" + getCartID()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "type" + "=" + (getType() != null ? !getType().equals(this)  ? getType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "timeSlot = "+(getTimeSlot()!=null?Integer.toHexString(System.identityHashCode(getTimeSlot())):"null");
  }
}