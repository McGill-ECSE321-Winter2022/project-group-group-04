/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.project321.model;
import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import java.beans.Transient;

// line 106 "../../../../../../model.ump"
// line 192 "../../../../../../model.ump"
@Entity
public class TheGroceryStoreSystem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TheGroceryStoreSystem Attributes
  private String telephone;
  private String email;
  private long id;

  //TheGroceryStoreSystem Associations
  private StoreOwner storeOwner;
  private List<Employee> employees;
  private List<Customer> customers;
  private List<Product> products;
  private List<TimeSlot> timeSlots;
  private List<Shift> shifts;
  private List<Day> daies;
  private Address address;
  private List<InStoreBill> inStoreBills;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TheGroceryStoreSystem(String aTelephone, String aEmail, StoreOwner aStoreOwner, Address aAddress)
  {
    telephone = aTelephone;
    email = aEmail;
    if (aStoreOwner == null)
    {
      throw new RuntimeException("Unable to create TheGroceryStoreSystem due to aStoreOwner. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    storeOwner = aStoreOwner;
    employees = new ArrayList<Employee>();
    customers = new ArrayList<Customer>();
    products = new ArrayList<Product>();
    timeSlots = new ArrayList<TimeSlot>();
    shifts = new ArrayList<Shift>();
    daies = new ArrayList<Day>();
    if (aAddress == null)
    {
      throw new RuntimeException("Unable to create TheGroceryStoreSystem due to aAddress. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    address = aAddress;
    inStoreBills = new ArrayList<InStoreBill>();
  }

  public TheGroceryStoreSystem(String aTelephone, String aEmail, String aEmailForStoreOwner, String aNameForStoreOwner, String aPasswordForStoreOwner, String aTownForAddress, String aStreetForAddress, String aPostalCodeForAddress, int aUnitForAddress)
  {
    telephone = aTelephone;
    email = aEmail;
    storeOwner = new StoreOwner(aEmailForStoreOwner, aNameForStoreOwner, aPasswordForStoreOwner);
    employees = new ArrayList<Employee>();
    customers = new ArrayList<Customer>();
    products = new ArrayList<Product>();
    timeSlots = new ArrayList<TimeSlot>();
    shifts = new ArrayList<Shift>();
    daies = new ArrayList<Day>();
    address = new Address(aTownForAddress, aStreetForAddress, aPostalCodeForAddress, aUnitForAddress);
    inStoreBills = new ArrayList<InStoreBill>();
  }

  public TheGroceryStoreSystem() {}

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTelephone(String aTelephone)
  {
    boolean wasSet = false;
    telephone = aTelephone;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public String getTelephone()
  {
    return telephone;
  }

  public String getEmail()
  {
    return email;
  }
  /* Code from template association_GetOne */
  @OneToOne(cascade = {CascadeType.ALL})
  public StoreOwner getStoreOwner()
  {
    return storeOwner;
  }

  public boolean setStoreOwner(StoreOwner storeOwner) {
    return true;
  }

  /* Code from template association_GetMany */
  public Employee getEmployee(int index)
  {
    Employee aEmployee = employees.get(index);
    return aEmployee;
  }
  public boolean setEmployees(Employee employee) {
    return true;
  }

  @OneToMany(cascade={CascadeType.ALL})
  public List<Employee> getEmployees()
  {
    List<Employee> newEmployees = Collections.unmodifiableList(employees);
    return newEmployees;
  }

  public int numberOfEmployees()
  {
    int number = employees.size();
    return number;
  }

  public boolean hasEmployees()
  {
    boolean has = employees.size() > 0;
    return has;
  }

  public int indexOfEmployee(Employee aEmployee)
  {
    int index = employees.indexOf(aEmployee);
    return index;
  }
  /* Code from template association_GetMany */
  public Customer getCustomer(int index)
  {
    Customer aCustomer = customers.get(index);
    return aCustomer;
  }

  @OneToMany(cascade = {CascadeType.ALL})
  public List<Customer> getCustomers()
  {
    List<Customer> newCustomers = Collections.unmodifiableList(customers);
    return newCustomers;
  }

  public boolean setCustomers(Customer customer) {
    return true;
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
  /* Code from template association_GetMany */
  public Product getProduct(int index)
  {
    Product aProduct = products.get(index);
    return aProduct;
  }

  @OneToMany(cascade=CascadeType.ALL)
  public List<Product> getProducts()
  {
    List<Product> newProducts = Collections.unmodifiableList(products);
    return newProducts;
  }

  public boolean setProducts(Product product) {
    return true;
  }

  public int numberOfProducts()
  {
    int number = products.size();
    return number;
  }

  public boolean hasProducts()
  {
    boolean has = products.size() > 0;
    return has;
  }

  public int indexOfProduct(Product aProduct)
  {
    int index = products.indexOf(aProduct);
    return index;
  }
  /* Code from template association_GetMany */
  public TimeSlot getTimeSlot(int index)
  {
    TimeSlot aTimeSlot = timeSlots.get(index);
    return aTimeSlot;
  }

  @OneToMany(cascade={CascadeType.ALL})
  public List<TimeSlot> getTimeSlots()
  {
    List<TimeSlot> newTimeSlots = Collections.unmodifiableList(timeSlots);
    return newTimeSlots;
  }

  public boolean setTimeSlots(TimeSlot timeSlot) {
    return true;
  }

  public int numberOfTimeSlots()
  {
    int number = timeSlots.size();
    return number;
  }

  public boolean hasTimeSlots()
  {
    boolean has = timeSlots.size() > 0;
    return has;
  }

  public int indexOfTimeSlot(TimeSlot aTimeSlot)
  {
    int index = timeSlots.indexOf(aTimeSlot);
    return index;
  }
  /* Code from template association_GetMany */
  public Shift getShift(int index)
  {
    Shift aShift = shifts.get(index);
    return aShift;
  }

  @OneToMany(cascade={CascadeType.ALL})
  public List<Shift> getShifts()
  {
    List<Shift> newShifts = Collections.unmodifiableList(shifts);
    return newShifts;
  }

  public boolean setShifts(Shift shift) {
    return true;
  }

  public int numberOfShifts()
  {
    int number = shifts.size();
    return number;
  }

  public boolean hasShifts()
  {
    boolean has = shifts.size() > 0;
    return has;
  }

  public int indexOfShift(Shift aShift)
  {
    int index = shifts.indexOf(aShift);
    return index;
  }
  /* Code from template association_GetMany */
  public Day getDay(int index)
  {
    Day aDay = daies.get(index);
    return aDay;
  }

  @OneToMany(cascade={CascadeType.ALL})
  public List<Day> getDaies()
  {
    List<Day> newDaies = Collections.unmodifiableList(daies);
    return newDaies;
  }

  public boolean setDaies(Day day) {
    return true;
  }

  @Transient
  public int numberOfDaies()
  {
    int number = daies.size();
    return number;
  }

  public boolean hasDaies()
  {
    boolean has = daies.size() > 0;
    return has;
  }

  public int indexOfDay(Day aDay)
  {
    int index = daies.indexOf(aDay);
    return index;
  }
  /* Code from template association_GetOne */
  @OneToOne(cascade = {CascadeType.ALL})
  public Address getAddress()
  {
    return address;
  }

  public boolean setAddress(Address address) {
    return true;
  }

  /* Code from template association_GetMany */
  public InStoreBill getInStoreBill(int index)
  {
    InStoreBill aInStoreBill = inStoreBills.get(index);
    return aInStoreBill;
  }

  @OneToMany(cascade={CascadeType.ALL})
  public List<InStoreBill> getInStoreBills()
  {
    List<InStoreBill> newInStoreBills = Collections.unmodifiableList(inStoreBills);
    return newInStoreBills;
  }

  public boolean setInStoreBills(InStoreBill inStoreBill) {
    return true;
  }

  public int numberOfInStoreBills()
  {
    int number = inStoreBills.size();
    return number;
  }

  public boolean hasInStoreBills()
  {
    boolean has = inStoreBills.size() > 0;
    return has;
  }

  public int indexOfInStoreBill(InStoreBill aInStoreBill)
  {
    int index = inStoreBills.indexOf(aInStoreBill);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfEmployees()
  {
    return 0;
  }

  public boolean addEmployee(Employee aEmployee)
  {
    boolean wasAdded = false;
    if (employees.contains(aEmployee)) { return false; }
    employees.add(aEmployee);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeEmployee(Employee aEmployee)
  {
    boolean wasRemoved = false;
    if (employees.contains(aEmployee)) { employees.remove(aEmployee); }
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addEmployeeAt(Employee aEmployee, int index)
  {  
    boolean wasAdded = false;
    if(addEmployee(aEmployee))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEmployees()) { index = numberOfEmployees() - 1; }
      employees.remove(aEmployee);
      employees.add(index, aEmployee);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveEmployeeAt(Employee aEmployee, int index)
  {
    boolean wasAdded = false;
    if(employees.contains(aEmployee))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEmployees()) { index = numberOfEmployees() - 1; }
      employees.remove(aEmployee);
      employees.add(index, aEmployee);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addEmployeeAt(aEmployee, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCustomers()
  {
    return 0;
  }

  public boolean addCustomer(Customer aCustomer)
  {
    boolean wasAdded = false;
    if (customers.contains(aCustomer)) { return false; }
    customers.add(aCustomer);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCustomer(Customer aCustomer)
  {
    boolean wasRemoved = false;
    if (customers.contains(aCustomer)) { customers.remove(aCustomer); }
    wasRemoved = true;
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfProducts()
  {
    return 0;
  }

  public boolean addProduct(Product aProduct)
  {
    boolean wasAdded = false;
    if (products.contains(aProduct)) { return false; }
    products.add(aProduct);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeProduct(Product aProduct)
  {
    boolean wasRemoved = false;
    if (products.contains(aProduct)) { products.remove(aProduct); }
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addProductAt(Product aProduct, int index)
  {  
    boolean wasAdded = false;
    if(addProduct(aProduct))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfProducts()) { index = numberOfProducts() - 1; }
      products.remove(aProduct);
      products.add(index, aProduct);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveProductAt(Product aProduct, int index)
  {
    boolean wasAdded = false;
    if(products.contains(aProduct))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfProducts()) { index = numberOfProducts() - 1; }
      products.remove(aProduct);
      products.add(index, aProduct);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addProductAt(aProduct, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTimeSlots()
  {
    return 0;
  }

  public boolean addTimeSlot(TimeSlot aTimeSlot)
  {
    boolean wasAdded = false;
    if (timeSlots.contains(aTimeSlot)) { return false; }
    timeSlots.add(aTimeSlot);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTimeSlot(TimeSlot aTimeSlot)
  {
    boolean wasRemoved = false;
    //Unable to remove aTimeSlot, as it must always have a theGroceryStoreSystem
    if (timeSlots.contains(aTimeSlot)) { timeSlots.remove(aTimeSlot); }
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTimeSlotAt(TimeSlot aTimeSlot, int index)
  {  
    boolean wasAdded = false;
    if(addTimeSlot(aTimeSlot))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTimeSlots()) { index = numberOfTimeSlots() - 1; }
      timeSlots.remove(aTimeSlot);
      timeSlots.add(index, aTimeSlot);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTimeSlotAt(TimeSlot aTimeSlot, int index)
  {
    boolean wasAdded = false;
    if(timeSlots.contains(aTimeSlot))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTimeSlots()) { index = numberOfTimeSlots() - 1; }
      timeSlots.remove(aTimeSlot);
      timeSlots.add(index, aTimeSlot);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTimeSlotAt(aTimeSlot, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfShifts()
  {
    return 0;
  }

  public boolean addShift(Shift aShift)
  {
    boolean wasAdded = false;
    if (shifts.contains(aShift)) { return false; }
    shifts.add(aShift);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeShift(Shift aShift)
  {
    boolean wasRemoved = false;
    //Unable to remove aShift, as it must always have a theGroceryStoreSystem
    if (shifts.contains(aShift)) { shifts.remove(aShift); }
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addShiftAt(Shift aShift, int index)
  {  
    boolean wasAdded = false;
    if(addShift(aShift))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfShifts()) { index = numberOfShifts() - 1; }
      shifts.remove(aShift);
      shifts.add(index, aShift);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveShiftAt(Shift aShift, int index)
  {
    boolean wasAdded = false;
    if(shifts.contains(aShift))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfShifts()) { index = numberOfShifts() - 1; }
      shifts.remove(aShift);
      shifts.add(index, aShift);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addShiftAt(aShift, index);
    }
    return wasAdded;
  }
  /* Code from template association_IsNumberOfValidMethod */
  @Transient
  public boolean isNumberOfDaiesValid()
  {
    boolean isValid = numberOfDaies() >= minimumNumberOfDaies() && numberOfDaies() <= maximumNumberOfDaies();
    return isValid;
  }

  public boolean setNumberOfDaiesValid(int numberOfDaysValid) {
    return true;
  }

  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfDaies()
  {
    return 7;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDaies()
  {
    return 7;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfDaies()
  {
    return 7;
  }

  public boolean addDay(Day aDay)
  {
    boolean wasAdded = false;
    if (daies.contains(aDay)) { return false; }
    if (numberOfDaies() >= maximumNumberOfDaies())
    {
      return wasAdded;
    }
    daies.add(aDay);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeDay(Day aDay)
  {
    boolean wasRemoved = false;

    //theGroceryStoreSystem already at minimum (7)
    if (numberOfDaies() <= minimumNumberOfDaies())
    {
      return wasRemoved;
    }
    daies.remove(aDay);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfInStoreBills()
  {
    return 0;
  }

  public boolean addInStoreBill(InStoreBill aInStoreBill)
  {
    boolean wasAdded = false;
    if (inStoreBills.contains(aInStoreBill)) { return false; }
    inStoreBills.add(aInStoreBill);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeInStoreBill(InStoreBill aInStoreBill)
  {
    boolean wasRemoved = false;
    //Unable to remove aInStoreBill, as it must always have a theGroceryStoreSystem
    if (inStoreBills.contains(aInStoreBill)) { inStoreBills.remove(aInStoreBill); }  
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addInStoreBillAt(InStoreBill aInStoreBill, int index)
  {  
    boolean wasAdded = false;
    if(addInStoreBill(aInStoreBill))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfInStoreBills()) { index = numberOfInStoreBills() - 1; }
      inStoreBills.remove(aInStoreBill);
      inStoreBills.add(index, aInStoreBill);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveInStoreBillAt(InStoreBill aInStoreBill, int index)
  {
    boolean wasAdded = false;
    if(inStoreBills.contains(aInStoreBill))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfInStoreBills()) { index = numberOfInStoreBills() - 1; }
      inStoreBills.remove(aInStoreBill);
      inStoreBills.add(index, aInStoreBill);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addInStoreBillAt(aInStoreBill, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    StoreOwner existingStoreOwner = storeOwner;
    storeOwner = null;
    if (existingStoreOwner != null)
    {
      existingStoreOwner.delete();
    }
    while (employees.size() > 0)
    {
      Employee aEmployee = employees.get(employees.size() - 1);
      aEmployee.delete();
      employees.remove(aEmployee);
    }
    
    while (customers.size() > 0)
    {
      Customer aCustomer = customers.get(customers.size() - 1);
      aCustomer.delete();
      customers.remove(aCustomer);
    }
    
    while (products.size() > 0)
    {
      Product aProduct = products.get(products.size() - 1);
      aProduct.delete();
      products.remove(aProduct);
    }
    
    while (timeSlots.size() > 0)
    {
      TimeSlot aTimeSlot = timeSlots.get(timeSlots.size() - 1);
      aTimeSlot.delete();
      timeSlots.remove(aTimeSlot);
    }
    
    while (shifts.size() > 0)
    {
      Shift aShift = shifts.get(shifts.size() - 1);
      aShift.delete();
      shifts.remove(aShift);
    }
    
    while (daies.size() > 0)
    {
      Day aDay = daies.get(daies.size() - 1);
      aDay.delete();
      daies.remove(aDay);
    }
    
    Address existingAddress = address;
    address = null;
    if (existingAddress != null)
    {
      existingAddress.delete();
    }
    while (inStoreBills.size() > 0)
    {
      InStoreBill aInStoreBill = inStoreBills.get(inStoreBills.size() - 1);
      aInStoreBill.delete();
      inStoreBills.remove(aInStoreBill);
    }
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "telephone" + ":" + getTelephone()+ "," +
            "email" + ":" + getEmail()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "storeOwner = "+(getStoreOwner()!=null?Integer.toHexString(System.identityHashCode(getStoreOwner())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "address = "+(getAddress()!=null?Integer.toHexString(System.identityHashCode(getAddress())):"null");
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
}