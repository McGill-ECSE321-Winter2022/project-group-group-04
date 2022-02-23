package ca.mcgill.ecse321.project321.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.project321.model.Address;
import ca.mcgill.ecse321.project321.model.Cart;
import ca.mcgill.ecse321.project321.model.CartItem;
import ca.mcgill.ecse321.project321.model.Customer;
import ca.mcgill.ecse321.project321.model.Order;
import ca.mcgill.ecse321.project321.model.Product;
import ca.mcgill.ecse321.project321.model.StoreOwner;
import ca.mcgill.ecse321.project321.model.Store;
import ca.mcgill.ecse321.project321.model.TimeSlot;
import ca.mcgill.ecse321.project321.model.Cart.ShoppingType;
import ca.mcgill.ecse321.project321.model.Employee;
import ca.mcgill.ecse321.project321.model.Employee.EmployeeStatus;
import ca.mcgill.ecse321.project321.model.Product.PriceType;
import ca.mcgill.ecse321.project321.model.Shift;
import ca.mcgill.ecse321.project321.model.InStoreBill;

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

	@AfterEach
	public void clearDatabase() {
		// First, we clear registrations to avoid exceptions due to inconsistencies
		storeOwnerRepository.deleteAll();
		orderRepository.deleteAll();
        shiftRepository.deleteAll();
        cartRepository.deleteAll();
        cartItemRepository.deleteAll();
		inStoreBillRepository.deleteAll();
        productRepository.deleteAll();
        timeslotRepository.deleteAll();
		employeeRepository.deleteAll();
        customerRepository.deleteAll();
        addressRepository.deleteAll();
		// Then we can clear the other tables
	}

//Read and Write test for Address Class 
	@Test 
    public void testPersistAndLoadAddress() { 
		  String town = "TestTown";
		  String street = "TestStreet";
		  String postalCode = "TestPostalCode";
		  int unit = 321;
		  
		  Address address = new Address(town,street,postalCode,unit);
		  addressRepository.save(address);
		  
		  address = null;
		  
		  address = addressRepository.findByUnitAndStreetAndTownAndPostalCode(unit, street, town, postalCode);
		
//Write test for Object
		  assertNotNull(address, "Address is not being written");
//Read and Write test(s) for attributes, and in turn Read test for Address Object as it is collected from addressRepository
		  assertEquals(address.getTown(),town);
		  assertEquals(address.getStreet(),street);
		  assertEquals(address.getPostalCode(),postalCode);
		  assertEquals(address.getUnit(),unit);

		
	}
	
//Read and Write test for Cart Class
	@Test
    public void testPersistAndLoadCart() {
		  String email = "customer@mail.com";
		  String name = "TestCustomer";
		  String password = "Testpassword";
		  String phone = "000-1111";
		  String town = "TestTown";
		  String street = "TestStreet";
		  String postalCode = "TestPostalCode";
		  int unit = 321;
		  Address address = new Address(town,street,postalCode,unit);	
		  addressRepository.save(address);  
		  Customer customer = new Customer(email, name, password, phone, address);
		  customerRepository.save(customer);
		  
		  TimeSlot testSlot = new TimeSlot(java.sql.Time.valueOf(LocalTime.of(12, 35)),
				  java.sql.Time.valueOf(LocalTime.of(13, 35)), java.sql.Date.valueOf(LocalDate.now()), 20);
		  timeslotRepository.save(testSlot);
		  
		  Cart testCart = new Cart(ShoppingType.Delivery, customer);
		  
		  cartRepository.save(testCart);

		
		
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
		
		CartItem cartItem = new CartItem(3, product);
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

//Read and Write test for InStoreBill Class
	@Test
    public void testPersistAndLoadInStoreBill() {
		
		int testTotal = 100;
		Date testDate = java.sql.Date.valueOf(LocalDate.now());
		InStoreBill testBill = new InStoreBill(testTotal, testDate);
		
		inStoreBillRepository.save(testBill);
		
		testBill = null;
		List<InStoreBill> tempList = inStoreBillRepository.findByPurchaseDate(testDate);
		testBill = tempList.get(0);
//Write test for Class	
		assertNotNull(testBill);
//Read and Write tests for attributes, in turn Read test for InStoreBill Object
		assertEquals(testBill.getTotal(), testTotal);
		assertEquals(testBill.getPurchaseDate(), testDate);
//Write test for attribute
		testBill.setTotal(200);
		assertEquals(testBill.getTotal(), 200); 
		
//Write and Read test for association
		int quantity = 1;
		String productName = "Apple";
		String isAvailableOnline = "Yes";
		int price = 3;
		int stock = 100;
		PriceType priceType = PriceType.PER_UNIT;
		Product product = new Product(priceType, productName, isAvailableOnline, price, stock);
		productRepository.save(product);
		CartItem cartItem = new CartItem(quantity, product);
		ArrayList<CartItem> cartItemList = new ArrayList<CartItem>();
		cartItemList.add(cartItem);
		cartItemRepository.save(cartItem);
		
		testBill.setCartItems(cartItemList);
		
		assertTrue(testBill.hasCartItems());
		assertEquals(testBill.getCartItem(0), cartItem);

	}

//Read and Write Test Case(s) for Order Class
	@Test
    public void testPersistAndLoadOrder() {
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
		  
		 TimeSlot testSlot = new TimeSlot(java.sql.Time.valueOf(LocalTime.of(12, 00)), 
				 java.sql.Time.valueOf(LocalTime.of(14, 00)), java.sql.Date.valueOf(LocalDate.now()), 100);
		timeslotRepository.save(testSlot);
		 
		 Cart testCart = new Cart(ShoppingType.Delivery, customer);
		 cartRepository.save(testCart);
		 
		Order testOrder = new Order(false, java.sql.Date.valueOf(LocalDate.now()), 500,  "CreditCard", testCart);
		 orderRepository.save(testOrder);
		  
		  
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
		  String email = "employee@mail.com";
		  String name = "TestEmployee";
		  String password = "Testpassword";
		  EmployeeStatus employeeStatus = EmployeeStatus.Active;	  
		  Employee employee = new Employee(email, name, password, employeeStatus);	  
		  employeeRepository.save(employee);
		  
		  Shift testShift = new Shift(java.sql.Time.valueOf(LocalTime.of(11, 35)),
				  java.sql.Time.valueOf(LocalTime.of(15, 35)), java.sql.Date.valueOf(LocalDate.now()), employee);
		  shiftRepository.save(testShift);
		
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
		
			 TimeSlot testSlot = new TimeSlot(java.sql.Time.valueOf(LocalTime.of(12, 00)), 
					 java.sql.Time.valueOf(LocalTime.of(14, 00)), java.sql.Date.valueOf(LocalDate.now()), 100);
			 timeslotRepository.save(testSlot); 
	}


}