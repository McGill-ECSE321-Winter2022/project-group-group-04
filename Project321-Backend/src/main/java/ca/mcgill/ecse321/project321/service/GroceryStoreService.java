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
import ca.mcgill.ecse321.project321.dao.InStoreBillRepository;
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
import ca.mcgill.ecse321.project321.model.InStoreBill;
import ca.mcgill.ecse321.project321.model.Order;
import ca.mcgill.ecse321.project321.model.Product;
import ca.mcgill.ecse321.project321.model.Shift;
import ca.mcgill.ecse321.project321.model.Store;
import ca.mcgill.ecse321.project321.model.StoreOwner;
import ca.mcgill.ecse321.project321.model.TimeSlot;
import ca.mcgill.ecse321.project321.model.Product.PriceType;

@Service
public class GroceryStoreService {

	@Autowired
	private TimeslotRepository timeslotRepository;
	@Autowired
	private StoreRepository storeRepository;
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
	private InStoreBillRepository inStoreBillRepository;
	@Autowired
	private UserRepository userRepository;

    /* This class will contain the methods to interact with the CRUD repository. We will be creating the objects
       and updating the database from these methods */ 

    /* Customer-related service methods -------------------------------------------------------------------------- */
    @Transactional
    public Customer createCustomer(String email, String name, String password, String phone, Address address) {
        if( customerRepository.findByEmail(email) != null ) return null; // Customer already exists
        Customer customer = new Customer(email, name, password, phone, address);
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

    /* Cart-related service methods ------------------------------------------------------------------------------ */
    @Transactional
    public Cart createCart(Cart.ShoppingType type, Customer customer, Date creationDate, Time creationTime) {
        Cart cart = new Cart(type, customer, creationDate, creationTime);
        return cart;
    }

    @Transactional
    public Cart createCart(Cart.ShoppingType type, Customer customer, Date creationDate, Time creationTime, TimeSlot timeSlot) {
        Cart cart = new Cart(type, customer, creationDate, creationTime);
        cart.setTimeSlot(timeSlot);
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
    public TimeSlot createTimeSlot(Time startTime, Time endTime, Date date, int maxOrderPerSlot) {
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
        Order order = new Order(completed, date, total, payment, cart);
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
        return cartItem;
    }

    @Transactional
    public CartItem createCartItem(int quantity, Product product, InStoreBill inStoreBill) {
        if( productRepository.findByProductName(product.getProductName()) == null ) return null; // Product does not exist
        CartItem cartItem = new CartItem(quantity, product);
        cartItem.setInStoreBill(inStoreBill);
        return cartItem;
    }

    @Transactional
    public List<CartItem> getCartItemsByCart(Cart cart) {
        return cartItemRepository.findByCart(cart);
    }

    @Transactional
    public List<CartItem> getCartItemsByInStoreBill(InStoreBill inStoreBill) {
        return cartItemRepository.findByInStoreBill(inStoreBill);
    }

    @Transactional
    public CartItem getCartItemByProductAndCart(Product product, Cart cart) {
        return cartItemRepository.findByCartAndProduct(cart, product);
    }

    @Transactional
    public CartItem getCartItemByProductAndInStoreBill(Product product, InStoreBill inStoreBill) {
        return cartItemRepository.findByInStoreBillAndProduct(inStoreBill, product);
    }

    @Transactional
    public List<CartItem> getCartItemsByProductAndQuantity(Product product, int quantity) {
        return cartItemRepository.findByProductAndQuantity(product, quantity);
    }

    @Transactional
    public List<CartItem> getAllCartItems() {
        return toList(cartItemRepository.findAll());
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
        Address a = new Address(town, street, postalCode, unit);
        addressRepository.save(a);
        return a;
    }
    /* Employee-related service methods -------------------------------------------------------------------------- */

    @Transactional
    public Employee createEmployee(String email, String name, String password, Employee.EmployeeStatus status) {
        if( employeeRepository.findByEmail(email) != null ) return null; // Customer already exists
        Employee employee = new Employee(email, name, password, status);
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
    
    /* In-Store Bill-related service methods --------------------------------------------------------------------- */

    /* Product-related service methods --------------------------------------------------------------------------- */

    @Transactional
    public List<Product> getAllProduct() {
        return toList(productRepository.findAll());
    }
    
    @Transactional
    public Product getAllProductByName(String name) {
        return productRepository.findByProductName(name);
    }
    
    @Transactional
    public List<Product> getProductByStockGreaterThan(int limit) {
    	return toList(productRepository.findByStockGreaterThan(limit));
    }
    
    @Transactional
    public Product createProduct(PriceType priceType, String productName, String isAvailableOnline, int price, int stock) {
        if(productRepository.findByProductName(productName) != null ) return null; // Product already exists
        Product product = new Product(priceType, productName, isAvailableOnline, price, stock);
        productRepository.save(product);
        return product;
    }
    
    @Transactional
    public Product deleteProduct(String productName) {
    	Product p = productRepository.findByProductName(productName);
        if(p == null ) return null; // Product do not exists
        productRepository.delete(p);
        return p;
    }
    
    /* Shift-related service methods ----------------------------------------------------------------------------- */

    @Transactional
    public Shift createShift(Time startHour, Time endHour, Date date, Employee employee) {
        if(shiftRepository.findByDateAndEmployee(date, employee) != null ) return null; // Customer already exists
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
            return null;
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
    	if (storeRepository.findByAddress(address) != null) return null;
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
        if( storeOwnerRepository.findByEmail(email) != null ) return null; 
        StoreOwner storeOwner = new StoreOwner(email, name, password);
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
            return null;
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
    public List<TimeSlot> getAllTimeSlots() {
        return toList(timeslotRepository.findAll());
    }

    @Transactional
    public TimeSlot getTimeSlot(Date date, Time startTime, Time endTime) {
        return timeslotRepository.findByDateAndStartTimeAndEndTime(date, startTime, endTime);
    }

    @Transactional
    public List<TimeSlot> getTimeSlotsBetween(Time lowerThreshold, Time upperThreshold) {
        List<TimeSlot> extendedList = timeslotRepository.findByStartTimeBetween(lowerThreshold, upperThreshold);
        extendedList.addAll(timeslotRepository.findByEndTimeBetween(lowerThreshold, upperThreshold));
        List<TimeSlot> finalList = new ArrayList<TimeSlot>();
        for(TimeSlot t1 : extendedList) {
            boolean contains = false;
            for(TimeSlot t2: finalList) {
                if(t1.getDate().equals(t2.getDate()) && t1.getStartTime().equals(t2.getStartTime()) && 
                    t1.getEndTime().equals(t2.getEndTime())) {
                    contains = true;
                    break;
                }
            }
            if(!contains) {
                contains = false;
                finalList.add(t1);
            }
        }
        return null;
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
    
    /* Helper methods -------------------------------------------------------------------------------------------- */
    private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
	    	resultList.add(t);
	    }
		return resultList;
	}
}