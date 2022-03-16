package ca.mcgill.ecse321.project321.service;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;

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
import ca.mcgill.ecse321.project321.dto.OrderDTO;
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
	@Mock
	private OrderRepository orderDao; 
	@Mock
	private StoreRepository storeDao;
	@Mock 
	private InStorePurchaseRepository inStorePurchaseDao;
	

	@InjectMocks
	private GroceryStoreService service;

	private static final String USER_KEY = "TestUser@mail.com";
	private static final String NONEXISTING_KEY = "NotAUser";
	
	private static final String CUSTOMER_KEY = "TestUser@mail.com";
	private static final String NONEXISTING_CUSTOMER_KEY = "NotAUser";
	private static final String SET_CUSTOMER_ADDRESS_KEY = "testSet@mail.com";
	
	private static final Address ADDRESS_KEY = new Address("testTown", "testStreet", "testPostCode", 1);
	private static final Address NON_EXISTING_ADDRESS_KEY = new Address("testTown2", "testStreet2", "testPostCode2", 1);
	private static final Address ADDRESS_KEY_DELETING = new Address("testTown", "testStreet", "testPostCode", 1);
	
	private static final Address CART_TESTING_ADDRESS = new Address("testTown", "testStreet", "testPostCode", 1);
	private static final Customer CART_TESTING_CUSTOMER = new Customer("email", "name", "password", "phone",CART_TESTING_ADDRESS ); 
	private static final Date TESTING_DATE = new Date(2022, 01, 01);
	private static final Time START_TESTING_TIME = java.sql.Time.valueOf(LocalTime.of(12,00)); 
	private static final Time END_TESTING_TIME = java.sql.Time.valueOf(LocalTime.of(16, 00));
	private static final TimeSlot CART_TESTING_TIMESLOT = new TimeSlot(); 
	private static final ShoppingType CART_TESTING_TYPE = ShoppingType.Delivery;
	private static final Cart TEST_CART = new Cart();
	private static final Order TEST_ORDER = new Order();
	private static final Order TEST_ORDER2 = new Order();
	private static final Cart TEST_CART2 = new Cart();
	
	private static final String PRODUCT_KEY = "testProduct";
	private static final String PRODUCT_KEY_NEG_PRICE = "testNegPrice";
	private static final String PRODUCT_KEY_CREATE = "testCreate"; 
	private static final String PRODUCT_KEY_NEG_STOCK = "testNegStock";
	private static final String PRODUCT_KEY_DELETE = "testSet";
	
	@BeforeEach
	public void setMockOutput() {
		
		lenient().when(userDao.findByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(USER_KEY)) {
				User user = new Customer();
				user.setEmail(USER_KEY);
				return user;
			} else {
				return null;
			}
		});
		
		lenient().when(productDao.findByProductName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(PRODUCT_KEY)) {
				Product product = new Product();
				product.setProductName(PRODUCT_KEY);
				product.setPrice(50);
				product.setStock(50); 
				return product;
			} else if(invocation.getArgument(0).equals(PRODUCT_KEY_CREATE)){
				
				return null;
			}else if(invocation.getArgument(0).equals(PRODUCT_KEY_NEG_PRICE)){
				
				return null;
			}else if(invocation.getArgument(0).equals(PRODUCT_KEY_NEG_STOCK)) {
				return null;
			}else if (invocation.getArgument(0).equals(PRODUCT_KEY_DELETE)){
				Product product = new Product();
				product.setProductName(PRODUCT_KEY_DELETE);
				product.setPrice(50);
				product.setStock(50);
				return product;
			}else {
				return null;
			}
		});
		
		lenient().when(cartDao.findByCustomer(CART_TESTING_CUSTOMER)).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(CART_TESTING_CUSTOMER)) {
				List<Cart> cartList =  new ArrayList<Cart>();
				
				TEST_CART.setCustomer(CART_TESTING_CUSTOMER);		
				TEST_CART2.setCustomer(CART_TESTING_CUSTOMER);
				cartList.add(TEST_CART);
				cartList.add(TEST_CART2);
				return cartList;	
			} else {
				return null;
			}
		});
		
		lenient().when(orderDao.findByCart(TEST_CART)).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(TEST_CART)) {
				TEST_ORDER.setCart(TEST_CART);
				return TEST_ORDER;	
			} else if (invocation.getArgument(0).equals(TEST_CART2)) {
				TEST_ORDER2.setCart(TEST_CART2);
				return TEST_ORDER2;
			}else {
				return null;
			}
		});
	
		
		lenient().when(cartDao.findByTimeSlot(CART_TESTING_TIMESLOT)).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(CART_TESTING_TIMESLOT)) {
				List<Cart> cartList =  new ArrayList<Cart>();
				Cart testCart = new Cart();
				testCart.setTimeSlot(CART_TESTING_TIMESLOT);
				cartList.add(testCart);
				return cartList;	
			} else {
				return null;
			}
		});
		
		lenient().when(cartDao.findByType(CART_TESTING_TYPE)).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(CART_TESTING_TYPE)) {
				List<Cart> cartList =  new ArrayList<Cart>();
				Cart testCart = new Cart();
				testCart.setType(CART_TESTING_TYPE);
				cartList.add(testCart);
				return cartList;	
			} else {
				return null;
			}
		});
		
		lenient().when(cartDao.findByCustomerAndCreationDateAndCreationTime(CART_TESTING_CUSTOMER,TESTING_DATE, START_TESTING_TIME )).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(CART_TESTING_CUSTOMER)) {
				Cart testCart = new Cart();
				testCart.setCustomer(CART_TESTING_CUSTOMER);
				testCart.setCreationDate(TESTING_DATE);
				testCart.setCreationTime(START_TESTING_TIME);	
				return testCart;	
			} else {
				return null;
			}
		});
		
		lenient().when(timeSlotDao.findByDateAndStartTimeAndEndTime(TESTING_DATE, START_TESTING_TIME, END_TESTING_TIME )).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(TESTING_DATE)) {
				TimeSlot testSlot = new TimeSlot(START_TESTING_TIME, END_TESTING_TIME, TESTING_DATE, 10);	
				return testSlot;	
			} else {
				return null;
			}
		});
			
		
			lenient().when(customerDao.findByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
				if (invocation.getArgument(0).equals(CUSTOMER_KEY)) {
					User user = new Customer();
					user.setEmail(CUSTOMER_KEY);
					return user;
				} else if(invocation.getArgument(0).equals(SET_CUSTOMER_ADDRESS_KEY)){
					User user = new Customer();
					user.setEmail(SET_CUSTOMER_ADDRESS_KEY);
					return user;
				}else {
					return null;
				}
			});
		
//		lenient().when((storeDao.findAll())).thenAnswer((InvocationOnMock invocation) -> {	
//				List<Store> storeList =  new ArrayList<Store>();
//				Store store = new Store();
//				store.setAddress(ADDRESS_KEY);
//				storeList.add(store);
//				return storeList;	
//		});
		

		
		lenient().when((orderDao.findAll())).thenAnswer((InvocationOnMock invocation) -> {	
			List<Order> orderList =  new ArrayList<Order>();	
			orderList.add(TEST_ORDER);
			orderList.add(TEST_ORDER2);
			return orderList;	
	});
		
		
		
		// Whenever anything is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(userDao.save(any(User.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(employeeDao.save(any(Employee.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(orderDao.save(any(Order.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(shiftDao.save(any(Shift.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(productDao.save(any(Product.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(timeSlotDao.save(any(TimeSlot.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(storeOwnerDao.save(any(StoreOwner.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(customerDao.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(cartDao.save(any(Cart.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(cartItemDao.save(any(CartItem.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(addressDao.save(any(Address.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(storeDao.save(any(Store.class))).thenAnswer(returnParameterAsAnswer);
	}
	
