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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


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
	@Autowired
	private UserRepository userRepository;

	@AfterEach
	public void clearDatabase() {
		// First, we clear registrations to avoid exceptions due to inconsistencies
		storeRepository.deleteAll();
		orderRepository.deleteAll();
        shiftRepository.deleteAll();
        cartRepository.deleteAll();
        cartItemRepository.deleteAll();
		inStoreBillRepository.deleteAll();
        productRepository.deleteAll();
		employeeRepository.deleteAll();
		customerRepository.deleteAll();
		userRepository.deleteAll();
		timeslotRepository.deleteAll();
        addressRepository.deleteAll();
		storeOwnerRepository.deleteAll();
		
		// Then we can clear the other tables
	}
	
	@Test
	public void StartOver() {
		clearDatabase();
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
		  assertEquals(address.getTown(),town);
		  assertEquals(address.getStreet(),street);
		  assertEquals(address.getPostalCode(),postalCode);
		  assertEquals(address.getUnit(),unit);

// Check for change in attribute persisting
		String newTown = "TestTown2";
		address.setTown(newTown);
		addressRepository.save(address);

		address = null;

		address = addressRepository.findByUnitAndStreetAndTownAndPostalCode(unit, street, town, postalCode);
		assertNull(address);
		address = addressRepository.findByUnitAndStreetAndTownAndPostalCode(unit, street, newTown, postalCode);
		assertNotNull(address);
		assertEquals(address.getTown(), newTown);
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
		
// Test for object persistence
		product = productRepository.findByProductName(productName);
		assertNotNull(product);
		assertEquals(product.getProductName(),productName);
		assertEquals(product.getIsAvailableOnline(),isAvailableOnline);
		assertEquals(product.getPrice(),price);
		assertEquals(product.getStock(),stock);
		assertEquals(product.getPriceType(),priceType);

// Test for attribute persistence
		int newStock = 80;
		product.setStock(newStock);
		productRepository.save(product);
		product = null;

		product = productRepository.findByProductName(productName);
		assertNotNull(product);
		assertEquals(product.getStock(), newStock);
	}

	@Test
    public void testPersistAndLoadTimeSlot() {
			Date date = java.sql.Date.valueOf(LocalDate.now());
			Time startTime = java.sql.Time.valueOf(LocalTime.of(12, 00));
			Time endTime = java.sql.Time.valueOf(LocalTime.of(14, 00));
			int maxOrderForSlot = 100;

			 TimeSlot testSlot = new TimeSlot(startTime, endTime, date, maxOrderForSlot);
			 timeslotRepository.save(testSlot); 

			 testSlot = null;

// Test for object persistence
			 testSlot = timeslotRepository.findByDateAndStartTimeAndEndTime(date, startTime, endTime);
			 assertNotNull(testSlot);
			 assertEquals(testSlot.getStartTime(), startTime);
			 assertEquals(testSlot.getEndTime(), endTime);
			 assertEquals(testSlot.getDate(), date);
			 assertEquals(testSlot.getMaxOrderPerSlot(), maxOrderForSlot);

// Test for attribute persistence
			 int newMaxOrderForSlot = 80;
			 testSlot.setMaxOrderPerSlot(newMaxOrderForSlot);
			 timeslotRepository.save(testSlot);
			 testSlot = null;
			 
			 testSlot = timeslotRepository.findByDateAndStartTimeAndEndTime(date, startTime, endTime);
			 assertNotNull(testSlot);
			 assertEquals(testSlot.getMaxOrderPerSlot(), newMaxOrderForSlot);
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
		  

		  ShoppingType type = ShoppingType.Delivery;
		  Date creationDate = java.sql.Date.valueOf(LocalDate.now());
		  Time creationTime = java.sql.Time.valueOf(LocalTime.now());
		  Cart testCart = new Cart(type, customer, creationDate, creationTime);
		  testCart.setTimeSlot(testSlot);
		  cartRepository.save(testCart);

		  testCart = null;

// Test for object persistence
		  testCart = cartRepository.findByCustomerAndCreationDateAndCreationTime(customer, creationDate, creationTime);
		  assertNotNull(testCart);
		  assertEquals(testCart.getType(), type);
		  assertEquals(testCart.getTimeSlot().getTimeSlotId(), testSlot.getTimeSlotId());
		  assertEquals(testCart.getCustomer().getAddress().getAddressId(), address.getAddressId());
		  assertEquals(testCart.getCustomer().getEmail(), customer.getEmail());

// Test for attribute persistence
		  ShoppingType newType = ShoppingType.Pickup;
		  testCart.setType(newType);
		  cartRepository.save(testCart);

		  testCart = null;
		  testCart = cartRepository.findByCustomerAndCreationDateAndCreationTime(customer, creationDate, creationTime);
		  assertNotNull(testCart);
		  assertEquals(testCart.getType(), newType);

// Test for reference persistence
		String email2 = "customer2@mail.com";
		String name2 = "TestCustomer2";
		String password2 = "Testpassword2";
		String phone2 = "000-2222";
		String town2 = "TestTown2";
		String street2 = "TestStreet2";
		String postalCode2 = "TestPostalCode2";
		int unit2 = 3212;
		Address address2 = new Address(town2,street2,postalCode2,unit2);	
		addressRepository.save(address2);  
		Customer customer2 = new Customer(email2, name2, password2, phone2, address2);
		customerRepository.save(customer2);

		TimeSlot testSlot2 = new TimeSlot(java.sql.Time.valueOf(LocalTime.of(14, 35)),
				java.sql.Time.valueOf(LocalTime.of(15, 35)), java.sql.Date.valueOf(LocalDate.now()), 20);
		timeslotRepository.save(testSlot2);

		testCart.setCustomer(customer2);
		testCart.setTimeSlot(testSlot2);
		cartRepository.save(testCart);

		testCart = null;

		testCart = cartRepository.findByCustomerAndCreationDateAndCreationTime(customer2, creationDate, creationTime);
		assertNotNull(testCart);
		assertEquals(testCart.getCustomer().getAddress().getAddressId(), address2.getAddressId());
		assertEquals(testCart.getCustomer().getEmail(), customer2.getEmail());
		assertEquals(testCart.getTimeSlot().getTimeSlotId(), testSlot2.getTimeSlotId());
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
		
		CartItem cartItem = new CartItem(quantity, product);
		cartItemRepository.save(cartItem);
		
		cartItem=null;

// Test for object persistence
		List<CartItem> cartItemlist = cartItemRepository.findByProductAndQuantity(product, quantity);
		cartItem = cartItemlist.get(0);
		assertNotNull(cartItem);
		assertEquals(cartItem.getQuantity(),quantity);
		
		assertEquals(cartItem.getProduct().getProductName(),productName);
		assertEquals(cartItem.getProduct().getIsAvailableOnline(),isAvailableOnline);
		assertEquals(cartItem.getProduct().getPrice(),price);
		assertEquals(cartItem.getProduct().getStock(),stock);
		assertEquals(cartItem.getProduct().getPriceType(),priceType);

// Test for attribute persistence
		int newQuantity = 3;
		cartItem.setQuantity(newQuantity);
		cartItemRepository.save(cartItem);
		
		cartItem=null;
		cartItemlist = cartItemRepository.findByProductAndQuantity(product, newQuantity);
		assertNotEquals(cartItemlist.size(), 0);
		cartItem = cartItemlist.get(0);
		assertNotNull(cartItem);
		assertEquals(cartItem.getQuantity(), newQuantity);

// Test for reference persistence
		String productName2 = "Orange";
		String isAvailableOnline2 = "No";
		int price2 = 5;
		int stock2 = 10;
		PriceType priceType2 = PriceType.PER_KILOS;
		Product product2 = new Product(priceType2, productName2, isAvailableOnline2, price2, stock2);
		productRepository.save(product2);

		cartItem.setProduct(product2);
		cartItemRepository.save(cartItem);
		
		cartItem=null;
		cartItemlist = cartItemRepository.findByProductAndQuantity(product2, newQuantity);
		assertNotEquals(cartItemlist.size(), 0);
		cartItem = cartItemlist.get(0);
		assertNotNull(cartItem);
		assertEquals(cartItem.getProduct().getProductName(), product2.getProductName());
		assertEquals(cartItem.getProduct().getIsAvailableOnline(), product2.getIsAvailableOnline());
		assertEquals(cartItem.getProduct().getPrice(), product2.getPrice());
		assertEquals(cartItem.getProduct().getStock(), product2.getStock());
		assertEquals(cartItem.getProduct().getPriceType(), product2.getPriceType());
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
		  addressRepository.save(address);
		  
		  
		  Customer customer = new Customer(email, name, password, phone, address);
		  customerRepository.save(customer);
		  
		  customer = null;
		  
// Test for object persistence
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
		  assertEquals(customer.getAddress().getAddressId(),address.getAddressId());

// Test for attribute persistence
		  String newPhone = "000-2222";
		  customer.setPhone(newPhone);
		  customerRepository.save(customer);
		  
		  customer = null;
		  customer = customerRepository.findByEmail(email);
		  assertNotNull(customer);
		  assertEquals(customer.getPhone(),newPhone);
	
// Test reference persistence
		  String town2 = "TestTown2";
		  String street2 = "TestStreet2";
		  String postalCode2 = "TestPostalCode2";
		  int unit2 = 3212;
		  Address address2 = new Address(town2,street2,postalCode2,unit2);
		  addressRepository.save(address2);

		  customer.setAddress(address2);
		  customerRepository.save(customer);
		  
		  customer = null;
		  customer = customerRepository.findByEmail(email);
		  assertNotNull(customer);
		  assertEquals(customer.getAddress().getUnit(),address2.getUnit());
		  assertEquals(customer.getAddress().getPostalCode(),address2.getPostalCode());
		  assertEquals(customer.getAddress().getTown(),address2.getTown());
		  assertEquals(customer.getAddress().getStreet(),address2.getStreet());
		  assertEquals(customer.getAddress().getAddressId(),address2.getAddressId());
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
		  
// Test object persistence
		  employee = employeeRepository.findByEmail(email);
		  assertNotNull(employee);
		  assertEquals(employee.getEmail(),email);
		  assertEquals(employee.getName(),name);
		  assertEquals(employee.getPassword(),password);
		  assertEquals(employee.getStatus(), employeeStatus);

// Test attribute persistence
		  String newName = "TestEmployee2";
		  employee.setName(newName);
		  employeeRepository.save(employee);
		  employee = null;
		  employee = employeeRepository.findByEmail(email);
		  assertNotNull(employee);
		  assertEquals(employee.getName(),newName);
		  
	}

	@Test
    public void testPersistAndLoadShift() {
		  String email = "employee@mail.com";
		  String name = "TestEmployee";
		  String password = "Testpassword";
		  EmployeeStatus employeeStatus = EmployeeStatus.Active;	  
		  Employee employee = new Employee(email, name, password, employeeStatus);	  
		  employeeRepository.save(employee);
		  
		  Date date = java.sql.Date.valueOf(LocalDate.now());
		  Time startTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
		  Time endTime = java.sql.Time.valueOf(LocalTime.of(15, 35));
		  Shift testShift = new Shift(startTime, endTime, date, employee);
		  shiftRepository.save(testShift);
		
		  testShift = null;

// Test object persistence
		  testShift = shiftRepository.findByDateAndEmployee(date, employee);
		  assertNotNull(testShift);
		  assertEquals(testShift.getDate(), date);
		  assertEquals(testShift.getStartHour(), startTime);
		  assertEquals(testShift.getEndHour(), endTime);
		  assertEquals(testShift.getEmployee().getEmail(), employee.getEmail());
		
// Test attribute persistence
		  Time endTime2 = java.sql.Time.valueOf(LocalTime.of(19, 35));
		  testShift.setEndHour(endTime2);
		  shiftRepository.save(testShift);
		
		  testShift = null;
		  testShift = shiftRepository.findByDateAndEmployee(date, employee);
		  assertNotNull(testShift);
		  assertEquals(testShift.getEndHour(), endTime2);

// Test reference persistence
		  String email2 = "employee2@mail.com";
		  String name2 = "TestEmployee2";
		  String password2 = "Testpassword2";
		  EmployeeStatus employeeStatus2 = EmployeeStatus.Inactive;	  
		  Employee employee2 = new Employee(email2, name2, password2, employeeStatus2);	  
		  employeeRepository.save(employee2);

		  testShift.setEmployee(employee2);
		  shiftRepository.save(testShift);
		  testShift = null;
		  testShift = shiftRepository.findByDateAndEmployee(date, employee2);
		  assertNotNull(testShift);
		  assertEquals(testShift.getEmployee().getEmail(), employee2.getEmail());
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
		assertEquals(testBill.getTotal(), testTotal);
		assertEquals(testBill.getPurchaseDate(), testDate);

// Test for attribute persistence
		int newTotal = 200;
		testBill.setTotal(newTotal);
		inStoreBillRepository.save(testBill);
		
		testBill = null;
		tempList = inStoreBillRepository.findByPurchaseDate(testDate);
		assertNotEquals(tempList.size(), 0);
		testBill = tempList.get(0);
		assertNotNull(testBill);
		assertEquals(testBill.getTotal(), newTotal);

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
		 
		 Date creationDate = java.sql.Date.valueOf(LocalDate.of(2022, 12, 31));
		 Time creationTime = java.sql.Time.valueOf(LocalTime.of(13, 00));
		 Cart testCart = new Cart(ShoppingType.Delivery, customer, creationDate, creationTime);
		 testCart.setTimeSlot(testSlot);
		 cartRepository.save(testCart);
		 
		 boolean comp = false;
		 Date date = java.sql.Date.valueOf(LocalDate.now());
		 int total = 500;
		 String payment = "CreditCard";
		Order testOrder = new Order(comp, date, total,  payment, testCart);
		 orderRepository.save(testOrder);

		 testOrder = null;

// Test for object persistence
		 testOrder = orderRepository.findByCart(testCart);
		 assertNotNull(testOrder);
		 assertEquals(testOrder.getOrderDate(), date);
		 assertEquals(testOrder.getCompleted(), comp);
		 assertEquals(testOrder.getCart().getCartId(), testCart.getCartId());
		 assertEquals(testOrder.getCart().getTimeSlot().getTimeSlotId(), testSlot.getTimeSlotId());
		 assertEquals(testOrder.getTotal(), total);
		 assertEquals(testOrder.getPayment(), payment);

// Test for attribute persistence
		 int newTotal = 400;
		 testOrder.setTotal(newTotal);
		 orderRepository.save(testOrder);

		 testOrder = null;
		 testOrder = orderRepository.findByCart(testCart);
		 assertNotNull(testOrder);
		 assertEquals(testOrder.getTotal(), newTotal);

// Test for reference persistence
		  String email2 = "customer@mail2.com";
		  String name2 = "TestCustomer2";
		  String password2 = "Testpassword2";
		  String phone2 = "000-2222";	  
		  String town2 = "TestTown2";
		  String street2 = "TestStreet2";
		  String postalCode2 = "TestPostalCode2";
		  int unit2 = 3212;
		  Address address2 = new Address(town2,street2,postalCode2,unit2); 
		  Customer customer2 = new Customer(email2, name2, password2, phone2, address2);
		  customerRepository.save(customer2);

		  TimeSlot testSlot2 = new TimeSlot(java.sql.Time.valueOf(LocalTime.of(15, 00)), 
				 java.sql.Time.valueOf(LocalTime.of(16, 00)), java.sql.Date.valueOf(LocalDate.now()), 80);
		timeslotRepository.save(testSlot2);
		 
		 Date creationDate2 = java.sql.Date.valueOf(LocalDate.of(2022, 12, 31));
		 Time creationTime2 = java.sql.Time.valueOf(LocalTime.of(12, 00));
		 Cart testCart2 = new Cart(ShoppingType.Delivery, customer2, creationDate2, creationTime2);
		 testCart2.setTimeSlot(testSlot2);
		 cartRepository.save(testCart2);

		 testOrder.setCart(testCart2);
		 orderRepository.save(testOrder);

		 testOrder = null;
		 testOrder = orderRepository.findByCart(testCart2);
		 assertNotNull(testOrder);
		 assertEquals(testOrder.getOrderDate(), date);
		 assertEquals(testOrder.getCompleted(), comp);
		 assertEquals(testOrder.getCart().getCartId(), testCart2.getCartId());
		 assertEquals(testOrder.getCart().getType(), testCart2.getType());
		 assertEquals(testOrder.getCart().getCreationDate(), testCart2.getCreationDate());
		 assertEquals(testOrder.getCart().getCreationTime(), testCart2.getCreationTime());
		 assertEquals(testOrder.getCart().getCustomer().getEmail(), testCart2.getCustomer().getEmail());
		 assertEquals(testOrder.getCart().getCustomer().getName(), testCart2.getCustomer().getName());
		 assertEquals(testOrder.getCart().getCustomer().getPassword(), testCart2.getCustomer().getPassword());
		 assertEquals(testOrder.getCart().getCustomer().getPhone(), testCart2.getCustomer().getPhone());
		 assertEquals(testOrder.getCart().getTimeSlot().getTimeSlotId(), testSlot2.getTimeSlotId());
		 assertEquals(testOrder.getCart().getTimeSlot().getDate(), testSlot2.getDate());
		 assertEquals(testOrder.getCart().getTimeSlot().getStartTime(), testSlot2.getStartTime());
		 assertEquals(testOrder.getCart().getTimeSlot().getEndTime(), testSlot2.getEndTime());
		 assertEquals(testOrder.getCart().getTimeSlot().getMaxOrderPerSlot(), testSlot2.getMaxOrderPerSlot());
		 assertEquals(testOrder.getTotal(), newTotal);
		 assertEquals(testOrder.getPayment(), payment);
	}
	
	@Test
    public void testPersistAndLoadStoreOwner() {
		  
		  String email = "owner@mail.com";
		  String name = "Testowner";
		  String password = "Testpassword";
		  
		  StoreOwner storeOwner = new StoreOwner(email, name, password);
		  
		  storeOwnerRepository.save(storeOwner);
		  storeOwner = null;
	
// Test object persistence
		  storeOwner = storeOwnerRepository.findByEmail(email);
		  assertNotNull(storeOwner);
		  assertEquals(storeOwner.getEmail(),email);
		  assertEquals(storeOwner.getName(),name);
		  assertEquals(storeOwner.getPassword(),password);
	
// Test attribute persistence
		  String newName = "Testowner2";
		  storeOwner.setName(newName);
		  storeOwnerRepository.save(storeOwner);
		  storeOwner = null;
		  storeOwner = storeOwnerRepository.findByEmail(email);
		  assertNotNull(storeOwner);
		  assertEquals(storeOwner.getName(), newName);
		  
	}

	@Test
	public void testPersistAndLoadStore() {
		String telephone = "000-1111";
  		String email = "store@mail.com";
  		Time openingHour = java.sql.Time.valueOf(LocalTime.of(9, 00));
  		Time closingHour = java.sql.Time.valueOf(LocalTime.of(21, 00));
		int outOfTownFee = 10;
		
		String ownerEmail = "owner@mail.com";
		String ownerName = "Testowner";
		String ownerPassword = "Testpassword";
		  
		StoreOwner storeOwner = new StoreOwner(ownerEmail, ownerName, ownerPassword);
		storeOwnerRepository.save(storeOwner);

		String town = "TestTown2";
		String street = "TestStreet2";
		String postalCode = "TestPostalCode2";
		int unit = 321;
		Address address = new Address(town,street,postalCode,unit);
		addressRepository.save(address);

		Store store = new Store(telephone, email, openingHour, closingHour, storeOwner, address, outOfTownFee);
		storeRepository.save(store);

		store = null;

// Test object persistence
		store = storeRepository.findByAddress(address);
		assertNotNull(store);
		assertEquals(store.getTelephone(), telephone);
		assertEquals(store.getEmail(), email);
		assertEquals(store.getOpeningHour(), openingHour);
		assertEquals(store.getClosingHour(), closingHour);
		assertEquals(store.getOutOfTownFee(), outOfTownFee);
		assertEquals(store.getAddress().getAddressId(), address.getAddressId());
		assertEquals(store.getStoreOwner().getEmail(), storeOwner.getEmail());

// Test attribute persistence
		String newEmail = "store@mail.com";
		store.setEmail(newEmail);
		storeRepository.save(store);

		store = null;
		store = storeRepository.findByAddress(address);
		assertNotNull(store);
		assertEquals(store.getEmail(), newEmail);

// Test reference persistence 
		String owner2Email = "owner@mail.com";
		String owner2Name = "Testowner";
		String owner2Password = "Testpassword";
  
		StoreOwner storeOwner2 = new StoreOwner(owner2Email, owner2Name, owner2Password);
		storeOwnerRepository.save(storeOwner2);

		store.setStoreOwner(storeOwner2);
		storeRepository.save(store);

		store = null;
		store = storeRepository.findByAddress(address);
		assertNotNull(store);
		assertEquals(store.getStoreOwner().getEmail(), storeOwner2.getEmail());
	}

}