/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.project321.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// line 52 "../../../../../../model.ump"
// line 167 "../../../../../../model.ump"
@Entity
@Table(name = "_products_")
public class Product
{

  //------------------------
  // CLASS ENUMS
  //------------------------
  public enum PriceType {PER_UNIT, PER_KILOS};

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Product Attributes
  private long id;
  private String productName;
  private String isAvailableOnline;
  private int price;
  private int stock;
  private PriceType priceType; 

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Product(PriceType aPriceType, String aProductName, String aIsAvailableOnline, int aPrice, int aStock)
  {
    productName = aProductName;
    isAvailableOnline = aIsAvailableOnline;
    price = aPrice;
    stock = aStock;
    priceType = aPriceType;
  }

  public Product() {}

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setProductName(String aProductName)
  {
    boolean wasSet = false;
    productName = aProductName;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsAvailableOnline(String aIsAvailableOnline)
  {
    boolean wasSet = false;
    isAvailableOnline = aIsAvailableOnline;
    wasSet = true;
    return wasSet;
  }

  public boolean setPrice(int aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public boolean setStock(int aStock)
  {
    boolean wasSet = false;
    stock = aStock;
    wasSet = true;
    return wasSet;
  }

  public PriceType getPriceType() {
    return priceType;
  }

  public boolean setPriceType(PriceType aPriceType) {
    priceType = aPriceType;
    return true;
  }

  public String getProductName()
  {
    return productName;
  }

  public String getIsAvailableOnline()
  {
    return isAvailableOnline;
  }

  public int getPrice()
  {
    return price;
  }

  public int getStock()
  {
    return stock;
  }

  public void delete()
  {
    return;
  }

  public String toString()
  {
    return super.toString() + "["+
            "productName" + ":" + getProductName()+ "," +
            "isAvailableOnline" + ":" + getIsAvailableOnline()+ "," +
            "price" + ":" + getPrice()+ "," +
            "stock" + ":" + getStock()+ "]" + System.getProperties().getProperty("line.separator");
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public long getId() {
    return id;
  }

  public boolean setId(long id) {
    this.id = id;
    return true;
  }
}