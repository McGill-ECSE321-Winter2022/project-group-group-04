package ca.mcgill.ecse321.project321.service;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.project321.dao.AddressRepository ;
import ca.mcgill.ecse321.project321.dao.CartItemRepository ;
import ca.mcgill.ecse321.project321.dao.CartRepository ;
import ca.mcgill.ecse321.project321.dao.CustomerRepository ;
import ca.mcgill.ecse321.project321.dao.EmployeeRepository ;
import ca.mcgill.ecse321.project321.dao.InStorePurchaseRepository;
import ca.mcgill.ecse321.project321.dao.OrderRepository ;
import ca.mcgill.ecse321.project321.dao.ProductRepository ;
import ca.mcgill.ecse321.project321.dao.ShiftRepository ;
import ca.mcgill.ecse321.project321.dao.StoreOwnerRepository ;
import ca.mcgill.ecse321.project321.dao.StoreRepository ;
import ca.mcgill.ecse321.project321.dao.TimeslotRepository ;
import ca.mcgill.ecse321.project321.dao.UserRepository ;

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
import ca.mcgill.ecse321.project321.model.Cart.ShoppingType;
import ca.mcgill.ecse321.project321.model.Employee.EmployeeStatus;

@ExtendWith(MockitoExtension.class)
public class TestProject321Service {

	@Mock
	private AddressRepository addressDao;
	@Mock
	private CartItemRepository cartItemDao;
	@Mock
	private CartRepository cartDao;
	@Mock
	private CustomerRepository customerDao;
	@Mock
	private EmployeeRepository employeeDao;
	@Mock
	private StoreOwnerRepository storeOwnerDao;
	@Mock
	private UserRepository userDao;
	@Mock
	private TimeslotRepository timeSlotDao;
	@Mock
	private ProductRepository productDao;
	@Mock
	private ShiftRepository shiftDao; 
	//

	@InjectMocks
	private GroceryStoreService service;

	private static final String USER_KEY = "TestUser";
	private static final String NONEXISTING_KEY = "NotAUser";
	

