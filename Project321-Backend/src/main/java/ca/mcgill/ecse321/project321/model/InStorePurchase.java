/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.project321.model;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// line 128 "../../../../../../model.ump"
// line 191 "../../../../../../model.ump"
@Entity
public class InStorePurchase
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //InStorePurchase Attributes
  private int total;
  private Date purchaseDate;
  private int inStorePurchaseId;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public InStorePurchase(int aTotal, Date aPurchaseDate)
  {
    total = aTotal;
    purchaseDate = aPurchaseDate;

    inStorePurchaseId = 0;
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

  public boolean setInStorePurchaseId(int aInStorePurchaseId)
  {
    boolean wasSet = false;
    inStorePurchaseId = aInStorePurchaseId;
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
  public int getInStorePurchaseId()
  {
    return inStorePurchaseId;
  }

  public void delete()
  {}

  // line 139 "../../../../../../model.ump"
   public  InStorePurchase(){
    
  }


  public String toString()
  {
    return super.toString() + "["+

            "total" + ":" + getTotal()+ "," +
            "inStorePurchaseId" + ":" + getInStorePurchaseId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "purchaseDate" + "=" + (getPurchaseDate() != null ? !getPurchaseDate().equals(this)  ? getPurchaseDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}