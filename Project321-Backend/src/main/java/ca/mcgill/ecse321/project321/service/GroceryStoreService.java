package ca.mcgill.ecse321.project321.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.project321.dao.AddressRepository;
import ca.mcgill.ecse321.project321.dao.CartItemRepository;
import ca.mcgill.ecse321.project321.dao.CartRepository;
import ca.mcgill.ecse321.project321.dao.CustomerRepository;
import ca.mcgill.ecse321.project321.dao.EmployeeRepository;
import ca.mcgill.ecse321.project321.dao.InStorePurchaseRepository;
import ca.mcgill.ecse321.project321.dao.OrderRepository;
import ca.mcgill.ecse321.project321.dao.ProductRepository;
import ca.mcgill.ecse321.project321.dao.ShiftRepository;
import ca.mcgill.ecse321.project321.dao.StoreOwnerRepository;
import ca.mcgill.ecse321.project321.dao.StoreRepository;
import ca.mcgill.ecse321.project321.dao.TimeslotRepository;
import ca.mcgill.ecse321.project321.dao.UserRepository;
import ca.mcgill.ecse321.project321.model.Address;
import ca.mcgill.ecse321.project321.model.Cart;
import ca.mcgill.ecse321.project321.model.CartItem;
import ca.mcgill.ecse321.project321.model.Customer;
import ca.mcgill.ecse321.project321.model.Employee;
import ca.mcgill.ecse321.project321.model.InStorePurchase;
import ca.mcgill.ecse321.project321.model.Order;
import ca.mcgill.ecse321.project321.model.Product;
import ca.mcgill.ecse321.project321.model.Shift;
import ca.mcgill.ecse321.project321.model.Store;
import ca.mcgill.ecse321.project321.model.StoreOwner;
import ca.mcgill.ecse321.project321.model.TimeSlot;
import ca.mcgill.ecse321.project321.model.User;
import ca.mcgill.ecse321.project321.model.Product.PriceType;

@Service
public class GroceryStoreService {

	@Autowired
	private TimeslotRepository timeslotRepository;
	@Autowired 
	StoreRepository storeRepository;
    @Autowired
    private StoreOwnerRepository storeOwnerRepository;
    @Autowired
    private ShiftRepository shiftRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private AddressRepository addressRepository;
	@Autowired
	private InStorePurchaseRepository inStorePurchaseRepository;
	@Autowired
	private UserRepository userRepository;

    /* This class will contain the methods to interact with the CRUD repository. We will be creating the objects
       and updating the database from these methods */ 

