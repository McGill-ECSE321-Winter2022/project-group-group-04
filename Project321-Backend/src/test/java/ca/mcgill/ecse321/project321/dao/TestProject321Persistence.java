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
import ca.mcgill.ecse321.project321.model.Employee;
import ca.mcgill.ecse321.project321.model.Employee.EmployeeStatus;
import ca.mcgill.ecse321.project321.model.Product.PriceType;

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
    public void testPersistAndLoadCart() {
		
	}

	@Test
    public void testPersistAndLoadCartItem() {
		int quantity = 1;
		
		String productName = "Apple";
		String isAvailableOnline = "Yes";
		int price = 3;
		int stock = 100;
		PriceType priceType = PriceType.PER_UNIT;
		Product product = new Product(priceType, productName, isAvailableOnline, price, stock);
		productRepository.save(product);
		product = productRepository.findByProductName(productName);
		
		CartItem cartItem = new CartItem(quantity, product);
		cartItemRepository.save(cartItem);
		
		cartItem=null;
		
		List<CartItem> cartItemlist = cartItemRepository.findByProductAndQuantity(product, quantity);
		cartItem = cartItemlist.get(0);
		assertNotNull(cartItem);
		assertEquals(cartItem.getQuantity(),quantity);
		
		assertEquals(cartItem.getProduct().getProductName(),productName);
		assertEquals(cartItem.getProduct().getIsAvailableOnline(),isAvailableOnline);
		assertEquals(cartItem.getProduct().getPrice(),price);
		assertEquals(cartItem.getProduct().getStock(),stock);
		assertEquals(cartItem.getProduct().getPriceType(),priceType);
		
	}
	
	@Test
    public void testPersistAndLoadCustomer() {
		
		  
		  String email = "customer@mail.com";
		  String name = "TestCustomer";
		  String password = "Testpassword";
		  String phone = "000-1111";
		  
		  String town = "TestTown";
		  String street = "TestStreet";
		  String postalCode = "TestPostalCode";
		  int unit = 321;
		  Address address = new Address(town,street,postalCode,unit);
		  
		  
		  Customer customer = new Customer(email, name, password, phone, address);
		  customerRepository.save(customer);
		  
		  customer = null;
		  
		  customer = customerRepository.findByEmail(email);
		  assertNotNull(customer);
		  assertEquals(customer.getPhone(),phone);
		  assertEquals(customer.getEmail(),email);
		  assertEquals(customer.getPassword(),password);
		  assertEquals(customer.getName(),name);

		  assertEquals(customer.getAddress().getTown(),town);
		  assertEquals(customer.getAddress().getStreet(),street);
		  assertEquals(customer.getAddress().getPostalCode(),postalCode);
		  assertEquals(customer.getAddress().getUnit(),unit);

	}

	@Test
    public void testPersistAndLoadDay() {
		
		WeekDays weekDay = WeekDays.Monday;
		Time storeStartHour = java.sql.Time.valueOf(LocalTime.of(11, 35));
		Time storeEndHour = java.sql.Time.valueOf(LocalTime.of(12, 35));
		
		Day day = new Day(weekDay, storeStartHour, storeEndHour);
		
		dayRepository.save(day);
		day=null;
		
		day = dayRepository.findByDay(weekDay);
		assertNotNull(day);
		assertEquals(day.getDay(), weekDay);
		assertEquals(day.getStoreStartHour(), storeStartHour);
		assertEquals(day.getStoreEndHour(), storeEndHour);
		
	}

	@Test
    public void testPersistAndLoadEmployee() {
		  
		  String email = "employee@mail.com";
		  String name = "TestEmployee";
		  String password = "Testpassword";
		  EmployeeStatus employeeStatus = EmployeeStatus.Active;
		  
		  Employee employee = new Employee(email, name, password, employeeStatus);
		  
		  employeeRepository.save(employee);
		  employee = null;
		  
		  employee = employeeRepository.findByEmail(email);
		  assertNotNull(employee);
		  assertEquals(employee.getEmail(),email);
		  assertEquals(employee.getName(),name);
		  assertEquals(employee.getPassword(),password);
		  assertEquals(employee.getStatus(), employeeStatus);
		  
	}
	@Test
    public void testPersistAndLoadInStoreBill() {
		
	}
	
	@Test
    public void testPersistAndLoadOrder() {
		
	}
	
	@Test
    public void testPersistAndLoadProduct() {
		
		String productName = "Apple";
		String isAvailableOnline = "Yes";
		int price = 3;
		int stock = 100;
		PriceType priceType = PriceType.PER_UNIT;
		Product product = new Product(priceType, productName, isAvailableOnline, price, stock);
  
		productRepository.save(product);
		product = null;
		
		product = productRepository.findByProductName(productName);
		assertNotNull(product);
		assertEquals(product.getProductName(),productName);
		assertEquals(product.getIsAvailableOnline(),isAvailableOnline);
		assertEquals(product.getPrice(),price);
		assertEquals(product.getStock(),stock);
		assertEquals(product.getPriceType(),priceType);
		
	}
	

	@Test
    public void testPersistAndLoadShift() {
		
		
	}
	
	@Test
    public void testPersistAndLoadStoreOwner() {
		  
		  String email = "owner@mail.com";
		  String name = "Testowner";
		  String password = "Testpassword";
		  
		  StoreOwner storeOwner = new StoreOwner(email, name, password);
		  
		  storeOwnerRepository.save(storeOwner);
		  storeOwner = null;
		  
		  storeOwner = storeOwnerRepository.findByEmail(email);
		  assertNotNull(storeOwner);
		  assertEquals(storeOwner.getEmail(),email);
		  assertEquals(storeOwner.getName(),name);
		  assertEquals(storeOwner.getPassword(),password);
		  
	}

	@Test
    public void testPersistAndLoadTimeSlot() {
		
		
	}


}