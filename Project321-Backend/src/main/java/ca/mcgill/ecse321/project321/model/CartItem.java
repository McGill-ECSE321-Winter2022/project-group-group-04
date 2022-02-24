/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.project321.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

// line 84 "../../../../../../model.ump"
// line 202 "../../../../../../model.ump"
@Entity
public class CartItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CartItem Attributes
  private int quantity;
  private int cartItemId;

  //CartItem Associations
  private Product product;
  private Cart cart;
  private InStoreBill inStoreBill;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CartItem(int aQuantity, Product aProduct)
  {
    quantity = aQuantity;
    cartItemId = 0;
    if (!setProduct(aProduct))
    {
      throw new RuntimeException("Unable to create CartItem due to aProduct. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setQuantity(int aQuantity)
  {
    boolean wasSet = false;
    quantity = aQuantity;
    wasSet = true;
    return wasSet;
  }

  public boolean setCartItemId(int aCartItemId)
  {
    boolean wasSet = false;
    cartItemId = aCartItemId;
    wasSet = true;
    return wasSet;
  }

  public int getQuantity()
  {
    return quantity;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public int getCartItemId()
  {
    return cartItemId;
  }
  /* Code from template association_GetOne */
  @ManyToOne(cascade = CascadeType.MERGE)
  public Product getProduct()
  {
    return product;
  }
  /* Code from template association_GetOne */
  @ManyToOne(cascade = CascadeType.MERGE)
  public Cart getCart()
  {
    return cart;
  }

  public boolean hasCart()
  {
    boolean has = cart != null;
    return has;
  }
  /* Code from template association_GetOne */
  @ManyToOne(cascade = CascadeType.MERGE)
  public InStoreBill getInStoreBill()
  {
    return inStoreBill;
  }

  public boolean hasInStoreBill()
  {
    boolean has = inStoreBill != null;
    return has;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setProduct(Product aNewProduct)
  {
    boolean wasSet = false;
    if (aNewProduct != null)
    {
      product = aNewProduct;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setCart(Cart aNewCart)
  {
    boolean wasSet = false;
    cart = aNewCart;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setInStoreBill(InStoreBill aNewInStoreBill)
  {
    boolean wasSet = false;
    inStoreBill = aNewInStoreBill;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    product = null;
    cart = null;
    inStoreBill = null;
  }

  // line 92 "../../../../../../model.ump"
   public  CartItem(){
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "quantity" + ":" + getQuantity()+ "," +
            "cartItemId" + ":" + getCartItemId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "product = "+(getProduct()!=null?Integer.toHexString(System.identityHashCode(getProduct())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "cart = "+(getCart()!=null?Integer.toHexString(System.identityHashCode(getCart())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "inStoreBill = "+(getInStoreBill()!=null?Integer.toHexString(System.identityHashCode(getInStoreBill())):"null");
  }
}