    @Transactional
    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }
	
    /* Customer-related service methods -------------------------------------------------------------------------- */
    @Transactional
    public Customer createCustomer(String email, String name, String password, String phone, Address address) {
        if( customerRepository.findByEmail(email) != null ) {
        	throw new IllegalArgumentException("Customer with this email already exists");
    
        }
        
        if (email == null || email.trim().length() == 0 
                || name == null || name.trim().length() == 0
                || password == null || password.trim().length() == 0) {
               	throw new IllegalArgumentException("email, name, and password of customer all needs to be associated with a non-empty string");
               }
               
        if (password.length() < 6) {
            throw new IllegalArgumentException("Customer account password should be longer than 6 alphabet/numbers");
            }
               
        Customer customer = new Customer(email, name, password, phone, address);
        userRepository.save(customer);
        customerRepository.save(customer); 
        return customer;
    }

    @Transactional
    public Customer getCustomer(String email) {
        return customerRepository.findByEmail(email);
    }

    @Transactional
    public List<Customer> getAllCustomers() {
        return toList(customerRepository.findAll());
    }

    @Transactional
    public Customer setCustomersAddress(String customerEmail, String town, String street, String postalcode, int unit) {
        Customer c = customerRepository.findByEmail(customerEmail);
        if(c == null) return null;
        c.setAddress(new Address(town, street, postalcode, unit));
        customerRepository.save(c);
        return c;
    }

    /* Cart-related service methods ------------------------------------------------------------------------------ */
    @Transactional
    public Cart createCart(Cart.ShoppingType type, Customer customer, Date creationDate, Time creationTime) {
        Cart cart = new Cart(type, customer, creationDate, creationTime);
        cartRepository.save(cart);
        return cart;
    }
    
    @Transactional
    public List<Cart> getCartsByCustomer(Customer customer) {
        return cartRepository.findByCustomer(customer);
    }

    @Transactional
    public Cart getCartByCustomerAndDateAndTime(Customer customer, Date date, Time time) {
        return cartRepository.findByCustomerAndCreationDateAndCreationTime(customer, date, time);
    }

    @Transactional
    public List<Cart> getAllCarts() {
        return toList(cartRepository.findAll());
    }

    @Transactional
    public List<Cart> getCartsByTimeSlot(TimeSlot timeSlot) {
        return cartRepository.findByTimeSlot(timeSlot);
    }

    @Transactional
    public List<Cart> getCartsByType(Cart.ShoppingType type) {
        return cartRepository.findByType(type);
    }

    /* Order-related service methods ----------------------------------------------------------------------------- */
    @Transactional
    public Order createOrder(boolean completed, Date date, int total, String payment, Cart cart) {
        if( orderRepository.findByCart(cart) != null ) return null; // An order is already attached to this cart
        
        if (date == null || payment == null || cart == null) {
           	throw new IllegalArgumentException("Any of the date, payment, and cart of a order cannot be null");
           }
        
        Order order = new Order(completed, date, total, payment, cart);
        orderRepository.save(order);
        return order;
    }

    @Transactional
    public Order getOrderByCart(Cart cart) {
        return orderRepository.findByCart(cart);
    }

    @Transactional
    public List<Order> getOrdersByCustomer(Customer customer) {
        List<Cart> carts = cartRepository.findByCustomer(customer);
        List<Order> orders = new ArrayList<Order>();
        for( Cart c : carts ) {
            Order o = orderRepository.findByCart(c);
            if(o != null) {
                orders.add(o);
            }
        }
        return orders;
    }

    @Transactional
    public List<Order> getAllOrders() {
        return toList(orderRepository.findAll());
    }

    @Transactional
    public boolean setPayment(Order order, String payment) {
        order.setPayment(payment);
        orderRepository.save(order);
        return true;
    }

    @Transactional
    public boolean setCompleted(Order order) {
        order.setCompleted(true);
        orderRepository.save(order);
        return true;
    }

    /* Cart Item-related service methods ------------------------------------------------------------------------- */
    @Transactional
    public CartItem createCartItem(int quantity, Product product, Cart cart) {
        if( productRepository.findByProductName(product.getProductName()) == null ) return null; // Product does not exist
        CartItem cartItem = new CartItem(quantity, product);
        cartItem.setCart(cart);
        cartItemRepository.save(cartItem);
        return cartItem;
    }

    @Transactional
    public CartItem createCartItem(int quantity, Product product, InStorePurchase inStorePurchase) {
        if( productRepository.findByProductName(product.getProductName()) == null ) return null; // Product does not exist
        CartItem cartItem = new CartItem(quantity, product);
        cartItem.setInStorePurchase(inStorePurchase);
        cartItemRepository.save(cartItem);
        return cartItem;
    }

    @Transactional
    public List<CartItem> getCartItemsByCart(Cart cart) {
        List<CartItem> list = cartItemRepository.findByCart(cart);
        return list;
    }

    @Transactional
    public List<CartItem> getCartItemsByInStorePurchase(InStorePurchase inStorePurchase) {
        return cartItemRepository.findByInStorePurchase(inStorePurchase);
    }

    @Transactional
    public CartItem getCartItemByProductAndCart(Product product, Cart cart) {
        return cartItemRepository.findByCartAndProduct(cart, product);
    }

    @Transactional
    public CartItem getCartItemByProductAndInStorePurchase(Product product, InStorePurchase inStorePurchase) {
        return cartItemRepository.findByInStorePurchaseAndProduct(inStorePurchase, product);
    }

    @Transactional
    public List<CartItem> getCartItemsByProductAndQuantity(Product product, int quantity) {
        return cartItemRepository.findByProductAndQuantity(product, quantity);
    }

    @Transactional
    public List<CartItem> getAllCartItems() {
        return toList(cartItemRepository.findAll());
    }

    @Transactional
    public void deleteCartItem(CartItem item) {
        if(item == null) {
        	throw new IllegalArgumentException ("Item you are trying to delete is null");
        }
        cartItemRepository.delete(item);
    }

    /* Address-related service methods --------------------------------------------------------------------------- */
    @Transactional
    public List<Address> getAllAddresses() {
        return toList(addressRepository.findAll());
    }
    
    @Transactional
    public Address getAddresseByUnitAndStreetAndTownAndPostalCode(int unit, String street, String town, String postalCode) {
        return addressRepository.findByUnitAndStreetAndTownAndPostalCode(unit, street, town, postalCode);
    }
        
    @Transactional
    public Address createAddresses(String town, String street, String postalCode, int unit) {
    	if( addressRepository.findByUnitAndStreetAndTownAndPostalCode(unit, street, town, postalCode) != null ) return null; // Customer already exists
    	
        if (town == null || town.trim().length() == 0 
                || street == null || street.trim().length() == 0
                || postalCode == null || postalCode.trim().length() == 0) {
               	throw new IllegalArgumentException("town, street, and postalCode of address all needs to be associated with a non-empty string");
               }
        
        Address a = new Address(town, street, postalCode, unit);
        addressRepository.save(a);
        return a;
    }
    /* Employee-related service methods -------------------------------------------------------------------------- */

    @Transactional
    public Employee createEmployee(String email, String name, String password, Employee.EmployeeStatus status) {
        if( employeeRepository.findByEmail(email) != null ) return null; // Customer already exists
        
        if (email == null || email.trim().length() == 0 
         || name == null || name.trim().length() == 0
         || password == null || password.trim().length() == 0) {
        	throw new IllegalArgumentException("email, name, and password of employee all needs to be associated with a non-empty string");
        }
        
        if (password.length() < 6) {
        	throw new IllegalArgumentException("Employee account password should be longer than 6 alphabet/numbers");
        }
        
        Employee employee = new Employee(email, name, password, status);
        userRepository.save(employee);
        employeeRepository.save(employee);
        return employee;
    }
    
    @Transactional
    public Employee getEmployee(String email) {
        return employeeRepository.findByEmail(email);
    }
    
    @Transactional
    public List<Employee> getAllEmployee() {
        return toList(employeeRepository.findAll());
    }
    
    @Transactional
    public void removeEmployee(Employee employee) {
    	employeeRepository.delete(employee);
    }
    
    /* In-Store Purchase-related service methods --------------------------------------------------------------------- */
    @Transactional
    public InStorePurchase createInStorePurchase(int total, Date purchaseDate) {
        InStorePurchase purchase = new InStorePurchase(total, purchaseDate);
        inStorePurchaseRepository.save(purchase);
        return purchase;
    }

    @Transactional
    public InStorePurchase setInStorePurchaseTotal(int total, InStorePurchase inStorePurchase) {
        inStorePurchase.setTotal(total);
        inStorePurchaseRepository.save(inStorePurchase);
        return inStorePurchase;
    }

    @Transactional
    public List<InStorePurchase> getAllInStorePurchases() {
        return toList(inStorePurchaseRepository.findAll());
    }

    /* Product-related service methods --------------------------------------------------------------------------- */

    @Transactional
    public List<Product> getAllProduct() {
        return toList(productRepository.findAll());
    }
    
    @Transactional
    public Product getProductByName(String name) {
        return productRepository.findByProductName(name);
    }
    
    @Transactional
    public List<Product> getProductByStockGreaterThan(int limit) {
    	return toList(productRepository.findByStockGreaterThan(limit));
    }
    
    @Transactional
    public Product createProduct(PriceType priceType, String productName, String isAvailableOnline, int price, int stock) {
        if(productRepository.findByProductName(productName) != null ) return null; // Product already exists
        
        if (priceType == null || productName == null || productName.trim().length() == 0 || isAvailableOnline == null || isAvailableOnline.trim().length() == 0) {
           	throw new IllegalArgumentException("Any of the priceType, productName and isAvailableOnline of a product cannot be null");
        }  
        
        if (price < 0) {
        	throw new IllegalArgumentException("price cannot be negative");
        }
        
        if (stock < 0) {
        	throw new IllegalArgumentException("stock cannot be negative");
        }
        
        Product product = new Product(priceType, productName, isAvailableOnline, price, stock);
        productRepository.save(product);
        return product;
    }
    
    @Transactional
    public Product deleteProduct(String productName) {
    	Product p = productRepository.findByProductName(productName);
        if(p == null ) {
        	throw new IllegalArgumentException ("Cannot find Product to delete"); // Product do not exists
        }else {
        	 productRepository.delete(p);
             return p;
        }
       
    }

    @Transactional
    public Product setProductStock(String productName, int stock) {
        Product p = productRepository.findByProductName(productName);
        if(p == null ) return null;
        p.setStock(stock);
        productRepository.save(p);
        return p;
    }

    /* Shift-related service methods ----------------------------------------------------------------------------- */

    @Transactional
    public Shift createShift(Time startHour, Time endHour, Date date, Employee employee) {
        if(shiftRepository.findByDateAndEmployee(date, employee) != null ) return null; // Customer already exists
        
        if (date == null || startHour == null || endHour == null) {
           	throw new IllegalArgumentException("startHour, endHour and date of a shift cannot be null");
           }     
        
        if (employee == null) {
           	throw new IllegalArgumentException("the employee that the shift is assigned to cannot be null");
           }   
        
        Shift shift = new Shift(startHour, endHour, date, employee);
        shiftRepository.save(shift);
        return shift;
    }
    
    @Transactional
    public List<Shift> getShiftByEmployee(Employee employee) {
    	return toList(shiftRepository.findByEmployee(employee));
    }
    
    @Transactional
    public List<Shift> getAllShifts() {
    	return toList(shiftRepository.findAll());
    }
    
    /* Store-related service methods ----------------------------------------------------------------------------- */
    
    @Transactional
    public Store getStore() {
        List<Store> storeList = toList(storeRepository.findAll());
        if(storeList.size() == 0) {
           throw new IllegalArgumentException ("There is no Store in Repository");
        } else {
            return storeList.get(0);
        }
    }
    
    @Transactional
    public Store deleteStore(Store s) {
        storeRepository.delete(s);
        return s;
    }
    
    @Transactional
    public Store createStore(String telephone, String email, Time openingHour, 
                             Time closingHour, StoreOwner storeOwner, Address address, int outOfTownFee) {
    	if (storeRepository.findByAddress(address) != null) {
    		return null;
    	}
    		
    	Store store = new Store(telephone, email, openingHour, closingHour, storeOwner, address, outOfTownFee);
    	storeRepository.save(store);
        return store;
    }
    
    @Transactional
    public Store createStore(Store store) {
    	storeRepository.save(store);
        return store;
    }

    @Transactional
    public Store setOwner(Store store, StoreOwner storeOwner) {
        store.setStoreOwner(storeOwner);
        storeRepository.save(store);
        return store;
    }

    /* Store Owner-related service methods ----------------------------------------------------------------------- */

    @Transactional
    public StoreOwner createStoreOwner(String email, String name, String password) {
        if( storeOwnerRepository.findByEmail(email) != null ) {
        	throw new IllegalArgumentException ("A StoreOwner exists already");
        }
        
        if (email == null || email.trim().length() == 0 
                || name == null || name.trim().length() == 0
                || password == null || password.trim().length() == 0) {
               	throw new IllegalArgumentException("email, name, and password of storeOwner all needs to be associated with a non-empty string");
               }
               
        if (password.length() < 6) {
            throw new IllegalArgumentException("Owner account password should be longer than 6 alphabet/numbers");
            }
        
        StoreOwner storeOwner = new StoreOwner(email, name, password);
        userRepository.save(storeOwner);
        storeOwnerRepository.save(storeOwner);
        return storeOwner;
    }
    
    @Transactional
    public StoreOwner getStoreOwner() {
        List<StoreOwner> list = toList(storeOwnerRepository.findAll());
        if(list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }
    
    @Transactional
    public StoreOwner setStoreOwnerInfo(String email, String name, String password) {
        List<StoreOwner> list = toList(storeOwnerRepository.findAll());
        if(list.size() == 0) {
           throw new IllegalArgumentException ("No Store Owner Account found");
        } else {
            StoreOwner owner = list.get(0);
            owner.setEmail(email);
            owner.setName(name);
            owner.setPassword(password);
            storeOwnerRepository.save(owner);
            return owner;
        }
    }
    
    /* Time Slot-related service methods ------------------------------------------------------------------------- */
    
    @Transactional
    public TimeSlot createTimeSlot(Time startTime, Time endTime, Date date, int maxOrderPerSlot) {
        if(timeslotRepository.findByDateAndStartTimeAndEndTime(date, startTime, endTime) != null) return null; // Already exists
        
        if (startTime == null || endTime == null || date == null) {
               	throw new IllegalArgumentException("Any of the startTime, endTime, and date of a timeSlot cannot be null");
               }
        if (startTime.after(endTime)){
           	throw new IllegalArgumentException("Event end time cannot be before start time!");
           }
        
    	TimeSlot ts = new TimeSlot(startTime, endTime, date, maxOrderPerSlot);
    	timeslotRepository.save(ts);
        return ts;
    }
    
    @Transactional
    public Cart setTimeSlot(Cart cart, TimeSlot timeSlot) {
        cart.setTimeSlot(timeSlot);
        cartRepository.save(cart);
        return cart;
    }
    
    @Transactional
    public List<TimeSlot> getAllTimeSlots() {
        return toList(timeslotRepository.findAll());
    }

    @Transactional
    public TimeSlot getTimeSlot(Date date, Time startTime, Time endTime) {
        return timeslotRepository.findByDateAndStartTimeAndEndTime(date, startTime, endTime);
    }

    @Transactional
    public List<TimeSlot> getTimeSlotsBetween(Date date, Time lowerThreshold, Time upperThreshold) {
        List<TimeSlot> extendedList = timeslotRepository.findByDate(date);
        List<TimeSlot> finalList = new ArrayList<TimeSlot>();
        for(TimeSlot t : extendedList) {
            if(lowerThreshold.before(t.getStartTime()) && upperThreshold.after(t.getEndTime())) {
                finalList.add(t);
            }
        }
        return finalList;
    }

    @Transactional
    public boolean incrementMaxOrderPerslot(TimeSlot timeSlot) {
        timeSlot.setMaxOrderPerSlot(timeSlot.getMaxOrderPerSlot() + 1);
        timeslotRepository.save(timeSlot);
        return true;
    }

    @Transactional
    public boolean decrementMaxOrderPerslot(TimeSlot timeSlot) {
        int maxOrderPerSlot = timeSlot.getMaxOrderPerSlot();
        if(maxOrderPerSlot > 0) {
            timeSlot.setMaxOrderPerSlot(maxOrderPerSlot - 1);
            timeslotRepository.save(timeSlot);
        }
        return true;
    }

    @Transactional
    public int getEmployeesForTimeSlot(Date date, Time timeslotStartTime, Time timeSlotEndTime) {
        List<Shift> extendedList = shiftRepository.findByDate(date);
        List<Shift> finalList = new ArrayList<Shift>();
        for(Shift s : extendedList) {
            if((timeslotStartTime.after(s.getStartHour()) && timeSlotEndTime.before(s.getEndHour())) || (timeslotStartTime.equals(s.getStartHour()) && timeSlotEndTime.equals(s.getEndHour()))) {
                finalList.add(s);
            }
        }
        return finalList.size();
    }
    
    @Transactional
    public void deleteTimeSlot(Time startTime, Time endTime, Date date) {
        TimeSlot t = timeslotRepository.findByDateAndStartTimeAndEndTime(date, startTime, endTime);
        if(t == null) {
        	throw new IllegalArgumentException ("Unable to find TimeSlot");
  
        }
        timeslotRepository.delete(t);
    }
    
    /* Helper methods -------------------------------------------------------------------------------------------- */
    private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
	    	resultList.add(t);
	    }
		return resultList;
	}
}