/*TestsRelated to User Service Methods*/
	@Test
	public void testGetUserByEmail() {
		assertEquals(USER_KEY, service.getUser(USER_KEY).getEmail());				
	}	
		
	@Test
	public void testGetNonExistentUser() {
		assertNull(service.getUser(NONEXISTING_KEY));
	}
	
/* Tests Related to Customer Service Methods */
	
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

	@Test
	public void testCreateExistingCustomer() {
		String town = "TestTown";
		String street = "TestStreet";
		String postalCode = "TestPostalCode";
		int unit = 321;
		Address address = new Address(town,street,postalCode,unit);
		String error = "";
		try {
		service.createCustomer(CUSTOMER_KEY,"name","password","phone",address);
		}catch(IllegalArgumentException e){
			error = e.getMessage();
		}
		
		assertEquals("Customer with this email already exists", error);
		
	}
	
   @Test
	private void checkResultCustomer(Customer user, String email, String name, String password, String phone) {
		assertNotNull(user);
		assertEquals(user.getPhone(),phone);
		assertEquals(user.getEmail(),email);
		assertEquals(user.getPassword(),password);
		assertEquals(user.getName(),name);
	}
	
	@Test
	public void testCreateCustomerNullEmail() {
		
		String email = null;
		String name = "test";
		String password = "123abcde";
		String phone = "123";
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
				"email, name, and password of customer all needs to be associated with a non-empty string",
				error);
	}
	
	@Test
	public void testCreateCustomerNullName() {
		
		String email = "test@email.com";
		String name = null;
		String password = "123abcde";
		String phone = "123";
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
				"email, name, and password of customer all needs to be associated with a non-empty string",
				error);
	}
	
	@Test
	public void testCreateCustomerNullPassword() {
		
		String email = "test@mail.com";
		String name = "test";
		String password = null;
		String phone = "123";
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
				"email, name, and password of customer all needs to be associated with a non-empty string",
				error);
	}
	
	@Test
	public void testCreateCustomerEmptyStringEmail() {
		String email = "";
		String name = "";
		String password = "";
		String phone = "";
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
				"email, name, and password of customer all needs to be associated with a non-empty string",
				error);
	}
	
	@Test
	public void testCreateCustomerEmptyStringName() {
		String email = "test@mail.com";
		String name = "";
		String password = "";
		String phone = "";
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
				"email, name, and password of customer all needs to be associated with a non-empty string",
				error);
	}
	
	@Test
	public void testCreateCustomerEmptyStringPassword() {
		String email = "test@mail.com";
		String name = "test";
		String password = "";
		String phone = "";
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
				"email, name, and password of customer all needs to be associated with a non-empty string",
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
	public void testSetCustomerAddress() {
		Address newAddress = new Address("newTown", "newStreet", "newPostCode", 123);
		Customer toTest = service.setCustomersAddress(SET_CUSTOMER_ADDRESS_KEY, "newTown", "newStreet", "newPostCode", 123);
		

		assertEquals(newAddress.getTown(), toTest.getAddress().getTown());
		assertEquals(newAddress.getStreet(), toTest.getAddress().getStreet());
		assertEquals(newAddress.getPostalCode(), toTest.getAddress().getPostalCode());
		assertEquals(newAddress.getUnit(), toTest.getAddress().getUnit());
		
	}
	
	@Test 
	public void testSetNullCustomerAddress() {
		Customer toTest = service.setCustomersAddress(NONEXISTING_CUSTOMER_KEY, "newTown", "newStreet", "newPostCode", 123);
		assertNull(toTest);
	}
	
	@Test
	public void testGetCustomer() {
		assertEquals(CUSTOMER_KEY, service.getCustomer(CUSTOMER_KEY).getEmail());
	}
	
	@Test
	public void testNonExistingCustomer() {
		assertNull(service.getCustomer(NONEXISTING_CUSTOMER_KEY));
	}
	
/*Tests Related to Store Owner Service Methods */
	
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
	 
	@Test
	public void testCreateStoreOwnerExists() {	
		String email = "owner@mail.com";
		String name = "TestOwner";
		String password = "pw1234";
		StoreOwner storeOwner = new StoreOwner(email, name, password);
		String error = null;
		
		when(storeOwnerDao.findByEmail(email)).thenReturn(storeOwner);
		
		try {
			service.createStoreOwner(email, name, password);
		}catch(Exception e) {
			error = e.getMessage();
		}
		verify(storeOwnerDao).findByEmail(email);
		assertEquals(error, "A StoreOwner exists already"); 
		
	}

	private void checkResultOwner(StoreOwner user, String email, String name, String password) {
		assertNotNull(user);
		assertEquals(user.getEmail(),email);
		assertEquals(user.getPassword(),password);
		assertEquals(user.getName(),name);
	}
	
	@Test
	public void testCreateStoreOwnerNullEmail() {
		
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
				"email, name, and password of storeOwner all needs to be associated with a non-empty string",
				error);
	}
	
	@Test
	public void testCreateStoreOwnerEmptyEmail() {
		
		String email = "";
		String name = "";
		String password = "";
		
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
				"email, name, and password of storeOwner all needs to be associated with a non-empty string",
				error);
	}
	
	@Test
	public void testCreateStoreOwnerNullName() {
		
		String email = "123@mail.com";
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
				"email, name, and password of storeOwner all needs to be associated with a non-empty string",
				error);
	}
	
	@Test
	public void testCreateStoreOwnerEmptyName() {
		
		String email = "test@mail.com";
		String name = "";
		String password = "";
		
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
				"email, name, and password of storeOwner all needs to be associated with a non-empty string",
				error);
	}
	
	@Test
	public void testCreateStoreOwnerNullPassword() {
		
		String email = "test@mail.com";
		String name = "testName";
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
				"email, name, and password of storeOwner all needs to be associated with a non-empty string",
				error);
	}
	
	@Test
	public void testCreateStoreOwnerEmptyPassword() {
		
		String email = "test@mail.com";
		String name = "testName";
		String password = "";
		
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
				"email, name, and password of storeOwner all needs to be associated with a non-empty string",
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
	public void testSetStoreOwnerInfoEmptyOwner() {
		List all = new ArrayList<StoreOwner>();
		when(storeOwnerDao.findAll()).thenReturn(all);
		String error = null;
		
		try {
			service.setStoreOwnerInfo("test@mail.com", "testName", "testPassword");
		}catch(Exception e) {
			error = e.getMessage();
		}
		verify(storeOwnerDao).findAll();
		assertEquals(error, "No Store Owner Account found");
	}
	
	@Test
	public void testSetStoreOwnerInfo() {
		StoreOwner testOwner = new StoreOwner("test@mail.com", "testName", "testPassword");
		List all =  new ArrayList<StoreOwner>();
		all.add(testOwner);
		
		when(storeOwnerDao.findAll()).thenReturn(all);
		
		testOwner = service.setStoreOwnerInfo("newMail@mail.com", "newName", "newPassword");
		
		verify(storeOwnerDao).findAll();
		assertEquals(testOwner.getEmail(),"newMail@mail.com");
		assertEquals(testOwner.getName(),"newName");
		assertEquals(testOwner.getPassword(), "newPassword");

	}
	
	@Test
	public void testGetStoreOwner() {
		StoreOwner owner = new StoreOwner();
		List<StoreOwner> all = new ArrayList<StoreOwner>();
		all.add(owner);
		when(storeOwnerDao.findAll()).thenReturn(all);
		StoreOwner returned = service.getStoreOwner();
		verify(storeOwnerDao).findAll();
		assertEquals(owner, returned);
	}
	
	@Test
	public void testGetStoreOwnerNull() {
		
		List<StoreOwner> all = new ArrayList<StoreOwner>();
		
		when(storeOwnerDao.findAll()).thenReturn(all);
		StoreOwner returned = service.getStoreOwner();
		verify(storeOwnerDao).findAll();
		assertNull(returned);
	}
	
/* Tests Related to Employee Service Methods*/
	
	@Test
	public void testCreateEmployee() {
		assertEquals(0, service.getAllEmployee().size());
		String email = "employee@mail.com";
		String name = "TestEmployee";
		String password = "pw1234";
		EmployeeStatus employeeStatus = EmployeeStatus.Active;
		
		assertEquals(0, service.getAllEmployee().size());
		String email2 = "employee@mail.com";
		String name2 = "TestEmployee";
		String password2 = "pw1234";
		EmployeeStatus employeeStatus2 = EmployeeStatus.Active;
		
		Employee employee = null;
		
		try {
			employee = service.createEmployee(email,name,password,employeeStatus);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		when(employeeDao.findByEmail(email2)).thenReturn(employee);
		Employee employee2 = service.createEmployee(email2, name2, password2, employeeStatus2);
		assertNull(employee2);
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
	public void testCreateEmployeeNullEmail() {
		
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
				"email, name, and password of employee all needs to be associated with a non-empty string",
				error);
	}
	
	@Test
	public void testCreateEmployeeNullName() {
		
		String email = "email";
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
				"email, name, and password of employee all needs to be associated with a non-empty string",
				error);
	}
	
	@Test
	public void testCreateEmployeeNullPassword() {
		
		String email = "email";
		String name = "name";
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
				"email, name, and password of employee all needs to be associated with a non-empty string",
				error);
	}
	
	@Test
	public void testCreateEmployeeEmptyEmail() {
		
		String email = "";
		String name = "name";
		String password = "password";
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
				"email, name, and password of employee all needs to be associated with a non-empty string",
				error);
	}
	
	@Test
	public void testCreateEmployeeEmptyName() {
		
		String email = "email.com";
		String name = "";
		String password = "password";
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
				"email, name, and password of employee all needs to be associated with a non-empty string",
				error);
	}
	
	@Test
	public void testCreateEmployeeEmptyPassword() {
		
		String email = "email.com";
		String name = "name";
		String password = "";
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
				"email, name, and password of employee all needs to be associated with a non-empty string",
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
				"Employee account password should be longer than 6 alphabet/numbers",
				error);
	}
	
	@Test
	public void testGetEmployee() {
		String email = "email@get.com";
		
		String name = "TestEmployee";
		String password = "pw1234";
		EmployeeStatus employeeStatus = EmployeeStatus.Active;		
		Employee employee = service.createEmployee(email,name,password,employeeStatus);
		
		when(employeeDao.findByEmail(email)).thenReturn(employee);
		Employee returned = service.getEmployee(email);
		verify(employeeDao, times(2)).findByEmail(email); //once for creating, then another for getting
		
		assertEquals(employee, returned);
	}
	
	@Test
	public void testRemoveEmployee() {
		String email = "email@get.com";
		String name = "TestEmployee";
		String password = "pw1234";
		EmployeeStatus employeeStatus = EmployeeStatus.Active;		
		Employee employee = service.createEmployee(email,name,password,employeeStatus);
		service.removeEmployee(employee);
		
		
		verify(employeeDao).delete(employee); //once for creating, then another for getting
	}
	
	
