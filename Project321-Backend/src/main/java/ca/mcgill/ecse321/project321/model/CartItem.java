/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.project321.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

// line 60 "../../../../../../model.ump"
// line 172 "../../../../../../model.ump"
@Entity
public class CartItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CartItem Attributes
  private String cartItemID;
  private int quantity;

  //CartItem Associations
  private Product product;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CartItem(String aCartItemID, int aQuantity, Product aProduct)
  {
    cartItemID = aCartItemID;
    quantity = aQuantity;
    if (!setProduct(aProduct))
    {
      throw new RuntimeException("Unable to create CartItem due to aProduct. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCartItemID(String aCartItemID)
  {
    boolean wasSet = false;
    cartItemID = aCartItemID;
    wasSet = true;
    return wasSet;
  }

  public boolean setQuantity(int aQuantity)
  {
    boolean wasSet = false;
    quantity = aQuantity;
    wasSet = true;
    return wasSet;
  }

  @Id
  public String getCartItemID()
  {
    return cartItemID;
  }

  public int getQuantity()
  {
    return quantity;
  }
  /* Code from template association_GetOne */
  @ManyToOne(cascade = {CascadeType.ALL})
  public Product getProduct()
  {
    return product;
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

  public void delete()
  {
    product = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "cartItemID" + ":" + getCartItemID()+ "," +
            "quantity" + ":" + getQuantity()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "product = "+(getProduct()!=null?Integer.toHexString(System.identityHashCode(getProduct())):"null");
  }
}