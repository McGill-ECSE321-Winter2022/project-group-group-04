/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;
import java.sql.Date;
import java.sql.Time;

// line 104 "model.ump"
// line 199 "model.ump"
public class TheGroceryStoreSystem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TheGroceryStoreSystem Associations
  private List<User> users;
  private List<Order> orders;
  private List<Product> products;
  private List<TimeSlot> timeSlots;
  private List<Shifts> shifts;
  private List<StoreInfo> storeInfos;
  private List<Days> days;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TheGroceryStoreSystem()
  {
    users = new ArrayList<User>();
    orders = new ArrayList<Order>();
    products = new ArrayList<Product>();
    timeSlots = new ArrayList<TimeSlot>();
    shifts = new ArrayList<Shifts>();
    storeInfos = new ArrayList<StoreInfo>();
    days = new ArrayList<Days>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public User getUser(int index)
  {
    User aUser = users.get(index);
    return aUser;
  }

  public List<User> getUsers()
  {
    List<User> newUsers = Collections.unmodifiableList(users);
    return newUsers;
  }

  public int numberOfUsers()
  {
    int number = users.size();
    return number;
  }

  public boolean hasUsers()
  {
    boolean has = users.size() > 0;
    return has;
  }

  public int indexOfUser(User aUser)
  {
    int index = users.indexOf(aUser);
    return index;
  }
  /* Code from template association_GetMany */
  public Order getOrder(int index)
  {
    Order aOrder = orders.get(index);
    return aOrder;
  }

  public List<Order> getOrders()
  {
    List<Order> newOrders = Collections.unmodifiableList(orders);
    return newOrders;
  }

  public int numberOfOrders()
  {
    int number = orders.size();
    return number;
  }

  public boolean hasOrders()
  {
    boolean has = orders.size() > 0;
    return has;
  }

  public int indexOfOrder(Order aOrder)
  {
    int index = orders.indexOf(aOrder);
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
  public Shifts getShift(int index)
  {
    Shifts aShift = shifts.get(index);
    return aShift;
  }

  public List<Shifts> getShifts()
  {
    List<Shifts> newShifts = Collections.unmodifiableList(shifts);
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

  public int indexOfShift(Shifts aShift)
  {
    int index = shifts.indexOf(aShift);
    return index;
  }
  /* Code from template association_GetMany */
  public StoreInfo getStoreInfo(int index)
  {
    StoreInfo aStoreInfo = storeInfos.get(index);
    return aStoreInfo;
  }

  public List<StoreInfo> getStoreInfos()
  {
    List<StoreInfo> newStoreInfos = Collections.unmodifiableList(storeInfos);
    return newStoreInfos;
  }

  public int numberOfStoreInfos()
  {
    int number = storeInfos.size();
    return number;
  }

  public boolean hasStoreInfos()
  {
    boolean has = storeInfos.size() > 0;
    return has;
  }

  public int indexOfStoreInfo(StoreInfo aStoreInfo)
  {
    int index = storeInfos.indexOf(aStoreInfo);
    return index;
  }
  /* Code from template association_GetMany */
  public Days getDay(int index)
  {
    Days aDay = days.get(index);
    return aDay;
  }

  public List<Days> getDays()
  {
    List<Days> newDays = Collections.unmodifiableList(days);
    return newDays;
  }

  public int numberOfDays()
  {
    int number = days.size();
    return number;
  }

  public boolean hasDays()
  {
    boolean has = days.size() > 0;
    return has;
  }

  public int indexOfDay(Days aDay)
  {
    int index = days.indexOf(aDay);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUsers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public User addUser(String aEmail, String aName, String aPassword)
  {
    return new User(aEmail, aName, aPassword, this);
  }

  public boolean addUser(User aUser)
  {
    boolean wasAdded = false;
    if (users.contains(aUser)) { return false; }
    TheGroceryStoreSystem existingTheGroceryStoreSystem = aUser.getTheGroceryStoreSystem();
    boolean isNewTheGroceryStoreSystem = existingTheGroceryStoreSystem != null && !this.equals(existingTheGroceryStoreSystem);
    if (isNewTheGroceryStoreSystem)
    {
      aUser.setTheGroceryStoreSystem(this);
    }
    else
    {
      users.add(aUser);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUser(User aUser)
  {
    boolean wasRemoved = false;
    //Unable to remove aUser, as it must always have a theGroceryStoreSystem
    if (!this.equals(aUser.getTheGroceryStoreSystem()))
    {
      users.remove(aUser);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addUserAt(User aUser, int index)
  {  
    boolean wasAdded = false;
    if(addUser(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserAt(User aUser, int index)
  {
    boolean wasAdded = false;
    if(users.contains(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserAt(aUser, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOrders()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Order addOrder(int aOrderID, boolean aCompleted, Date aOrderDate, int aTotal, String aPayment, Cart aCart)
  {
    return new Order(aOrderID, aCompleted, aOrderDate, aTotal, aPayment, aCart, this);
  }

  public boolean addOrder(Order aOrder)
  {
    boolean wasAdded = false;
    if (orders.contains(aOrder)) { return false; }
    TheGroceryStoreSystem existingTheGroceryStoreSystem = aOrder.getTheGroceryStoreSystem();
    boolean isNewTheGroceryStoreSystem = existingTheGroceryStoreSystem != null && !this.equals(existingTheGroceryStoreSystem);
    if (isNewTheGroceryStoreSystem)
    {
      aOrder.setTheGroceryStoreSystem(this);
    }
    else
    {
      orders.add(aOrder);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOrder(Order aOrder)
  {
    boolean wasRemoved = false;
    //Unable to remove aOrder, as it must always have a theGroceryStoreSystem
    if (!this.equals(aOrder.getTheGroceryStoreSystem()))
    {
      orders.remove(aOrder);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOrderAt(Order aOrder, int index)
  {  
    boolean wasAdded = false;
    if(addOrder(aOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrders()) { index = numberOfOrders() - 1; }
      orders.remove(aOrder);
      orders.add(index, aOrder);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOrderAt(Order aOrder, int index)
  {
    boolean wasAdded = false;
    if(orders.contains(aOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrders()) { index = numberOfOrders() - 1; }
      orders.remove(aOrder);
      orders.add(index, aOrder);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOrderAt(aOrder, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfProducts()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Product addProduct(String aProductID, String aIsAvailableOnline, int aPrice, int aStock)
  {
    return new Product(aProductID, aIsAvailableOnline, aPrice, aStock, this);
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
  public TimeSlot addTimeSlot(Time aStartTime, Time aEndTime, int aMaxOrderPerSlot, TimeSlot.ShoppingType aShoppingType)
  {
    return new TimeSlot(aStartTime, aEndTime, aMaxOrderPerSlot, aShoppingType, this);
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
  public Shifts addShift(Time aStartHour, Time aEndHour, Days aDays)
  {
    return new Shifts(aStartHour, aEndHour, aDays, this);
  }

  public boolean addShift(Shifts aShift)
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

  public boolean removeShift(Shifts aShift)
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
  public boolean addShiftAt(Shifts aShift, int index)
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

  public boolean addOrMoveShiftAt(Shifts aShift, int index)
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfStoreInfos()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public StoreInfo addStoreInfo(String aTelephone, String aEmail, StoreOwner aStoreOwner, Address aAddress)
  {
    return new StoreInfo(aTelephone, aEmail, aStoreOwner, aAddress, this);
  }

  public boolean addStoreInfo(StoreInfo aStoreInfo)
  {
    boolean wasAdded = false;
    if (storeInfos.contains(aStoreInfo)) { return false; }
    TheGroceryStoreSystem existingTheGroceryStoreSystem = aStoreInfo.getTheGroceryStoreSystem();
    boolean isNewTheGroceryStoreSystem = existingTheGroceryStoreSystem != null && !this.equals(existingTheGroceryStoreSystem);
    if (isNewTheGroceryStoreSystem)
    {
      aStoreInfo.setTheGroceryStoreSystem(this);
    }
    else
    {
      storeInfos.add(aStoreInfo);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeStoreInfo(StoreInfo aStoreInfo)
  {
    boolean wasRemoved = false;
    //Unable to remove aStoreInfo, as it must always have a theGroceryStoreSystem
    if (!this.equals(aStoreInfo.getTheGroceryStoreSystem()))
    {
      storeInfos.remove(aStoreInfo);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addStoreInfoAt(StoreInfo aStoreInfo, int index)
  {  
    boolean wasAdded = false;
    if(addStoreInfo(aStoreInfo))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStoreInfos()) { index = numberOfStoreInfos() - 1; }
      storeInfos.remove(aStoreInfo);
      storeInfos.add(index, aStoreInfo);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveStoreInfoAt(StoreInfo aStoreInfo, int index)
  {
    boolean wasAdded = false;
    if(storeInfos.contains(aStoreInfo))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStoreInfos()) { index = numberOfStoreInfos() - 1; }
      storeInfos.remove(aStoreInfo);
      storeInfos.add(index, aStoreInfo);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addStoreInfoAt(aStoreInfo, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDays()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Days addDay(Days.WeekDays aDays, Time aStoreStartHour, Time aStoreEndHour, StoreInfo aStoreInfo)
  {
    return new Days(aDays, aStoreStartHour, aStoreEndHour, aStoreInfo, this);
  }

  public boolean addDay(Days aDay)
  {
    boolean wasAdded = false;
    if (days.contains(aDay)) { return false; }
    TheGroceryStoreSystem existingTheGroceryStoreSystem = aDay.getTheGroceryStoreSystem();
    boolean isNewTheGroceryStoreSystem = existingTheGroceryStoreSystem != null && !this.equals(existingTheGroceryStoreSystem);
    if (isNewTheGroceryStoreSystem)
    {
      aDay.setTheGroceryStoreSystem(this);
    }
    else
    {
      days.add(aDay);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeDay(Days aDay)
  {
    boolean wasRemoved = false;
    //Unable to remove aDay, as it must always have a theGroceryStoreSystem
    if (!this.equals(aDay.getTheGroceryStoreSystem()))
    {
      days.remove(aDay);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addDayAt(Days aDay, int index)
  {  
    boolean wasAdded = false;
    if(addDay(aDay))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDays()) { index = numberOfDays() - 1; }
      days.remove(aDay);
      days.add(index, aDay);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveDayAt(Days aDay, int index)
  {
    boolean wasAdded = false;
    if(days.contains(aDay))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDays()) { index = numberOfDays() - 1; }
      days.remove(aDay);
      days.add(index, aDay);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addDayAt(aDay, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (users.size() > 0)
    {
      User aUser = users.get(users.size() - 1);
      aUser.delete();
      users.remove(aUser);
    }
    
    while (orders.size() > 0)
    {
      Order aOrder = orders.get(orders.size() - 1);
      aOrder.delete();
      orders.remove(aOrder);
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
      Shifts aShift = shifts.get(shifts.size() - 1);
      aShift.delete();
      shifts.remove(aShift);
    }
    
    while (storeInfos.size() > 0)
    {
      StoreInfo aStoreInfo = storeInfos.get(storeInfos.size() - 1);
      aStoreInfo.delete();
      storeInfos.remove(aStoreInfo);
    }
    
    while (days.size() > 0)
    {
      Days aDay = days.get(days.size() - 1);
      aDay.delete();
      days.remove(aDay);
    }
    
  }

}