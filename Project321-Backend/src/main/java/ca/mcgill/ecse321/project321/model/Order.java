/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.project321.model;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

// line 42 "../../../../../../model.ump"
// line 148 "../../../../../../model.ump"
@Entity
@Table(name = "orders")
public class Order
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Order Attributes
  private int orderId;
  private boolean completed;
  private Date orderDate;
  private int total;
  private String payment;

  //Order Associations
  private Cart cart;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Order(boolean aCompleted, Date aOrderDate, int aTotal, String aPayment, Cart aCart)
  {
    orderId = 0;
    completed = aCompleted;
    orderDate = aOrderDate;
    total = aTotal;
    payment = aPayment;
    boolean didAddCart = setCart(aCart);
    if (!didAddCart)
    {
      throw new RuntimeException("Unable to create order due to cart. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Order() {}

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setOrderId(int aOrderId)
  {
    boolean wasSet = false;
    orderId = aOrderId;
    wasSet = true;
    return wasSet;
  }

  public boolean setCompleted(boolean aCompleted)
  {
    boolean wasSet = false;
    completed = aCompleted;
    wasSet = true;
    return wasSet;
  }

  public boolean setOrderDate(Date aOrderDate)
  {
    boolean wasSet = false;
    orderDate = aOrderDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setTotal(int aTotal)
  {
    boolean wasSet = false;
    total = aTotal;
    wasSet = true;
    return wasSet;
  }

  public boolean setPayment(String aPayment)
  {
    boolean wasSet = false;
    payment = aPayment;
    wasSet = true;
    return wasSet;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public int getOrderId()
  {
    return orderId;
  }

  public boolean getCompleted()
  {
    return completed;
  }

  public Date getOrderDate()
  {
    return orderDate;
  }

  public int getTotal()
  {
    return total;
  }

  public String getPayment()
  {
    return payment;
  }
  /* Code from template attribute_IsBoolean */
  @Transient
  public boolean isCompleted()
  {
    return completed;
  }
  /* Code from template association_GetOne */
  @OneToOne(cascade = CascadeType.MERGE)
  public Cart getCart()
  {
    return cart;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setCart(Cart aNewCart)
  {
    boolean wasSet = false;
    if (aNewCart == null)
    {
      //Unable to setCart to null, as order must always be associated to a cart
      return wasSet;
    }
    
    Order existingOrder = aNewCart.getOrder();
    if (existingOrder != null && !equals(existingOrder))
    {
      //Unable to setCart, the current cart already has a order, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Cart anOldCart = cart;
    cart = aNewCart;
    cart.setOrder(this);

    if (anOldCart != null)
    {
      anOldCart.setOrder(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Cart existingCart = cart;
    cart = null;
    if (existingCart != null)
    {
      existingCart.setOrder(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "orderId" + ":" + getOrderId()+ "," +
            "completed" + ":" + getCompleted()+ "," +
            "total" + ":" + getTotal()+ "," +
            "payment" + ":" + getPayment()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "orderDate" + "=" + (getOrderDate() != null ? !getOrderDate().equals(this)  ? getOrderDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "cart = "+(getCart()!=null?Integer.toHexString(System.identityHashCode(getCart())):"null");
  }
}