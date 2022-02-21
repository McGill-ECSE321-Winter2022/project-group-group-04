/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.project321.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

// line 60 "../../../../../../model.ump"
// line 172 "../../../../../../model.ump"
@Entity
@Table(name = "_cart_items_")
public class CartItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CartItem Attributes
  private long id;
  private int quantity;

  //CartItem Associations
  private Product product;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CartItem(int aQuantity, Product aProduct)
  {
    quantity = aQuantity;
    if (!setProduct(aProduct))
    {
      throw new RuntimeException("Unable to create CartItem due to aProduct. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public CartItem() {}

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

  public boolean setId(long id) {
    this.id = id;
    return true;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public long getId() {
    return id;
  }
  public int getQuantity()
  {
    return quantity;
  }
  /* Code from template association_GetOne */
  @ManyToOne(cascade = {CascadeType.MERGE})
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
            "id" + ":" + getId()+ "," +
            "quantity" + ":" + getQuantity()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "product = "+(getProduct()!=null?Integer.toHexString(System.identityHashCode(getProduct())):"null");
  }
}