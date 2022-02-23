/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.project321.model;
import java.sql.Date;
import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

// line 95 "../../../../../../model.ump"
// line 180 "../../../../../../model.ump"
@Entity
public class InStoreBill
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //InStoreBill Attributes
  private int total;
  private Date purchaseDate;
  private int inStoreBillId;

  //InStoreBill Associations
  private List<CartItem> cartItems;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public InStoreBill(int aTotal, Date aPurchaseDate)
  {
    total = aTotal;
    purchaseDate = aPurchaseDate;
    inStoreBillId = 0;
    cartItems = new ArrayList<CartItem>();
  }

  public InStoreBill() {
    cartItems = new ArrayList<CartItem>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTotal(int aTotal)
  {
    boolean wasSet = false;
    total = aTotal;
    wasSet = true;
    return wasSet;
  }

  public boolean setPurchaseDate(Date aPurchaseDate)
  {
    boolean wasSet = false;
    purchaseDate = aPurchaseDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setInStoreBillId(int aInStoreBillId)
  {
    boolean wasSet = false;
    inStoreBillId = aInStoreBillId;
    wasSet = true;
    return wasSet;
  }

  public int getTotal()
  {
    return total;
  }

  public Date getPurchaseDate()
  {
    return purchaseDate;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public int getInStoreBillId()
  {
    return inStoreBillId;
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
    InStoreBill existingInStoreBill = aCartItem.getInStoreBill();
    if (existingInStoreBill == null)
    {
      aCartItem.setInStoreBill(this);
    }
    else if (!this.equals(existingInStoreBill))
    {
      existingInStoreBill.removeCartItem(aCartItem);
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
      aCartItem.setInStoreBill(null);
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

  public void delete()
  {
    while (cartItems.size() > 0)
    {
      CartItem aCartItem = cartItems.get(cartItems.size() - 1);
      aCartItem.delete();
      cartItems.remove(aCartItem);
    }
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "total" + ":" + getTotal()+ "," +
            "inStoreBillId" + ":" + getInStoreBillId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "purchaseDate" + "=" + (getPurchaseDate() != null ? !getPurchaseDate().equals(this)  ? getPurchaseDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}