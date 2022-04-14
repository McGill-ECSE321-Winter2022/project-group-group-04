/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.project321.model;

import javax.persistence.Entity;
import javax.persistence.Id;

// line 51 "../../../../../../model.ump"
// line 153 "../../../../../../model.ump"
@Entity
public class Product
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum PriceType { PER_UNIT, PER_KILOS }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Product Attributes
  private PriceType priceType;
  private String productName;
  private String isAvailableOnline;
  private int price;
  private int stock;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Product(PriceType aPriceType, String aProductName, String aIsAvailableOnline, int aPrice, int aStock)
  {
    priceType = aPriceType;
    productName = aProductName;
    isAvailableOnline = aIsAvailableOnline;
    price = aPrice;
    stock = aStock;
  }

  public Product() {}

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPriceType(PriceType aPriceType)
  {
    boolean wasSet = false;
    priceType = aPriceType;
    wasSet = true;
    return wasSet;
  }

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

  public PriceType getPriceType()
  {
    return priceType;
  }

  @Id
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
  {}


  public String toString()
  {
    return super.toString() + "["+
            "productName" + ":" + getProductName()+ "," +
            "isAvailableOnline" + ":" + getIsAvailableOnline()+ "," +
            "price" + ":" + getPrice()+ "," +
            "stock" + ":" + getStock()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "priceType" + "=" + (getPriceType() != null ? !getPriceType().equals(this)  ? getPriceType().toString().replaceAll("  ","    ") : "this" : "null");
  }
}