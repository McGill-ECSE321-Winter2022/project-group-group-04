package ca.mcgill.ecse321.project321.service;

import java.sql.Date;
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
import ca.mcgill.ecse321.project321.dto.EmployeeDTO.EmployeeStatusDTO;
import ca.mcgill.ecse321.project321.model.Address;
import ca.mcgill.ecse321.project321.model.Cart;
import ca.mcgill.ecse321.project321.model.CartItem;
import ca.mcgill.ecse321.project321.model.Customer;
import ca.mcgill.ecse321.project321.model.Employee;
import ca.mcgill.ecse321.project321.model.InStoreBill;
import ca.mcgill.ecse321.project321.model.Order;
import ca.mcgill.ecse321.project321.model.Product;
import ca.mcgill.ecse321.project321.model.StoreOwner;
import ca.mcgill.ecse321.project321.model.TimeSlot;

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
    public Cart createCart(Cart.ShoppingType type, Customer customer) {
        Cart cart = new Cart(type, customer);
        return cart;
    }

    @Transactional
    public Cart createCart(Cart.ShoppingType type, Customer customer, TimeSlot timeSlot) {
        Cart cart = new Cart(type, customer);
        cart.setTimeSlot(timeSlot);
        return cart;
    }

    @Transactional
    public List<Cart> getCartsByCustomer(Customer customer) {
        return cartRepository.findByCustomer(customer);
    }

    @Transactional
    public List<Cart> getAllCarts() {
        return toList(cartRepository.findAll());
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
    
    /* In-Store Bill-related service methods --------------------------------------------------------------------- */

    /* Product-related service methods --------------------------------------------------------------------------- */

    /* Shift-related service methods ----------------------------------------------------------------------------- */

    /* Store-related service methods ----------------------------------------------------------------------------- */

    /* Store Owner-related service methods ----------------------------------------------------------------------- */

    @Transactional
    public StoreOwner createStoreOwner(String email, String name, String password) {
        if( storeOwnerRepository.findByEmail(email) != null ) return null; // Customer already exists
        StoreOwner storeOwner = new StoreOwner(email, name, password);
        storeOwnerRepository.save(storeOwner);
        return storeOwner;
    }
    
    @Transactional
    public StoreOwner getStoreOwner(String email) {
        return storeOwnerRepository.findByEmail(email);
    }
    
    
    
    /* Time Slot-related service methods ------------------------------------------------------------------------- */
    @Transactional
    public List<TimeSlot> getAllTimeSlots() {
        return toList(timeslotRepository.findAll());
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