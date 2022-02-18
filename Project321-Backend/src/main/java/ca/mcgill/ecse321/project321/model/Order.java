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

// line 43 "../../../../../../model.ump"
// line 162 "../../../../../../model.ump"
@Entity
@Table(name = "_order_")
public class Order
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Order Attributes
  private long id;
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
    completed = aCompleted;
    orderDate = aOrderDate;
    total = aTotal;
    payment = aPayment;
    if (aCart == null || aCart.getOrder() != null)
    {
      throw new RuntimeException("Unable to create Order due to aCart. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    cart = aCart;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(long id) {
    this.id = id;
    return true;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public long getId() {
    return id;
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
  @OneToOne(cascade = {CascadeType.ALL})
  public Cart getCart()
  {
    return cart;
  }

  // Unused setter: only to please hibernate
  public void setCart(Cart cart) {
    return;
  }

  public void delete()
  {
    Cart existingCart = cart;
    cart = null;
    if (existingCart != null)
    {
      existingCart.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "completed" + ":" + getCompleted()+ "," +
            "total" + ":" + getTotal()+ "," +
            "payment" + ":" + getPayment()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "orderDate" + "=" + (getOrderDate() != null ? !getOrderDate().equals(this)  ? getOrderDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "cart = "+(getCart()!=null?Integer.toHexString(System.identityHashCode(getCart())):"null");
  }
}