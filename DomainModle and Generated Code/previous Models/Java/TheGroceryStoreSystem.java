/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;
import java.sql.Time;
import java.sql.Date;

// line 99 "model.ump"
// line 177 "model.ump"
public class TheGroceryStoreSystem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TheGroceryStoreSystem Attributes
  private String telephone;
  private String email;

  //TheGroceryStoreSystem Associations
  private StoreOwner storeOwner;
  private List<Employee> employees;
  private List<Customer> customers;
  private List<Product> products;
  private List<TimeSlot> timeSlots;
  private List<Shift> shifts;
  private List<Day> daies;
  private Address address;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TheGroceryStoreSystem(String aTelephone, String aEmail, StoreOwner aStoreOwner, Address aAddress)
  {
    telephone = aTelephone;
    email = aEmail;
    if (aStoreOwner == null || aStoreOwner.getTheGroceryStoreSystem() != null)
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
    if (aAddress == null || aAddress.getTheGroceryStoreSystem() != null)
    {
      throw new RuntimeException("Unable to create TheGroceryStoreSystem due to aAddress. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    address = aAddress;
  }

  public TheGroceryStoreSystem(String aTelephone, String aEmail, String aEmailForStoreOwner, String aNameForStoreOwner, String aPasswordForStoreOwner, String aTownForAddress, String aStreetForAddress, String aPostalCodeForAddress, int aUnitForAddress)
  {
    telephone = aTelephone;
    email = aEmail;
    storeOwner = new StoreOwner(aEmailForStoreOwner, aNameForStoreOwner, aPasswordForStoreOwner, this);
    employees = new ArrayList<Employee>();
    customers = new ArrayList<Customer>();
    products = new ArrayList<Product>();
    timeSlots = new ArrayList<TimeSlot>();
    shifts = new ArrayList<Shift>();
    daies = new ArrayList<Day>();
    address = new Address(aTownForAddress, aStreetForAddress, aPostalCodeForAddress, aUnitForAddress, this);
  }

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
  public StoreOwner getStoreOwner()
  {
    return storeOwner;
  }
  /* Code from template association_GetMany */
  public Employee getEmployee(int index)
  {
    Employee aEmployee = employees.get(index);
    return aEmployee;
  }

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
  /* Code from template association_GetMany */
  public Product getProduct(int index)
  {
    Product aProduct = products.get(index);
    return aProduct;
  }

  public List<Product> getProducts()
  {
    List<Product> newProducts = Collections.unmodifiableList(products);
    return newProducts;
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

  public List<TimeSlot> getTimeSlots()
  {
    List<TimeSlot> newTimeSlots = Collections.unmodifiableList(timeSlots);
    return newTimeSlots;
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

  public List<Shift> getShifts()
  {
    List<Shift> newShifts = Collections.unmodifiableList(shifts);
    return newShifts;
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

  public List<Day> getDaies()
  {
    List<Day> newDaies = Collections.unmodifiableList(daies);
    return newDaies;
  }

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
  public Address getAddress()
  {
    return address;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfEmployees()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Employee addEmployee(String aEmail, String aName, String aPassword, Employee.EmployeeStatus aStatus, Shift aShift)
  {
    return new Employee(aEmail, aName, aPassword, aStatus, aShift, this);
  }

  public boolean addEmployee(Employee aEmployee)
  {
    boolean wasAdded = false;
    if (employees.contains(aEmployee)) { return false; }
    TheGroceryStoreSystem existingTheGroceryStoreSystem = aEmployee.getTheGroceryStoreSystem();
    boolean isNewTheGroceryStoreSystem = existingTheGroceryStoreSystem != null && !this.equals(existingTheGroceryStoreSystem);
    if (isNewTheGroceryStoreSystem)
    {
      aEmployee.setTheGroceryStoreSystem(this);
    }
    else
    {
      employees.add(aEmployee);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeEmployee(Employee aEmployee)
  {
    boolean wasRemoved = false;
    //Unable to remove aEmployee, as it must always have a theGroceryStoreSystem
    if (!this.equals(aEmployee.getTheGroceryStoreSystem()))
    {
      employees.remove(aEmployee);
      wasRemoved = true;
    }
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
  /* Code from template association_AddManyToOne */
  public Customer addCustomer(String aEmail, String aName, String aPassword, String aPhone, Address aAddress, Cart aCart)
  {
    return new Customer(aEmail, aName, aPassword, aPhone, aAddress, aCart, this);
  }

  public boolean addCustomer(Customer aCustomer)
  {
    boolean wasAdded = false;
    if (customers.contains(aCustomer)) { return false; }
    TheGroceryStoreSystem existingTheGroceryStoreSystem = aCustomer.getTheGroceryStoreSystem();
    boolean isNewTheGroceryStoreSystem = existingTheGroceryStoreSystem != null && !this.equals(existingTheGroceryStoreSystem);
    if (isNewTheGroceryStoreSystem)
    {
      aCustomer.setTheGroceryStoreSystem(this);
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
    //Unable to remove aCustomer, as it must always have a theGroceryStoreSystem
    if (!this.equals(aCustomer.getTheGroceryStoreSystem()))
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfProducts()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Product addProduct(String aIsAvailableOnline, int aPrice, int aStock)
  {
    return new Product(aIsAvailableOnline, aPrice, aStock, this);
  }

  public boolean addProduct(Product aProduct)
  {
    boolean wasAdded = false;
    if (products.contains(aProduct)) { return false; }
    TheGroceryStoreSystem existingTheGroceryStoreSystem = aProduct.getTheGroceryStoreSystem();
    boolean isNewTheGroceryStoreSystem = existingTheGroceryStoreSystem != null && !this.equals(existingTheGroceryStoreSystem);
    if (isNewTheGroceryStoreSystem)
    {
      aProduct.setTheGroceryStoreSystem(this);
    }
    else
    {
      products.add(aProduct);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeProduct(Product aProduct)
  {
    boolean wasRemoved = false;
    //Unable to remove aProduct, as it must always have a theGroceryStoreSystem
    if (!this.equals(aProduct.getTheGroceryStoreSystem()))
    {
      products.remove(aProduct);
      wasRemoved = true;
    }
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
  /* Code from template association_AddManyToOne */
  public TimeSlot addTimeSlot(Time aStartTime, Time aEndTime, Date aDate, int aMaxOrderPerSlot, TimeSlot.ShoppingType aShoppingType, Day aDay)
  {
    return new TimeSlot(aStartTime, aEndTime, aDate, aMaxOrderPerSlot, aShoppingType, aDay, this);
  }

  public boolean addTimeSlot(TimeSlot aTimeSlot)
  {
    boolean wasAdded = false;
    if (timeSlots.contains(aTimeSlot)) { return false; }
    TheGroceryStoreSystem existingTheGroceryStoreSystem = aTimeSlot.getTheGroceryStoreSystem();
    boolean isNewTheGroceryStoreSystem = existingTheGroceryStoreSystem != null && !this.equals(existingTheGroceryStoreSystem);
    if (isNewTheGroceryStoreSystem)
    {
      aTimeSlot.setTheGroceryStoreSystem(this);
    }
    else
    {
      timeSlots.add(aTimeSlot);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTimeSlot(TimeSlot aTimeSlot)
  {
    boolean wasRemoved = false;
    //Unable to remove aTimeSlot, as it must always have a theGroceryStoreSystem
    if (!this.equals(aTimeSlot.getTheGroceryStoreSystem()))
    {
      timeSlots.remove(aTimeSlot);
      wasRemoved = true;
    }
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
  /* Code from template association_AddManyToOne */
  public Shift addShift(Time aStartHour, Time aEndHour, Date aDate, Day aDay)
  {
    return new Shift(aStartHour, aEndHour, aDate, aDay, this);
  }

  public boolean addShift(Shift aShift)
  {
    boolean wasAdded = false;
    if (shifts.contains(aShift)) { return false; }
    TheGroceryStoreSystem existingTheGroceryStoreSystem = aShift.getTheGroceryStoreSystem();
    boolean isNewTheGroceryStoreSystem = existingTheGroceryStoreSystem != null && !this.equals(existingTheGroceryStoreSystem);
    if (isNewTheGroceryStoreSystem)
    {
      aShift.setTheGroceryStoreSystem(this);
    }
    else
    {
      shifts.add(aShift);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeShift(Shift aShift)
  {
    boolean wasRemoved = false;
    //Unable to remove aShift, as it must always have a theGroceryStoreSystem
    if (!this.equals(aShift.getTheGroceryStoreSystem()))
    {
      shifts.remove(aShift);
      wasRemoved = true;
    }
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
  public boolean isNumberOfDaiesValid()
  {
    boolean isValid = numberOfDaies() >= minimumNumberOfDaies() && numberOfDaies() <= maximumNumberOfDaies();
    return isValid;
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
  /* Code from template association_AddMNToOnlyOne */
  public Day addDay(Day.WeekDays aDay, Time aStoreStartHour, Time aStoreEndHour)
  {
    if (numberOfDaies() >= maximumNumberOfDaies())
    {
      return null;
    }
    else
    {
      return new Day(aDay, aStoreStartHour, aStoreEndHour, this);
    }
  }

  public boolean addDay(Day aDay)
  {
    boolean wasAdded = false;
    if (daies.contains(aDay)) { return false; }
    if (numberOfDaies() >= maximumNumberOfDaies())
    {
      return wasAdded;
    }

    TheGroceryStoreSystem existingTheGroceryStoreSystem = aDay.getTheGroceryStoreSystem();
    boolean isNewTheGroceryStoreSystem = existingTheGroceryStoreSystem != null && !this.equals(existingTheGroceryStoreSystem);

    if (isNewTheGroceryStoreSystem && existingTheGroceryStoreSystem.numberOfDaies() <= minimumNumberOfDaies())
    {
      return wasAdded;
    }

    if (isNewTheGroceryStoreSystem)
    {
      aDay.setTheGroceryStoreSystem(this);
    }
    else
    {
      daies.add(aDay);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeDay(Day aDay)
  {
    boolean wasRemoved = false;
    //Unable to remove aDay, as it must always have a theGroceryStoreSystem
    if (this.equals(aDay.getTheGroceryStoreSystem()))
    {
      return wasRemoved;
    }

    //theGroceryStoreSystem already at minimum (7)
    if (numberOfDaies() <= minimumNumberOfDaies())
    {
      return wasRemoved;
    }
    daies.remove(aDay);
    wasRemoved = true;
    return wasRemoved;
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
  }


  public String toString()
  {
    return super.toString() + "["+
            "telephone" + ":" + getTelephone()+ "," +
            "email" + ":" + getEmail()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "storeOwner = "+(getStoreOwner()!=null?Integer.toHexString(System.identityHashCode(getStoreOwner())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "address = "+(getAddress()!=null?Integer.toHexString(System.identityHashCode(getAddress())):"null");
  }
}