	@BeforeEach
	public void setMockOutput() {
		lenient().when(userDao.findByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(USER_KEY)) {
				User user = new Customer();
				user.setName(USER_KEY);
				return user;
			} else {
				return null;
			}
		});
		// Whenever anything is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(userDao.save(any(User.class))).thenAnswer(returnParameterAsAnswer);
	}
	
	@Test
	public void testCreateCustomer() {
		assertEquals(0, service.getAllCustomers().size());
		
		String email = "customer@mail.com";
		String name = "Smith";
		String password = "pw1234";
		String phone = "000-1234";
		
		String town = "TestTown";
		String street = "TestStreet";
		String postalCode = "TestPostalCode";
		int unit = 321;
		  
		Address address = new Address(town,street,postalCode,unit);
		
		Customer customer = null;
		try {
			customer = service.createCustomer(email,name,password,phone,address);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(customer);
		checkResultCustomer(customer,email,name,password,phone);
		
		assertEquals(customer.getAddress().getTown(),town);
		assertEquals(customer.getAddress().getStreet(),street);
		assertEquals(customer.getAddress().getPostalCode(),postalCode);
		assertEquals(customer.getAddress().getUnit(),unit);
		assertEquals(customer.getAddress().getAddressId(),address.getAddressId());
	}

	private void checkResultCustomer(Customer user, String email, String name, String password, String phone) {
		assertNotNull(user);
		assertEquals(user.getPhone(),phone);
		assertEquals(user.getEmail(),email);
		assertEquals(user.getPassword(),password);
		assertEquals(user.getName(),name);
	}
	
	@Test
	public void testCreateCustomerNull() {
		
		String email = null;
		String name = null;
		String password = null;
		String phone = null;
		Address address = null;
		
		String error=null;
		
		Customer customer=null;

		try {
			customer = service.createCustomer(email,name,password,phone,address);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		// check error
		assertEquals(
				"Customer email, name, password, phone, address cannot be empty!",
				error);
	}
		
	@Test
	public void testCreateCustomerShortPassWord() {
		
		String email = "customer@mail.com";
		String name = "Smith";
		String password = "pw12";
		String phone = "000-1234";
		
		String town = "TestTown";
		String street = "TestStreet";
		String postalCode = "TestPostalCode";
		int unit = 321;
		  
		Address address = new Address(town,street,postalCode,unit);
		Customer customer = null;
		String error=null;

		try {
			customer = service.createCustomer(email,name,password,phone,address);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(customer);
		// check error
		assertEquals(
				"Customer account password should be longer than 6 alphabet/numbers",
				error);
	}
	
	@Test
	public void testCreateStoreOwner() {
		
		String email = "owner@mail.com";
		String name = "TestOwner";
		String password = "pw1234";
		
		StoreOwner storeOwner = null;
		try {
			storeOwner = service.createStoreOwner(email,name,password);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(storeOwner);
		checkResultOwner(storeOwner,email,name,password);
	}

	private void checkResultOwner(StoreOwner user, String email, String name, String password) {
		assertNotNull(user);
		assertEquals(user.getEmail(),email);
		assertEquals(user.getPassword(),password);
		assertEquals(user.getName(),name);
	}
	
	@Test
	public void testCreateStoreOwnerNull() {
		
		String email = null;
		String name = null;
		String password = null;
		
		String error=null;
		
		StoreOwner owner=null;

		try {
			owner = service.createStoreOwner(email,name,password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(owner);
		// check error
		assertEquals(
				"Owner email, name, password cannot be empty!",
				error);
	}
		
	@Test
	public void testCreateStoreOwnerShortPassWord() {
		
		String email = "owner@mail.com";
		String name = "Smith";
		String password = "pw12";
		
		StoreOwner owner = null;
		String error=null;

		try {
			owner = service.createStoreOwner(email,name,password);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(owner);
		// check error
		assertEquals(
				"Owner account password should be longer than 6 alphabet/numbers",
				error);
	}
	
	@Test
	public void testCreateEmployee() {
		
		String email = "employee@mail.com";
		String name = "TestEmployee";
		String password = "pw1234";
		EmployeeStatus employeeStatus = EmployeeStatus.Active;
		
		Employee employee = null;
		
		try {
			employee = service.createEmployee(email,name,password,employeeStatus);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(employee);
		checkResultEmployee(employee,email,name,password,employeeStatus);
	}

	private void checkResultEmployee(Employee user, String email, String name, String password, EmployeeStatus status) {
		assertNotNull(user);
		assertEquals(user.getEmail(),email);
		assertEquals(user.getPassword(),password);
		assertEquals(user.getName(),name);
		assertEquals(user.getStatus(), status);
	}
	
	@Test
	public void testCreateEmployeeNull() {
		
		String email = null;
		String name = null;
		String password = null;
		EmployeeStatus employeeStatus = null;
		
		String error=null;
		
		Employee employee=null;

		try {
			employee = service.createEmployee(email,name,password,employeeStatus);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(employee);
		// check error
		assertEquals(
				"Employee email, name, password cannot be empty!",
				error);
	}
		
	@Test
	public void testCreateEmployeeShortPassWord() {
		
		String email = "owner@mail.com";
		String name = "Smith";
		String password = "pw12";
		EmployeeStatus employeeStatus = EmployeeStatus.Active;
		
		Employee employee = null;
		String error=null;

		try {
			employee = service.createEmployee(email,name,password,employeeStatus);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(employee);
		// check error
		assertEquals(
				"Owner account password should be longer than 6 alphabet/numbers",
				error);
	}
	
	@Test
	public void testCreateAddresses() {
		assertEquals(0, service.getAllAddresses().size());
		
		String town = "TestTown";
		String street = "TestStreet";
		String postalCode = "TestPostalCode";
		int unit = 321;
		  
		Address address = null ;
		
		try {
			address = service.createAddresses(town,street,postalCode,unit);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(address);
		checkResultAddress(address,town,street,postalCode,unit);
	}

	private void checkResultAddress(Address address, String aTown, String aStreet, String aPostalCode, int aUnit) {
		assertEquals(address.getTown(),aTown);
		assertEquals(address.getStreet(),aStreet);
		assertEquals(address.getPostalCode(),aPostalCode);
		assertEquals(address.getUnit(),aUnit);
	}
	
	@Test
	public void testCreateAddressNull() {
		
		String town = null;
		String street = null;
		String postalCode = null;
		int unit = 321;
		  
		Address address = null ;
		String error=null;
		

		try {
			address = service.createAddresses(town,street,postalCode,unit);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(address);
		// check error
		assertEquals(
				"town, street, postalCode cannot be empty!",
				error);
	}
	
	@Test
	public void testCreateCart() {
		ShoppingType type = ShoppingType.Delivery;
		
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

		Date creationDate = java.sql.Date.valueOf(LocalDate.now());
		Time creationTime = java.sql.Time.valueOf(LocalTime.now());
		
		Cart cart = null;
		
		try {
			cart = service.createCart(type, customer,creationDate, creationTime);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		assertNotNull(cart);
		checkResultCustomer(cart.getCustomer(),email,name,password,phone);
		checkResultAddress(cart.getCustomer().getAddress(),town,street,postalCode,unit);
		assertEquals(cart.getCreationDate(),creationDate);
		assertEquals(cart.getCreationTime(),creationTime);
		assertEquals(cart.getType(),type);
		
	}
	
	@Test
	public void testCreateTimeSlot() {
		Time startTime = java.sql.Time.valueOf(LocalTime.of(12, 00));
		Time endTime = java.sql.Time.valueOf(LocalTime.of(14, 00));
		Date date = java.sql.Date.valueOf(LocalDate.now());
		int maxOrderPerSlot = 100;
		
		TimeSlot ts = null;
		
		try {
			ts = service.createTimeSlot(startTime,endTime,date,maxOrderPerSlot);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		assertNotNull(ts);
		assertEquals(ts.getStartTime(),startTime);
		assertEquals(ts.getEndTime(),endTime);
		assertEquals(ts.getDate(),date);
		assertEquals(ts.getMaxOrderPerSlot(),maxOrderPerSlot);
	}
	
	@Test
	public void testCreateTimeSlotNull() {
		Time startTime = null;
		Time endTime = null;
		Date date = null;
		int maxOrderPerSlot = 100;
		
		TimeSlot ts = null;
		String error = null;
		
		try {
			ts = service.createTimeSlot(startTime,endTime,date,maxOrderPerSlot);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		assertNotNull(ts);
		assertEquals(
				"start time, end time, date of TimeSlot cannot be empty!",
				error);
	}
	
	@Test
	public void testCreateTimeSlotEndTimeBeforeStartTime() {
		Time startTime = java.sql.Time.valueOf(LocalTime.of(14, 00));
		Time endTime = java.sql.Time.valueOf(LocalTime.of(12, 00));
		Date date = java.sql.Date.valueOf(LocalDate.now());
		int maxOrderPerSlot = 100;
		
		TimeSlot ts = null;
		String error = null;
		
		try {
			ts = service.createTimeSlot(startTime,endTime,date,maxOrderPerSlot);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		assertNotNull(ts);
		assertEquals("Event end time cannot be before start time!", error);
	}
	
	@Test
	public void testCreateOrder() {

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

		 Date creationDate = java.sql.Date.valueOf(LocalDate.of(2022, 12, 31));
		 Time creationTime = java.sql.Time.valueOf(LocalTime.of(13, 00));
		Cart testCart = new Cart(ShoppingType.Delivery, customer, creationDate, creationTime);

		boolean comp = false;
		Date date = java.sql.Date.valueOf(LocalDate.now());
		int total = 500;
		String payment = "CreditCard";
		
		Order order = null;
		
		try {
			order = service.createOrder(comp, date, total,  payment, testCart);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		assertNotNull(order);
		assertEquals(order.getCompleted(),comp);
		assertEquals(order.getOrderDate(),date);
		assertEquals(order.getTotal(),total);
		assertEquals(order.getPayment(),payment);

		assertEquals(order.getCart().getType(),ShoppingType.Delivery);
		assertEquals(order.getCart().getCreationDate(),creationDate);
		assertEquals(order.getCart().getCreationTime(),creationTime);
		checkResultCustomer(order.getCart().getCustomer(),email,name,password,phone);
		checkResultAddress(order.getCart().getCustomer().getAddress(),
				town,street,postalCode,unit);
		
		
	}
	@Test
	public void testCreateOrderNull() {
		boolean comp = false;
		Date date = null;
		int total = 0;
		String payment = null;
		Cart cart = null;
		
		String error = null;
		Order order = null;
		
		try {
			order = service.createOrder(comp, date, total,  payment, cart);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		assertNull(order);
		assertEquals(
				"Payment method, cart, date cannot be empty!"
				+ "total cannot be empty!", error);
		
	}
	
}

