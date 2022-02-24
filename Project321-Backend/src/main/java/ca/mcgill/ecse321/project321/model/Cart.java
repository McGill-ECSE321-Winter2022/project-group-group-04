/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.project321.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

// line 44 "../../../../../../model.ump"
// line 182 "../../../../../../model.ump"
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
  private TimeSlot timeSlot;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Cart(ShoppingType aType, Customer aCustomer)
  {
    type = aType;
    cartId = 0;
    if (!setCustomer(aCustomer))
    {
      throw new RuntimeException("Unable to create Cart due to aCustomer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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
  @ManyToOne(cascade = {CascadeType.MERGE})
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_GetOne */
  @ManyToOne(cascade = {CascadeType.MERGE})
  public TimeSlot getTimeSlot()
  {
    return timeSlot;
  }

  public boolean hasTimeSlot()
  {
    boolean has = timeSlot != null;
    return has;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCustomer(Customer aNewCustomer)
  {
    boolean wasSet = false;
    if (aNewCustomer != null)
    {
      customer = aNewCustomer;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setTimeSlot(TimeSlot aNewTimeSlot)
  {
    boolean wasSet = false;
    timeSlot = aNewTimeSlot;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    customer = null;
    timeSlot = null;
  }

  // line 57 "../../../../../../model.ump"
   public  Cart(){
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "cartId" + ":" + getCartId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "type" + "=" + (getType() != null ? !getType().equals(this)  ? getType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "timeSlot = "+(getTimeSlot()!=null?Integer.toHexString(System.identityHashCode(getTimeSlot())):"null");
  }
}