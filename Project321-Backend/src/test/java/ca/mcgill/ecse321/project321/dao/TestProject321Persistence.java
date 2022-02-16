package ca.mcgill.ecse321.project321.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

import ca.mcgill.ecse321.project321.model.Address;
import ca.mcgill.ecse321.project321.model.Cart;
import ca.mcgill.ecse321.project321.model.CartItem;
import ca.mcgill.ecse321.project321.model.Customer;
import ca.mcgill.ecse321.project321.model.Day;
import ca.mcgill.ecse321.project321.model.Order;
import ca.mcgill.ecse321.project321.model.Product;
import ca.mcgill.ecse321.project321.model.StoreOwner;
import ca.mcgill.ecse321.project321.model.TheGroceryStoreSystem;
import ca.mcgill.ecse321.project321.model.TimeSlot;
import ca.mcgill.ecse321.project321.model.Cart.ShoppingType;
import ca.mcgill.ecse321.project321.model.Day.WeekDays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestProject321Persistence {

	// @Autowired
	// private UserRepository userRepository;
	@Autowired
	private TimeslotRepository timeslotRepository;
	// @Autowired
	// private TheGroceryStoreSystemRepository tGSRepository;
    // @Autowired
    // private StoreOwnerRepository storeOwnerRepository;
    @Autowired
    private ShiftRepository shiftRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DayRepository dayRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private AddressRepository addressRepository;

	@AfterEach
	public void clearDatabase() {
		// First, we clear registrations to avoid exceptions due to inconsistencies
		orderRepository.deleteAll();
        shiftRepository.deleteAll();
        cartRepository.deleteAll();
        cartItemRepository.deleteAll();
        productRepository.deleteAll();
        timeslotRepository.deleteAll();
        dayRepository.deleteAll();
        employeeRepository.deleteAll();
        customerRepository.deleteAll();
        addressRepository.deleteAll();
		// Then we can clear the other tables
	}

	@Test
    public void testPersistAndLoadAddress() {
		  String town = "TestTown";
		  String street = "TestStreet";
		  String postalCode = "TestPostalCode";
		  int unit = 321;
		  
		  Address address = new Address(town,street,postalCode,unit);
		  addressRepository.save(address);
		  
		  address = null;
		  
		  List<Address> addresslist = addressRepository.findByStreet(street);
		  address = addresslist.get(0);
		  assertNotNull(address);
		  assertEquals(address.getTown(),town);
		  assertEquals(address.getStreet(),street);
		  assertEquals(address.getPostalCode(),postalCode);
		  assertEquals(address.getUnit(),unit);
		
	}

	@Test
    public void testPersistAndLoadCartItem() {
		String cartItemID = "cartItem321";
		int quantity = 1;
		Product product = new Product();
		
		CartItem cartItem = new CartItem(cartItemID, quantity,product);
		cartItemRepository.save(cartItem);
		
		cartItem=null;
		
		List<CartItem> cartItemlist = cartItemRepository.findByProductAndQuantity(product, quantity);
		cartItem = cartItemlist.get(0);
		assertNotNull(cartItem);
		assertEquals(cartItem.getCartItemID(),cartItemID);
		assertEquals(cartItem.getQuantity(),quantity);
		
	}
	
	@Test
    public void testPersistAndLoadCart() {
		
		  String cartID = "cart321";
		  ShoppingType type = ShoppingType.Delivery;
		  
		  Customer customer = new Customer();
		  TimeSlot timeSlot = new TimeSlot();
		  Order order= new order();
		  
		  Cart cart = new Cart(cartID, type, customer, timeSlot, order);
		  cartRepository.save(cart);
		  
		  cart = null;
		  
		  cart = cartRepository.findByOrder(order);
		  assertNotNull(cart);
		  assertEquals(cart.getCartID(),cartID);
		  assertEquals(cart.getType(), type);
		  
	}
	
	@Test
    public void testPersistAndLoadCustomer() {
		
		  String phone = "000-1111";
		  
		  String email = "customer@mail.com";
		  String name = "TestCustomer";
		  String password = "Testpassword";
		  Address address = new Address();
		  TheGroceryStoreSystem theGroceryStoreSystem = new TheGroceryStoreSystem ();
		  
		  Customer customer = new Customer(email, name, password, phone, address,  theGroceryStoreSystem);
		  customerRepository.save(customer);
		  
		  customer = null;
		  
		  customer = customerRepository.findByEmail(email);
		  assertNotNull(customer);
		  assertEquals(customer.getPhone(),phone);
		  assertEquals(customer.getAddress(),address);
		  assertEquals(customer.getEmail(),email);
		  assertEquals(customer.getPassword(),password);
		  assertEquals(customer.getName(),name);
		  

	}

}