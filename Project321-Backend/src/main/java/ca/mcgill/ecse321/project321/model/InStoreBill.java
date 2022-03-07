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
public class InStoreBill
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //InStoreBill Attributes
  private int total;
  private Date purchaseDate;
  private int inStoreBillId;
  private String paymentCode;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public InStoreBill(int aTotal, Date aPurchaseDate, String aPaymentCode)
  {
    total = aTotal;
    purchaseDate = aPurchaseDate;
    paymentCode = aPaymentCode;

    inStoreBillId = 0;
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

  public boolean setPaymentCode(String aPaymentCode)
  {
    boolean wasSet = false;
    paymentCode = aPaymentCode;
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

  public String getPaymentCode()
  {
    return paymentCode;
  }

  public void delete()
  {}

  // line 139 "../../../../../../model.ump"
   public  InStoreBill(){
    
  }


  public String toString()
  {
    return super.toString() + "["+

            "total" + ":" + getTotal()+ "," +
            "inStoreBillId" + ":" + getInStoreBillId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "purchaseDate" + "=" + (getPurchaseDate() != null ? !getPurchaseDate().equals(this)  ? getPurchaseDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}