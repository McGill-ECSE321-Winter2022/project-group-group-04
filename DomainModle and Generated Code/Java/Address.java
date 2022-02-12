/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;

// line 94 "model.ump"
// line 192 "model.ump"
public class Address
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Address Attributes
  private String town;
  private String street;
  private String postalCode;
  private int apartmentOrUnit;

  //Address Associations
  private List<Customer> customers;
  private StoreInfo storeInfo;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Address(String aTown, String aStreet, String aPostalCode, int aApartmentOrUnit, StoreInfo aStoreInfo)
  {
    town = aTown;
    street = aStreet;
    postalCode = aPostalCode;
    apartmentOrUnit = aApartmentOrUnit;
    customers = new ArrayList<Customer>();
    if (aStoreInfo == null || aStoreInfo.getAddress() != null)
    {
      throw new RuntimeException("Unable to create Address due to aStoreInfo. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    storeInfo = aStoreInfo;
  }

  public Address(String aTown, String aStreet, String aPostalCode, int aApartmentOrUnit, String aTelephoneForStoreInfo, String aEmailForStoreInfo, StoreOwner aStoreOwnerForStoreInfo, TheGroceryStoreSystem aTheGroceryStoreSystemForStoreInfo)
  {
    town = aTown;
    street = aStreet;
    postalCode = aPostalCode;
    apartmentOrUnit = aApartmentOrUnit;
    customers = new ArrayList<Customer>();
    storeInfo = new StoreInfo(aTelephoneForStoreInfo, aEmailForStoreInfo, aStoreOwnerForStoreInfo, this, aTheGroceryStoreSystemForStoreInfo);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTown(String aTown)
  {
    boolean wasSet = false;
    town = aTown;
    wasSet = true;
    return wasSet;
  }

  public boolean setStreet(String aStreet)
  {
    boolean wasSet = false;
    street = aStreet;
    wasSet = true;
    return wasSet;
  }

  public boolean setPostalCode(String aPostalCode)
  {
    boolean wasSet = false;
    postalCode = aPostalCode;
    wasSet = true;
    return wasSet;
  }

  public boolean setApartmentOrUnit(int aApartmentOrUnit)
  {
    boolean wasSet = false;
    apartmentOrUnit = aApartmentOrUnit;
    wasSet = true;
    return wasSet;
  }

  public String getTown()
  {
    return town;
  }

  public String getStreet()
  {
    return street;
  }

  public String getPostalCode()
  {
    return postalCode;
  }

  public int getApartmentOrUnit()
  {
    return apartmentOrUnit;
  }
  /* Code from template association_GetMany */
  public Customer getCustomer(int index)
  {
    Customer aCustomer = customers.get(index);
    return aCustomer;
  }

  public List<Customer> getCustomers()
  {
    List<Customer> newCustomers = Collections.unmodifiableList(customers);
    return newCustomers;
  }

  public int numberOfCustomers()
  {
    int number = customers.size();
    return number;
  }

  public boolean hasCustomers()
  {
    boolean has = customers.size() > 0;
    return has;
  }

  public int indexOfCustomer(Customer aCustomer)
  {
    int index = customers.indexOf(aCustomer);
    return index;
  }
  /* Code from template association_GetOne */
  public StoreInfo getStoreInfo()
  {
    return storeInfo;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCustomers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Customer addCustomer(String aEmail, String aName, String aPassword, TheGroceryStoreSystem aTheGroceryStoreSystem, String aPhone, Cart aCart)
  {
    return new Customer(aEmail, aName, aPassword, aTheGroceryStoreSystem, aPhone, aCart, this);
  }

  public boolean addCustomer(Customer aCustomer)
  {
    boolean wasAdded = false;
    if (customers.contains(aCustomer)) { return false; }
    Address existingAddress = aCustomer.getAddress();
    boolean isNewAddress = existingAddress != null && !this.equals(existingAddress);
    if (isNewAddress)
    {
      aCustomer.setAddress(this);
    }
    else
    {
      customers.add(aCustomer);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCustomer(Customer aCustomer)
  {
    boolean wasRemoved = false;
    //Unable to remove aCustomer, as it must always have a address
    if (!this.equals(aCustomer.getAddress()))
    {
      customers.remove(aCustomer);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCustomerAt(Customer aCustomer, int index)
  {  
    boolean wasAdded = false;
    if(addCustomer(aCustomer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCustomers()) { index = numberOfCustomers() - 1; }
      customers.remove(aCustomer);
      customers.add(index, aCustomer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCustomerAt(Customer aCustomer, int index)
  {
    boolean wasAdded = false;
    if(customers.contains(aCustomer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCustomers()) { index = numberOfCustomers() - 1; }
      customers.remove(aCustomer);
      customers.add(index, aCustomer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCustomerAt(aCustomer, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=customers.size(); i > 0; i--)
    {
      Customer aCustomer = customers.get(i - 1);
      aCustomer.delete();
    }
    StoreInfo existingStoreInfo = storeInfo;
    storeInfo = null;
    if (existingStoreInfo != null)
    {
      existingStoreInfo.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "town" + ":" + getTown()+ "," +
            "street" + ":" + getStreet()+ "," +
            "postalCode" + ":" + getPostalCode()+ "," +
            "apartmentOrUnit" + ":" + getApartmentOrUnit()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "storeInfo = "+(getStoreInfo()!=null?Integer.toHexString(System.identityHashCode(getStoreInfo())):"null");
  }
}