/*Tests Related to Address Service Methods*/
	
	@Test
	public void testCreateAddresses() {
		assertEquals(0, service.getAllAddresses().size());
		
		String town = "TestTown";
		String street = "TestStreet";
		String postalCode = "TestPostalCode";
		int unit = 321;
		
		String town1 = "TestTown";
		String street1 = "TestStreet";
		String postalCode1 = "TestPostalCode";
		int unit1 = 321;
		  
		Address address = null ;
		
		try {
			address = service.createAddresses(town,street,postalCode,unit);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
	when(addressDao.findByUnitAndStreetAndTownAndPostalCode(unit1, street1, town1, postalCode1)).thenReturn(address);
	Address address2 = service.createAddresses(town1, street1, postalCode1, unit1);
		assertNotNull(address);
		assertNull(address2);
		checkResultAddress(address,town,street,postalCode,unit);
	}

	private void checkResultAddress(Address address, String aTown, String aStreet, String aPostalCode, int aUnit) {
		assertEquals(address.getTown(),aTown);
		assertEquals(address.getStreet(),aStreet);
		assertEquals(address.getPostalCode(),aPostalCode);
		assertEquals(address.getUnit(),aUnit);
	}
	
	@Test
	public void testCreateAddressNullTown() {
		
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
				"town, street, and postalCode of address all needs to be associated with a non-empty string",
				error);
	}
	
	@Test
	public void testCreateAddressNullStreet() {
		
		String town = "town";
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
				"town, street, and postalCode of address all needs to be associated with a non-empty string",
				error);
	}
	
	@Test
	public void testCreateAddressNullPostCode() {
		
		String town = "town";
		String street = "street";
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
				"town, street, and postalCode of address all needs to be associated with a non-empty string",
				error);
	}
	
	
	@Test
	public void testCreateAddressEmptyTown() {
		
		String town = "";
		String street = "";
		String postalCode = "";
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
				"town, street, and postalCode of address all needs to be associated with a non-empty string",
				error);
	}
	
	@Test
	public void testCreateAddressEmptyStreet() {
		
		String town = "town";
		String street = "";
		String postalCode = "";
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
				"town, street, and postalCode of address all needs to be associated with a non-empty string",
				error);
	}
	
	@Test
	public void testCreateAddressEmptyPostcode() {
		
		String town = "town";
		String street = "street"; 
		String postalCode = "";
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
				"town, street, and postalCode of address all needs to be associated with a non-empty string",
				error);
	}
	
	@Test
	public void testGetAddresseByUnitAndStreetAndTownAndPostalCode() {
		String town = "TestTown";
		String street = "TestStreet";
		String postalCode = "TestPostalCode";
		int unit = 321;
		Address	address = service.createAddresses(town,street,postalCode,unit);
		
		when(addressDao.findByUnitAndStreetAndTownAndPostalCode(unit, street, town, postalCode)).thenReturn(address);
		Address returned = service.getAddresseByUnitAndStreetAndTownAndPostalCode(unit, street, town, postalCode);
		verify(addressDao, times(2)).findByUnitAndStreetAndTownAndPostalCode(unit, street, town, postalCode); //one time when creating, then one time when getting
		assertEquals(address, returned);
	}


