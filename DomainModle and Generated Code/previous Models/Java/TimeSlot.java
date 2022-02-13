/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.sql.Time;
import java.util.*;

// line 70 "model.ump"
// line 175 "model.ump"
public class TimeSlot
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum ShoppingType { Delivery, Pickup }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TimeSlot Attributes
  private Time startTime;
  private Time endTime;
  private int maxOrderPerSlot;
  private ShoppingType shoppingType;

  //TimeSlot Associations
  private List<Cart> carts;
  private TheGroceryStoreSystem theGroceryStoreSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TimeSlot(Time aStartTime, Time aEndTime, int aMaxOrderPerSlot, ShoppingType aShoppingType, TheGroceryStoreSystem aTheGroceryStoreSystem)
  {
    startTime = aStartTime;
    endTime = aEndTime;
    maxOrderPerSlot = aMaxOrderPerSlot;
    shoppingType = aShoppingType;
    carts = new ArrayList<Cart>();
    boolean didAddTheGroceryStoreSystem = setTheGroceryStoreSystem(aTheGroceryStoreSystem);
    if (!didAddTheGroceryStoreSystem)
    {
      throw new RuntimeException("Unable to create timeSlot due to theGroceryStoreSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStartTime(Time aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(Time aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setMaxOrderPerSlot(int aMaxOrderPerSlot)
  {
    boolean wasSet = false;
    maxOrderPerSlot = aMaxOrderPerSlot;
    wasSet = true;
    return wasSet;
  }

  public boolean setShoppingType(ShoppingType aShoppingType)
  {
    boolean wasSet = false;
    shoppingType = aShoppingType;
    wasSet = true;
    return wasSet;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }

  public int getMaxOrderPerSlot()
  {
    return maxOrderPerSlot;
  }

  public ShoppingType getShoppingType()
  {
    return shoppingType;
  }
  /* Code from template association_GetMany */
  public Cart getCart(int index)
  {
    Cart aCart = carts.get(index);
    return aCart;
  }

  public List<Cart> getCarts()
  {
    List<Cart> newCarts = Collections.unmodifiableList(carts);
    return newCarts;
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
  /* Code from template association_GetOne */
  public TheGroceryStoreSystem getTheGroceryStoreSystem()
  {
    return theGroceryStoreSystem;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCarts()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Cart addCart(int aCartID, ShoppingType aType, Customer aCustomer)
  {
    return new Cart(aCartID, aType, aCustomer, this);
  }

  public boolean addCart(Cart aCart)
  {
    boolean wasAdded = false;
    if (carts.contains(aCart)) { return false; }
    TimeSlot existingTimeSlot = aCart.getTimeSlot();
    boolean isNewTimeSlot = existingTimeSlot != null && !this.equals(existingTimeSlot);
    if (isNewTimeSlot)
    {
      aCart.setTimeSlot(this);
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
    //Unable to remove aCart, as it must always have a timeSlot
    if (!this.equals(aCart.getTimeSlot()))
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
      existingTheGroceryStoreSystem.removeTimeSlot(this);
    }
    theGroceryStoreSystem.addTimeSlot(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=carts.size(); i > 0; i--)
    {
      Cart aCart = carts.get(i - 1);
      aCart.delete();
    }
    TheGroceryStoreSystem placeholderTheGroceryStoreSystem = theGroceryStoreSystem;
    this.theGroceryStoreSystem = null;
    if(placeholderTheGroceryStoreSystem != null)
    {
      placeholderTheGroceryStoreSystem.removeTimeSlot(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "maxOrderPerSlot" + ":" + getMaxOrderPerSlot()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "shoppingType" + "=" + (getShoppingType() != null ? !getShoppingType().equals(this)  ? getShoppingType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "theGroceryStoreSystem = "+(getTheGroceryStoreSystem()!=null?Integer.toHexString(System.identityHashCode(getTheGroceryStoreSystem())):"null");
  }
}