/* Tests Related to Cart Service Methods*/
	@Test
	public void testCreateCart() {
	assertEquals(0,service.getAllCarts().size());
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
	public void testGetCartsByCustomer() {
		List<Cart> listOfCarts = service.getCartsByCustomer(CART_TESTING_CUSTOMER);
		assertEquals(CART_TESTING_CUSTOMER, listOfCarts.get(0).getCustomer());
	}
	
	@Test
	public void testGetCartByCustomerAndDateAndTime() {
		Cart testCart = service.getCartByCustomerAndDateAndTime(CART_TESTING_CUSTOMER, TESTING_DATE, START_TESTING_TIME);
		assertEquals(CART_TESTING_CUSTOMER, testCart.getCustomer());
		assertEquals(TESTING_DATE, testCart.getCreationDate());
		assertEquals(START_TESTING_TIME, testCart.getCreationTime());
	}
	
	@Test
	public void testSetTimeSlot() {
		Time startTime = java.sql.Time.valueOf(LocalTime.of(12, 00));
		Time endTime = java.sql.Time.valueOf(LocalTime.of(14, 00));
		Date date = java.sql.Date.valueOf(LocalDate.now());
		int maxOrderPerSlot = 100;	
		TimeSlot ts = null;
		ts = service.createTimeSlot(startTime,endTime,date,maxOrderPerSlot);
		
		Cart testCart = new Cart();
		assertNull(testCart.getTimeSlot());
		service.setTimeSlot(testCart, ts);
		assertEquals(ts,testCart.getTimeSlot());
		
	}
	
	@Test
	public void testGetByTimeSlot() {
		List<Cart> listOfCarts = service.getCartsByTimeSlot(CART_TESTING_TIMESLOT);
		assertEquals(CART_TESTING_TIMESLOT, listOfCarts.get(0).getTimeSlot());
	}
	
	@Test
	public void testGetByType() {
		List<Cart> listOfCarts = service.getCartsByType(CART_TESTING_TYPE);
		assertEquals(CART_TESTING_TYPE, listOfCarts.get(0).getType());
	}
	
	
	
/*Tests Related to TimeSlot Service Methods */
	
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
		
		when(timeSlotDao.findByDateAndStartTimeAndEndTime(date, startTime, endTime)).thenReturn(ts);
		TimeSlot ts2 = service.createTimeSlot(startTime, endTime, date, maxOrderPerSlot);
		
		assertNull(ts2);
		assertNotNull(ts);
		assertEquals(ts.getStartTime(),startTime);
		assertEquals(ts.getEndTime(),endTime);
		assertEquals(ts.getDate(),date);
		assertEquals(ts.getMaxOrderPerSlot(),maxOrderPerSlot);
	}
	
	
	@Test
	public void testCreateTimeSlotStartTimeNull() {
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
			error = e.getMessage();
		}
		
		assertNull(ts);
		assertEquals(
				"Any of the startTime, endTime, and date of a timeSlot cannot be null",
				error);
	}
	

	@Test
	public void testCreateTimeSlotEndTimeNull() {
		Time startTime = java.sql.Time.valueOf(LocalTime.of(12, 00));
		Time endTime = null;
		Date date = java.sql.Date.valueOf(LocalDate.now());
		int maxOrderPerSlot = 100;
		
		TimeSlot ts = null;
		String error = null;
		
		try {
			ts = service.createTimeSlot(startTime,endTime,date,maxOrderPerSlot);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		
		assertNull(ts);
		assertEquals(
				"Any of the startTime, endTime, and date of a timeSlot cannot be null",
				error);
	}
	
	@Test
	public void testCreateTimeSlotDateNull() {
		Time startTime = java.sql.Time.valueOf(LocalTime.of(12, 00));
		Time endTime = java.sql.Time.valueOf(LocalTime.of(20, 00));
		Date date = null;
		int maxOrderPerSlot = 100;
		
		TimeSlot ts = null;
		String error = null;
		
		try {
			ts = service.createTimeSlot(startTime,endTime,date,maxOrderPerSlot);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		
		assertNull(ts);
		assertEquals(
				"Any of the startTime, endTime, and date of a timeSlot cannot be null",
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
			error = e.getMessage();
		}
		
		assertNull(ts);
		assertEquals("Event end time cannot be before start time!", error);
	}
	
	
	
	@Test
	public void testIncrementMaxOrderPerslot() {
		Time startTime = java.sql.Time.valueOf(LocalTime.of(12, 00));
		Time endTime = java.sql.Time.valueOf(LocalTime.of(14, 00));
		Date date = java.sql.Date.valueOf(LocalDate.now());
		int maxOrderPerSlot = 100;
		
		TimeSlot ts = null;
		ts = service.createTimeSlot(startTime,endTime,date,maxOrderPerSlot);
		
		Boolean incremented = service.incrementMaxOrderPerslot(ts);
		
		assertTrue(incremented);
		assertEquals(ts.getMaxOrderPerSlot(), 101);
		
	}
	
	
	@Test 
	public void testDecrementMaxOrderPerslot() {
		Time startTime = java.sql.Time.valueOf(LocalTime.of(12, 00));
		Time endTime = java.sql.Time.valueOf(LocalTime.of(14, 00));
		Date date = java.sql.Date.valueOf(LocalDate.now());
		int maxOrderPerSlot = 100;
		
		TimeSlot ts = null;
		ts = service.createTimeSlot(startTime,endTime,date,maxOrderPerSlot);
		
		Boolean decremented = service.decrementMaxOrderPerslot(ts);
		
		assertTrue(decremented);
		assertEquals(ts.getMaxOrderPerSlot(), 99);
		
	}
	
	@Test 
	public void testDecrementMaxOrderPerslotBelowZero() {
		TimeSlot ts = new TimeSlot();
		ts.setMaxOrderPerSlot(0);
		service.decrementMaxOrderPerslot(ts);
		verify(timeSlotDao, times(0)).save(ts);
		
	}
	
	@Test
	public void testDeleteTimeSlotNotThere() {
		Time startTime = java.sql.Time.valueOf(LocalTime.of(12, 00));
		Time endTime = java.sql.Time.valueOf(LocalTime.of(14, 00));
		Date date = java.sql.Date.valueOf(LocalDate.now());
		int maxOrderPerSlot = 100;	
		TimeSlot ts = null;
		ts = service.createTimeSlot(startTime,endTime,date,maxOrderPerSlot);
		String error = "";
		try {
		service.deleteTimeSlot(startTime,endTime,date);
		}catch(Exception e) {
			error = e.getMessage();
		} 
		assertEquals(error, "Unable to find TimeSlot");
	}
	
	@Test
	public void testDeleteTimeSlot() {
		Time startTime = java.sql.Time.valueOf(LocalTime.of(12, 00));
		Time endTime = java.sql.Time.valueOf(LocalTime.of(14, 00));
		Date date = java.sql.Date.valueOf(LocalDate.now());
		
		TimeSlot ts = new TimeSlot();
		ts.setDate(date);
		ts.setEndTime(endTime);
		ts.setStartTime(startTime);
		
		when(timeSlotDao.findByDateAndStartTimeAndEndTime(date, startTime, endTime)).thenReturn(ts);
		
		service.deleteTimeSlot(startTime, endTime, date);
		verify(timeSlotDao).delete(ts);
	}
	
	@Test
	public void testGetAllTimeSlots() {
		TimeSlot slot = new TimeSlot();
		List<TimeSlot> all = new ArrayList<TimeSlot>();
		all.add(slot);
		
		when(timeSlotDao.findAll()).thenReturn(all);
		List<TimeSlot> returned = service.getAllTimeSlots();
		verify(timeSlotDao).findAll();
		
		assertEquals(all, returned);
	}
	
	@Test
	public void testGetTimeSlot() {
		Time startTime = java.sql.Time.valueOf(LocalTime.of(12, 00));
		Time endTime = java.sql.Time.valueOf(LocalTime.of(14, 00));
		Date date = java.sql.Date.valueOf(LocalDate.now());
		
		TimeSlot slot = service.createTimeSlot(startTime, endTime, date, 10);
		
		when(timeSlotDao.findByDateAndStartTimeAndEndTime(date, startTime, endTime)).thenReturn(slot);
		TimeSlot returned = service.getTimeSlot(date, startTime, endTime);
		verify(timeSlotDao, times(2)).findByDateAndStartTimeAndEndTime(date, startTime, endTime); // once for creating and once for getting
		
		assertEquals(slot, returned);
	}
	
	@Test
	public void testGetTimeSlotsBetween() {
		Time startTime = java.sql.Time.valueOf(LocalTime.of(12, 00));
		Time endTime = java.sql.Time.valueOf(LocalTime.of(13, 00));
		Date date = java.sql.Date.valueOf(LocalDate.now());	
		TimeSlot slot = service.createTimeSlot(startTime, endTime, date, 10);
		
		Time startTime1 = java.sql.Time.valueOf(LocalTime.of(8, 00));
		Time endTime1 = java.sql.Time.valueOf(LocalTime.of(10, 00));	
		TimeSlot slot1 = service.createTimeSlot(startTime1, endTime1, date, 10);
		
		Time startTime2 = java.sql.Time.valueOf(LocalTime.of(13, 00));
		Time endTime2 = java.sql.Time.valueOf(LocalTime.of(14, 00));	
		TimeSlot slot2 = service.createTimeSlot(startTime2, endTime2, date, 10);
		
		Time startTime3 = java.sql.Time.valueOf(LocalTime.of(15, 00));
		Time endTime3 = java.sql.Time.valueOf(LocalTime.of(17, 00));	
		TimeSlot slot3 = service.createTimeSlot(startTime3, endTime3, date, 10);
		
		List<TimeSlot> all = new ArrayList<TimeSlot>();
		all.add(slot);
		all.add(slot1); 
		all.add(slot2);
		all.add(slot3);
		
		
		
		when(timeSlotDao.findByDate(date)).thenReturn(all);
		List<TimeSlot> returned = service.getTimeSlotsBetween(date, java.sql.Time.valueOf(LocalTime.of(11, 00)), java.sql.Time.valueOf(LocalTime.of(15, 00)));
		verify(timeSlotDao).findByDate(date); // once for creating and once for getting
		
		assertTrue(returned.contains(slot));
		assertTrue(returned.contains(slot2));
		assertTrue(!returned.contains(slot1));
		assertTrue(!returned.contains(slot3));
	}
	
	
	
/*Tests Related to Order Service Methods*/
	@Test
	public void testCreateOrderAndSetPaymentAndSetCompleted() {

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
		
		service.setPayment(order, "Cash");
		service.setCompleted(order);
		
		assertEquals(order.getPayment(), "Cash");
		assertEquals(order.getCompleted(), true);

		
		
	}
	
	@Test
	public void testCreateOrderAlreadyAttachedToCart() {

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
		
		when(orderDao.findByCart(testCart)).thenReturn(TEST_ORDER);
		
		Order order = service.createOrder(comp, date, total,  payment, testCart);
		
		assertNull(order);
	}
	
	@Test
	public void testCreateOrderNullDate() {
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
			error = e.getMessage();
		}
		
		assertNull(order);
		assertEquals(
				"Any of the date, payment, and cart of a order cannot be null", error);
		
	} 
	
	@Test
	public void testCreateOrderNullPayment() {
		boolean comp = false;
		Date date = java.sql.Date.valueOf(LocalDate.of(2022, 12, 1));
		int total = 0;
		String payment = null;
		Cart cart = new Cart();
		
		String error = null;
		Order order = null;
		
		try {
			order = service.createOrder(comp, date, total,  payment, cart);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		
		assertNull(order);
		assertEquals(
				"Any of the date, payment, and cart of a order cannot be null", error);
		
	} 
	
	@Test
	public void testCreateOrderNullCart() {
		boolean comp = false;
		Date date = java.sql.Date.valueOf(LocalDate.of(2022, 12, 1));
		int total = 0;
		String payment = "cash";
		Cart cart = null;
		
		String error = null;
		Order order = null;
		
		try {
			order = service.createOrder(comp, date, total,  payment, cart);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		
		assertNull(order);
		assertEquals(
				"Any of the date, payment, and cart of a order cannot be null", error);
		
	} 
	
	@Test
	public void testGetOrdersByCustomer() {
		List<Order> testList = service.getOrdersByCustomer(CART_TESTING_CUSTOMER);
		assertEquals(TEST_ORDER, testList.get(0));
	}
	
	@Test
	public void testGetOrderByCart() {
		assertEquals(TEST_ORDER, service.getOrderByCart(TEST_CART));
	}
	
	@Test
	public void testGetAllOrders() {
		List<Order> testList = service.getAllOrders();
		assertEquals(TEST_ORDER, testList.get(0));
		assertEquals(TEST_ORDER2, testList.get(1));

	}	
	
	
/*Tests Related to Shift Service Methods*/
	@Test
	public void testCreateShift() {
		Time startTime = java.sql.Time.valueOf(LocalTime.of(12, 00));
		Time endTime = java.sql.Time.valueOf(LocalTime.of(14, 00));
		Date date = java.sql.Date.valueOf(LocalDate.now());
		Employee employee = new Employee("testEmail@gmail.com", "TestName", "TestPsw", EmployeeStatus.Active);
		
		Shift s = null;
		
		try {
			s = service.createShift(startTime, endTime, date, employee);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
	when(shiftDao.findByDateAndEmployee(date, employee)).thenReturn(s);
	Shift s2 = service.createShift(startTime, endTime, date, employee);
		assertNull(s2);
		assertNotNull(s);
		assertEquals(s.getStartHour(),startTime);
		assertEquals(s.getEndHour(),endTime);
		assertEquals(s.getDate(),date);
		assertEquals(s.getEmployee(), employee);
	}
	
	
	
	
	@Test
	public void testCreateShiftNullDate() {
		Time startTime = null;
		Time endTime = null;
		Date date = null;
		Employee employee = new Employee("testEmail@gmail.com", "TestName", "TestPsw", EmployeeStatus.Active);
		Shift s = null;
		String error = null;
		
		try {
			s = service.createShift(startTime, endTime, date, employee);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		
		assertNull(s);
		assertEquals(
				"startHour, endHour and date of a shift cannot be null", error);
	}
	
	@Test
	public void testCreateShiftNullTimeStart() {
		Time startTime = null;
		Time endTime = null;
		Date date = java.sql.Date.valueOf(LocalDate.now());
		Employee employee = new Employee("testEmail@gmail.com", "TestName", "TestPsw", EmployeeStatus.Active);
		Shift s = null;
		String error = null;
		
		try {
			s = service.createShift(startTime, endTime, date, employee);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		
		assertNull(s);
		assertEquals(
				"startHour, endHour and date of a shift cannot be null", error);
	}
	
	@Test
	public void testCreateShiftNullTimeEnd() {
		Time startTime = java.sql.Time.valueOf(LocalTime.of(12, 00));
		Time endTime = null;
		Date date = java.sql.Date.valueOf(LocalDate.now());
		Employee employee = new Employee("testEmail@gmail.com", "TestName", "TestPsw", EmployeeStatus.Active);
		Shift s = null;
		String error = null;
		
		try {
			s = service.createShift(startTime, endTime, date, employee);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		
		assertNull(s);
		assertEquals(
				"startHour, endHour and date of a shift cannot be null", error);
	}
	
	
	@Test
	public void testCreateShiftNullEmployee() {
		Time startTime = java.sql.Time.valueOf(LocalTime.of(12, 00));
		Time endTime = java.sql.Time.valueOf(LocalTime.of(14, 00));
		Date date = java.sql.Date.valueOf(LocalDate.now());
		Employee employee = null;
		Shift s = null;
		String error = null;
		
		try {
			s = service.createShift(startTime, endTime, date, employee);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		
		assertNull(s);
		assertEquals(
				"the employee that the shift is assigned to cannot be null", error);
	}
	
	@Test
	public void testGetShiftByEmployee() {
		Employee employee = new Employee();
		Shift shift1 = new Shift();
		Shift shift2 = new Shift();
		List<Shift> all = new ArrayList<Shift>();
		
		all.add(shift1);
		all.add(shift2);
		
		when(shiftDao.findByEmployee(employee)).thenReturn(all);
		List<Shift> returned = service.getShiftByEmployee(employee);
		
		verify(shiftDao).findByEmployee(employee);
		assertEquals(all, returned);
		
		
	}
	
	@Test
	public void testGetAllShifts() {
		Shift shift1 = new Shift();
		Shift shift2 = new Shift();
		List<Shift> all = new ArrayList<Shift>();
		
		all.add(shift1);
		all.add(shift2);
		
		when(shiftDao.findAll()).thenReturn(all);
		List<Shift> returned = service.getAllShifts();
		
		verify(shiftDao).findAll();
		assertEquals(all, returned);
		
		
	}
	
	@Test 
	public void testGetEmployeesForTimeSlot(){
		Time startTime = java.sql.Time.valueOf(LocalTime.of(6, 30));
		Time endTime = java.sql.Time.valueOf(LocalTime.of(10, 15));
		Date date = java.sql.Date.valueOf(LocalDate.now());	
		Employee employee = new Employee();
		Shift shift = service.createShift(startTime, endTime, date, employee);
		
		Time startTime1 = java.sql.Time.valueOf(LocalTime.of(7, 55));
		Time endTime1 = java.sql.Time.valueOf(LocalTime.of(10,05));	
		Shift shift1 = service.createShift(startTime1, endTime1, date, employee);
		
		Time startTime2 = java.sql.Time.valueOf(LocalTime.of(13, 00));
		Time endTime2 = java.sql.Time.valueOf(LocalTime.of(14, 00));	
		Shift shift2 = service.createShift(startTime2, endTime2, date, employee);
		
		Time startTime3 = java.sql.Time.valueOf(LocalTime.of(7, 00));
		Time endTime3 = java.sql.Time.valueOf(LocalTime.of(9, 00));	
		Shift shift3 = service.createShift(startTime3, endTime3, date, employee);
		
		List<Shift> all = new ArrayList<Shift>();
		all.add(shift); 
		all.add(shift1); 
		all.add(shift2);
		all.add(shift3);
		
		
		
		when(shiftDao.findByDate(date)).thenReturn(all);
		int returned = service.getEmployeesForTimeSlot(date, java.sql.Time.valueOf(LocalTime.of(8, 00)), java.sql.Time.valueOf(LocalTime.of(10, 00)));
		verify(shiftDao).findByDate(date); // once for creating and once for getting
		
		assertEquals(2, returned);
	}

/*Tests Related to Product Service Methods*/
	@Test
	public void testCreateProduct() {
		assertEquals(0, service.getAllProduct().size());
		Product.PriceType type = Product.PriceType.PER_UNIT; 
		String productName = PRODUCT_KEY_CREATE;
		String isAviliableOnline = "yes";  // Again, it is too deep into the project to fix it into a boolean. So it will stay a String.
		int price = 5;
		int stock = 50;
		Product p = null;
		
		try {
			p = service.createProduct(type, productName, isAviliableOnline, price, stock);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		assertNotNull(p);
		assertEquals(p.getPriceType(), type);
		assertEquals(p.getProductName(), productName);
		assertEquals(p.getIsAvailableOnline(),isAviliableOnline);
		assertEquals(p.getPrice(),price);
		assertEquals(p.getStock(), stock);
		
	}
	
	
	
	@Test
	public void testCreateProductNullType() {
		Product.PriceType type = null; 
		String productName = "name";
		String isAviliableOnline = "yes";
		int price = 5;
		int stock = 50;
		Product p = null;
		String error = null;
		
		try {
			p = service.createProduct(type, productName, isAviliableOnline, price, stock);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		
		assertNull(p);
		assertEquals(
				"Any of the priceType, productName and isAvailableOnline of a product cannot be null", error);
	}
	
	@Test
	public void testCreateProductNullName() {
		Product.PriceType type = Product.PriceType.PER_UNIT; 
		String productName = null;
		String isAviliableOnline = "yes";
		int price = 5;
		int stock = 50;
		Product p = null;
		String error = null;
		
		try {
			p = service.createProduct(type, productName, isAviliableOnline, price, stock);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		
		assertNull(p);
		assertEquals(
				"Any of the priceType, productName and isAvailableOnline of a product cannot be null", error);
	}
	
	@Test
	public void testCreateProductNullAvailability() {
		Product.PriceType type = Product.PriceType.PER_UNIT; 
		String productName = "name";
		String isAviliableOnline = null;
		int price = 5;
		int stock = 50;
		Product p = null;
		String error = null;
		
		try {
			p = service.createProduct(type, productName, isAviliableOnline, price, stock);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		
		assertNull(p);
		assertEquals(
				"Any of the priceType, productName and isAvailableOnline of a product cannot be null", error);
	}
	
	@Test
	public void testCreateProductEmptyName() {
		Product.PriceType type = Product.PriceType.PER_UNIT; 
		String productName = "";
		String isAviliableOnline = "yes";
		int price = 5;
		int stock = 50;
		Product p = null;
		String error = null;
		
		try {
			p = service.createProduct(type, productName, isAviliableOnline, price, stock);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		
		assertNull(p);
		assertEquals(
				"Any of the priceType, productName and isAvailableOnline of a product cannot be null", error);
	}
	
	@Test
	public void testCreateProductEmptyAvailbility() {
		Product.PriceType type = Product.PriceType.PER_UNIT; 
		String productName = "name";
		String isAviliableOnline = "";
		int price = 5;
		int stock = 50;
		Product p = null;
		String error = null;
		
		try {
			p = service.createProduct(type, productName, isAviliableOnline, price, stock);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		
		assertNull(p);
		assertEquals(
				"Any of the priceType, productName and isAvailableOnline of a product cannot be null", error);
	}
	

	@Test
	public void testCreateProductNegativeStock() {
		Product.PriceType type = Product.PriceType.PER_UNIT; 
		String isAviliableOnline = "yes";
		int price = 5;
		int stock = -50;
		Product p = null;
		String error = null;
		
		try {
			p = service.createProduct(type, PRODUCT_KEY_NEG_STOCK, isAviliableOnline, price, stock);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		
		assertNull(p);
		assertEquals(
				"stock cannot be negative", error);
	}
	
	
	
	@Test
	public void testCreateProductNegativePrice() {
		Product.PriceType type = Product.PriceType.PER_UNIT; 
		String isAviliableOnline = "yes";
		int price = -5;
		int stock = 50;
		Product p = null;
		String error = null;
		
		try {
			p = service.createProduct(type, PRODUCT_KEY_NEG_PRICE, isAviliableOnline, price, stock);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			error = e.getMessage();
		}
		
		assertNull(p); 
		assertEquals(
				"price cannot be negative", error);
	}
	
	@Test
	public void testSetProductStock() {
		Product.PriceType type = Product.PriceType.PER_UNIT; 
		String isAviliableOnline = "yes";
		int price = 10;
		int stock = 50;
		
		Product p = service.createProduct(type, PRODUCT_KEY, isAviliableOnline, price, stock);
		
		 
		p = service.setProductStock(PRODUCT_KEY, 100);
		when(productDao.findByProductName("noname")).thenReturn(null);
		Product p2 = service.setProductStock("noname", stock);
		assertNull(p2);
		assertEquals(p.getStock(), 100);
		
	}
	
	@Test 
	public void testDeleteProduct() {
		Product p = service.deleteProduct(PRODUCT_KEY_DELETE);
		assertEquals(p.getProductName(), PRODUCT_KEY_DELETE);
	    verify(productDao, times(1)).delete(p);
	}
	
	@Test
	public void testDeleteNullProduct(){
		Product p = new Product();
		p.setProductName("PRODUCT_DNE");
		String error = null;
		try {
			service.deleteProduct(p.getProductName());
		}catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals(error, "Cannot find Product to delete");
	}
	
	@Test
	public void testGetProductByName() {
		Product.PriceType type = Product.PriceType.PER_UNIT; 
		String isAviliableOnline = "yes";
		String name = "testName";
		int price = 10;
		int stock = 50;
		Product p = service.createProduct(type, name, isAviliableOnline, price, stock);
		
		when(productDao.findByProductName(name)).thenReturn(p);
		
		Product returned = service.getProductByName(name);
		verify(productDao, times(2)).findByProductName(name);
		assertEquals(p, returned);
	}
	
	@Test
	public void testGetProductByStockGreaterThan() {
		Product p = new Product();
		p.setStock(5);
		Product p2 = new Product();
		p2.setStock(5);
		Product p3 = new Product();
		p3.setStock(5);
		
		List<Product> all = new ArrayList<Product>();
		all.add(p);
		all.add(p2);
		all.add(p3);
		
		when(productDao.findByStockGreaterThan(2)).thenReturn(all);
		
		List<Product> returned = service.getProductByStockGreaterThan(2);
		verify(productDao).findByStockGreaterThan(2);
		assertEquals(all, returned);
	}

		
	
/*Tests Related to Store Service Methods*/ 

	@Test
	public void  testCreateStore() {
		String testPhoneNumber = "123-456-7890";
		String testEmail = "test@test.com";
		Time testOpenTime = java.sql.Time.valueOf(LocalTime.of(8, 00));
		Time testCloseTime = java.sql.Time.valueOf(LocalTime.of(20, 00));
		int testOutOfTownFee = 15;
		
		String email = "owner@mail.com";
		String name = "TestOwner";
		String password = "pw1234";
		StoreOwner testStoreOwner = service.createStoreOwner(email,name,password);
		
		String town = "TestTown";
		String street = "TestStreet";
		String postalCode = "TestPostalCode";
		int unit = 321;	  
		Address address = service.createAddresses(town,street,postalCode,unit);
		Store testStore = null;
		
		try {
			testStore = service.createStore(testPhoneNumber, testEmail, testOpenTime, testCloseTime, testStoreOwner, address, testOutOfTownFee);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		
		
		assertNotNull(testStore);
		assertEquals(testStore.getTelephone(), testPhoneNumber);
		assertEquals(testStore.getEmail(), testEmail);
		assertEquals(testStore.getOpeningHour(),testOpenTime);
		assertEquals(testStore.getClosingHour(), testCloseTime);
		assertEquals(testStore.getStoreOwner(), testStoreOwner);
		assertEquals(testStore.getAddress(), address);
		assertEquals(testStore.getOutOfTownFee(), testOutOfTownFee);
		
		service.deleteStore(testStore);
		service.createStore(testStore);
		
		assertNotNull(testStore);
		assertEquals(testStore.getTelephone(), testPhoneNumber);
		assertEquals(testStore.getEmail(), testEmail);
		assertEquals(testStore.getOpeningHour(),testOpenTime);
		assertEquals(testStore.getClosingHour(), testCloseTime);
		assertEquals(testStore.getStoreOwner(), testStoreOwner);
		assertEquals(testStore.getAddress(), address);
		assertEquals(testStore.getOutOfTownFee(), testOutOfTownFee);
		
		
	}
	
	
	@Test
	public void testCreateExistingStore(){
		String testPhoneNumber = "123-456-7890";
		String testEmail = "test@test.com";
		Time testOpenTime = java.sql.Time.valueOf(LocalTime.of(8, 00));
		Time testCloseTime = java.sql.Time.valueOf(LocalTime.of(20, 00));
		int testOutOfTownFee = 15;
		
		String email = "owner@mail.com";
		String name = "TestOwner";
		String password = "pw1234";
		StoreOwner testStoreOwner = service.createStoreOwner(email,name,password);
		
		String town = "TestTown";
		String street = "TestStreet";
		String postalCode = "TestPostalCode";
		int unit = 321;	  
		Address address = service.createAddresses(town,street,postalCode,unit);
		Store testStore = service.createStore(testPhoneNumber, testEmail, testOpenTime, testCloseTime, testStoreOwner, address, testOutOfTownFee);
		//now testing duplicate store 
		
		when(storeDao.findByAddress(address)).thenReturn(testStore);
		
 		Store testStore2 = service.createStore(testPhoneNumber, testEmail, testOpenTime, testCloseTime, testStoreOwner, address, testOutOfTownFee);	
	
		assertNull(testStore2);
	}
	
	
	@Test 
	public void testSetOwner(){
		String testPhoneNumber = "123-456-7890";
		String testEmail = "test@test.com";
		Time testOpenTime = java.sql.Time.valueOf(LocalTime.of(8, 00));
		Time testCloseTime = java.sql.Time.valueOf(LocalTime.of(20, 00));
		int testOutOfTownFee = 15;
		
		String email = "owner@mail.com";
		String name = "TestOwner";
		String password = "pw1234";
		StoreOwner testStoreOwner = service.createStoreOwner(email,name,password);
	
		String town = "TestTown";
		String street = "TestStreet";
		String postalCode = "TestPostalCode";
		int unit = 321;	  
		Address address = service.createAddresses(town,street,postalCode,unit);
		Store testStore = service.createStore(testPhoneNumber, testEmail, testOpenTime, testCloseTime, testStoreOwner, address, testOutOfTownFee);
		
		String email2 = "owner2@mail.com";
		String name2 = "newOwner";
		String password2 = "pw21234";
		StoreOwner testStoreOwner2 = service.createStoreOwner(email2,name2,password2);
		
		service.setOwner(testStore, testStoreOwner2);
		assertEquals(testStore.getStoreOwner(),testStoreOwner2);
		
	}
	
	
	@Test 
	public void testDeleteStore() {
		String testPhoneNumber = "123-456-7890";
		String testEmail = "test@test.com";
		Time testOpenTime = java.sql.Time.valueOf(LocalTime.of(8, 00));
		Time testCloseTime = java.sql.Time.valueOf(LocalTime.of(20, 00));
		int testOutOfTownFee = 15;
		
		String email = "owner@mail.com";
		String name = "TestOwner";
		String password = "pw1234";
		StoreOwner testStoreOwner = service.createStoreOwner(email,name,password);
		Store testStore = service.createStore(testPhoneNumber, testEmail, testOpenTime, testCloseTime, testStoreOwner, ADDRESS_KEY_DELETING, testOutOfTownFee);
		Store testStore2 = new Store(testPhoneNumber, testEmail, testOpenTime, testCloseTime, testStoreOwner, ADDRESS_KEY_DELETING, testOutOfTownFee);	    
	    

//	    when(storeDao.findByAddress(ADDRESS_KEY_DELETING)).thenReturn(testStore); //expect a fetch, return a "fetched" person;

	    service.deleteStore(testStore);

	    verify(storeDao, times(1)).delete(testStore);
		
	}
	
	@Test
	public void testGetStore() {
		List<Store> all = new ArrayList<Store>();
		Store store = new Store();
		store.setAddress(ADDRESS_KEY);
		all.add(store);
		when(storeDao.findAll()).thenReturn(all);
		Store foundStore = service.getStore();
		verify(storeDao).findAll();
		assertEquals(store.getAddress(), foundStore.getAddress());
		}
	
	@Test
	public void testGetNonExistingStore(){
		List<Store> all = new ArrayList<Store>();
		when(storeDao.findAll()).thenReturn(all);
		String error = null;
		
		try{
			Store foundStore = service.getStore();
		}catch(Exception e) {
			error = e.getMessage();
		}
		verify(storeDao).findAll();
		assertEquals(error, "There is no Store in Repository");
		
	}
	
/*Tests Related to CartItem Service Methods*/
	
	@Test
	public void testCreateCartItem(){
		assertEquals(0, service.getAllCartItems().size());
		Product.PriceType type = Product.PriceType.PER_UNIT; 	
		String isAviliableOnline = "yes";  // Again, it is too deep into the project to fix it into a boolean. So it will stay a String.
		int price = 5;
		int stock = 50;
		
		Product p = new Product(type, PRODUCT_KEY, isAviliableOnline, price, stock);
		
		CartItem testCartItem = service.createCartItem(100, p, TEST_CART);
		
		assertEquals(testCartItem.getCart(), TEST_CART);
		assertEquals(testCartItem.getProduct(), p);
		assertEquals(testCartItem.getQuantity(), 100);
	
	}
	
	@Test
	public void testCreateCartItemProductNull(){
		assertEquals(0, service.getAllCartItems().size());
		Product.PriceType type = Product.PriceType.PER_UNIT; 	
		String isAviliableOnline = "yes";  // Again, it is too deep into the project to fix it into a boolean. So it will stay a String.
		int price = 5;
		int stock = 50;
		InStorePurchase purchase = new InStorePurchase();
		
		Product p = new Product(type, PRODUCT_KEY, isAviliableOnline, price, stock);
		
		when(productDao.findByProductName(PRODUCT_KEY)).thenReturn(null);
		
		CartItem testCartItem = service.createCartItem(100, p, TEST_CART);
		when(productDao.findByProductName(p.getProductName())).thenReturn(null);
		CartItem testCartItem2 = service.createCartItem(10, p, purchase);
		assertNull(testCartItem);
		assertNull(testCartItem2);
	
	}
	
	@Test
	public void testCreateCartItemWithInstore() {
		Product.PriceType type = Product.PriceType.PER_UNIT; 	
		String isAviliableOnline = "yes";  // Again, it is too deep into the project to fix it into a boolean. So it will stay a String.
		int price = 5;
		int stock = 50;	
		Product p = new Product(type, PRODUCT_KEY, isAviliableOnline, price, stock);
		
		InStorePurchase testPurchase = service.createInStorePurchase(150, TESTING_DATE);
		
		CartItem testCartItem = service.createCartItem(100, p, testPurchase);
		
		assertEquals(testCartItem.getInStorePurchase(), testPurchase);
		assertEquals(testCartItem.getProduct(), p);
		assertEquals(testCartItem.getQuantity(), 100);
	} 
	
	@Test
	public void testDeleteCartItem() {
		Product.PriceType type = Product.PriceType.PER_UNIT; 	
		String isAviliableOnline = "yes";  // Again, it is too deep into the project to fix it into a boolean. So it will stay a String.
		int price = 5;
		int stock = 50;
		
		Product p = new Product(type, PRODUCT_KEY, isAviliableOnline, price, stock);
		CartItem testCartItem = service.createCartItem(100, p, TEST_CART);
		
		service.deleteCartItem(testCartItem);
		verify(cartItemDao, times(1)).delete(testCartItem);
	}
	
	@Test
	public void testDeleteNullCartItem() {
		CartItem testItem = null; 
		String error = null;
		try {
			service.deleteCartItem(testItem);
		}catch(Exception e) {
			error = e.getMessage();
		}
		assertEquals(error, "Item you are trying to delete is null");
	}
	
	@Test
	public void testGetCartItemsByCart() {
		Cart cart = new Cart();
		CartItem item1 = new CartItem();
		CartItem item2 = new CartItem();
		item1.setCart(cart);
		item2.setCart(cart);
	List<CartItem> all = new ArrayList<CartItem>();
	all.add(item1);
	all.add(item2);
	
	when(cartItemDao.findByCart(cart)).thenReturn(all);
	List<CartItem> newAll;
	newAll = service.getCartItemsByCart(cart);
	
	verify(cartItemDao).findByCart(cart);
	assertEquals(all, newAll);
	}
	
	@Test
	public void testGetCartItemsByByInStorePurchase() {
		InStorePurchase purchase = new InStorePurchase();
		CartItem item1 = new CartItem();
		CartItem item2 = new CartItem();
		item1.setInStorePurchase(purchase);
		item2.setInStorePurchase(purchase);
	List<CartItem> all = new ArrayList<CartItem>();
	all.add(item1);
	all.add(item2);
	
	when(cartItemDao.findByInStorePurchase(purchase)).thenReturn(all);
	List<CartItem> newAll;
	newAll = service.getCartItemsByInStorePurchase(purchase);
	
	verify(cartItemDao).findByInStorePurchase(purchase);
	assertEquals(all, newAll);
	}
	
	@Test
	public void testGetCartItemsByProductAndCart() {
		Cart cart = new Cart();
		CartItem item1 = new CartItem();
		CartItem item2 = new CartItem();
		item1.setCart(cart);
		item2.setCart(cart);
		
		Product product1 = new Product();
		Product product2 = new Product();
		item1.setProduct(product1);
		item2.setProduct(product2);
		
	List<CartItem> all = new ArrayList<CartItem>();
	all.add(item1);
	all.add(item2);
	
	when(cartItemDao.findByCartAndProduct(cart, product1)).thenReturn(all.get(0));
	
	CartItem returned = service.getCartItemByProductAndCart(product1, cart);
	
	verify(cartItemDao).findByCartAndProduct(cart, product1);
	assertEquals(item1, returned);
	}
	
	@Test
	public void testGetCartItemByProductAndInStorePurchase() {
		Product product = new Product();
		InStorePurchase purchase = new InStorePurchase();
		CartItem item = new CartItem();
		item.setProduct(product);
		item.setInStorePurchase(purchase);
		
	when(cartItemDao.findByInStorePurchaseAndProduct(purchase, product)).thenReturn(item);
	CartItem returned = service.getCartItemByProductAndInStorePurchase(product, purchase);
	
	verify(cartItemDao).findByInStorePurchaseAndProduct(purchase, product);
	assertEquals(item, returned);
	assertEquals(item.getProduct(), returned.getProduct());
	assertEquals(item.getInStorePurchase(), returned.getInStorePurchase());
		
	}
	
	@Test
	public void testGetCartItemByProductAndQuantity() {
		Product product1 = new Product();
		
		CartItem item = new CartItem();
		item.setQuantity(5);
		item.setProduct(product1);
		
		CartItem item1 = new CartItem();
		item1.setProduct(product1);
		item1.setQuantity(5);
		
		CartItem item2 = new CartItem();
		item2.setProduct(product1);
		item2.setQuantity(5);
		
	List<CartItem> all = new ArrayList<CartItem>();
	all.add(item);
	all.add(item1);
	all.add(item2);
	
	when(cartItemDao.findByProductAndQuantity(product1, 5)).thenReturn(all);
	List<CartItem> newAll;
	newAll = service.getCartItemsByProductAndQuantity(product1, 5);
	
	verify(cartItemDao).findByProductAndQuantity(product1, 5);
	assertEquals(all, newAll);
		
		
	}
	
/*Tests Related to InStorePurchase Service Methods*/
	
	@Test
	public void testCreateInStorePurchaseAndSetAndGet() {
		assertEquals(0, service.getAllInStorePurchases().size());
		InStorePurchase testPurchase = service.createInStorePurchase(150, TESTING_DATE);
		
		assertEquals(testPurchase.getPurchaseDate(), TESTING_DATE);
		assertEquals(testPurchase.getTotal(), 150);
		
		service.setInStorePurchaseTotal(1000, testPurchase);
		
		assertEquals(testPurchase.getTotal(), 1000);
	}
	
	
}
