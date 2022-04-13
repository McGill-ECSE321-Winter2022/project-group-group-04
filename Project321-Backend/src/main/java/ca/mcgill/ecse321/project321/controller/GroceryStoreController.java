package ca.mcgill.ecse321.project321.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.project321.dto.AddressDTO;
import ca.mcgill.ecse321.project321.dto.CartDTO;
import ca.mcgill.ecse321.project321.dto.CartItemDTO;
import ca.mcgill.ecse321.project321.dto.CustomerDTO;
import ca.mcgill.ecse321.project321.dto.EmployeeDTO;
import ca.mcgill.ecse321.project321.dto.InStorePurchaseDTO;
import ca.mcgill.ecse321.project321.dto.EmployeeDTO.EmployeeStatusDTO;
import ca.mcgill.ecse321.project321.dto.OrderDTO;
import ca.mcgill.ecse321.project321.dto.ProductDTO;
import ca.mcgill.ecse321.project321.dto.TimeSlotDTO;
import ca.mcgill.ecse321.project321.dto.UserDTO;
import ca.mcgill.ecse321.project321.dto.CartDTO.ShoppingTypeDTO;
import ca.mcgill.ecse321.project321.dto.ProductDTO.PriceTypeDTO;
import ca.mcgill.ecse321.project321.dto.ShiftDTO;
import ca.mcgill.ecse321.project321.dto.StoreDTO;
import ca.mcgill.ecse321.project321.dto.StoreOwnerDTO;
import ca.mcgill.ecse321.project321.model.Address;
import ca.mcgill.ecse321.project321.model.Cart;
import ca.mcgill.ecse321.project321.model.CartItem;
import ca.mcgill.ecse321.project321.model.Customer;
import ca.mcgill.ecse321.project321.model.Employee;
import ca.mcgill.ecse321.project321.model.InStorePurchase;
import ca.mcgill.ecse321.project321.model.Employee.EmployeeStatus;
import ca.mcgill.ecse321.project321.model.Order;
import ca.mcgill.ecse321.project321.model.Product;
import ca.mcgill.ecse321.project321.model.TimeSlot;
import ca.mcgill.ecse321.project321.model.User;
import ca.mcgill.ecse321.project321.model.Cart.ShoppingType;
import ca.mcgill.ecse321.project321.model.Product.PriceType;
import ca.mcgill.ecse321.project321.model.Shift;
import ca.mcgill.ecse321.project321.model.Store;
import ca.mcgill.ecse321.project321.model.StoreOwner;
import ca.mcgill.ecse321.project321.service.GroceryStoreService;

@CrossOrigin(origins = "*")
@RestController
public class GroceryStoreController {
	
    public static String adminCode = "admin";

    private class AccountDTO{
        private boolean exists;

        public AccountDTO(boolean exists) {
            this.exists = exists;
        }

        public void setExists(boolean exists) {
            this.exists = exists;
        }

        public boolean getExists() {
            return this.exists;
        }
    }
    
    @Autowired
    private GroceryStoreService service;

    @GetMapping(value = {"/login", "/login/"})
    public UserDTO login(@RequestParam(name = "email")     String email,
                            @RequestParam(name = "password")  String password) throws IllegalArgumentException, IllegalStateException {
        User u = service.getUser(email);
        if( u == null) {
            throw new IllegalArgumentException("Failed to find user with email or username " + email + " in database");
        }
        if(!u.getPassword().equals(password)) {
            throw new IllegalArgumentException("Wrong password!");
        }
        UserDTO user = convertToDTO(u);
        if(u instanceof Customer) {
            user.setType("customer");
        } else if(u instanceof Employee) {
            user.setType("employee");
        } else if(u instanceof StoreOwner) {
            user.setType("owner");
        } else {
            throw new IllegalStateException("Internal model failure: user is neither customer, employee nor owner");
        }
        return user;
    }

    @GetMapping(value = {"/userexists", "/userexists/"})
    public AccountDTO userExists(@RequestParam(name = "email")     String email) {
        AccountDTO a = new AccountDTO(false);
        if(service.getUser(email) != null) {
            a.setExists(true);
        }
        return a;
    }

    @GetMapping(value = {"/customers", "/customers/"})
    public List<CustomerDTO> getAllCustomers() throws IllegalArgumentException{
        return convertCustomerListToDTO(service.getAllCustomers());
    }

    @GetMapping(value = {"/customer", "/customer/"})
    public CustomerDTO getCustomer(@RequestParam(name = "email") String email) throws IllegalArgumentException{
        return convertToDTO(service.getCustomer(email));
    }

    @PostMapping(value = {"/customers", "/customers/"})
    public CustomerDTO createCustomer(@RequestParam(name = "email")     String email, 
                                      @RequestParam(name = "name")      String name, 
                                      @RequestParam(name = "password")  String password, 
                                      @RequestParam(name = "phone")     String phone, 
                                      @RequestParam(name = "town")      String town,
                                      @RequestParam(name = "street")      String street, 
                                      @RequestParam(name = "postalcode")  String postalcode,
                                      @RequestParam(name = "unit")     int unit) 
    throws IllegalArgumentException {
        Address a = service.getAddresseByUnitAndStreetAndTownAndPostalCode(unit, street, town, postalcode);
        if(a == null) {
            a = service.createAddresses(town, street, postalcode, unit);
        }
        Customer c = service.createCustomer(email, name, password, phone, a);
        if(c == null) {
            throw new IllegalArgumentException("Customer already exsist");
        }
        //System.out.println(c.getName());
        return convertToDTO(c);
    }

    @PostMapping(value = {"/customers/address", "/customers/address/"})
    public CustomerDTO setCustomersAddress(@RequestParam(name = "town")      String town,
                                            @RequestParam(name = "street")      String street, 
                                            @RequestParam(name = "postalcode")  String postalcode,
                                            @RequestParam(name = "unit") int unit,
                                            @RequestParam(name = "customeremail") String customerEmail,
                                            @RequestParam(name = "customerpassword") String customerPassword) throws IllegalArgumentException {
        CheckUser(customerEmail, customerPassword, "customer", "Must be logged in as customer to change a customer's address");
        Customer c = service.setCustomersAddress(customerEmail, town, street, postalcode, unit);
        if(c == null) {
            throw new IllegalArgumentException("Cannot seem to find customer in database...");
        }
        return convertToDTO(c);
    }
    
    @GetMapping(value = {"/storeOwners", "/storeOwners/"})
    public StoreOwnerDTO getStoreOwner() throws IllegalArgumentException{
        return convertToDTO(service.getStoreOwner());
    }
    
    @PostMapping(value = {"/storeOwners", "/storeOwners/"})
    public StoreOwnerDTO createStoreOwner(@RequestParam(name = "email")     String email, 
                                          @RequestParam(name = "name")      String name, 
                                          @RequestParam(name = "password")  String password,
                                          @RequestParam(name = "adminCode")  String code) 
    throws IllegalArgumentException, IllegalAccessException {
    	if (!adminCode.equals(code)) {
    		throw new IllegalAccessException("You need the correct admin code to create owner");
    	}
        if(service.getStoreOwner() != null) {
        	return setStoreOwnerInfo(email, name, password, code);
        }
        StoreOwner c = service.createStoreOwner(email, name, password);
        System.out.println(c.getName());
        return convertToDTO(c);
    }

    @PostMapping(value = {"/storeOwners/info", "/storeOwners/info/"})
    public StoreOwnerDTO setStoreOwnerInfo(@RequestParam(name = "email")     String email, 
                                           @RequestParam(name = "name")      String name, 
                                           @RequestParam(name = "password")  String password,
                                           @RequestParam(name = "adminCode")  String code)
    throws IllegalArgumentException, IllegalAccessException {
    	if (!adminCode.equals(code)) {
    		throw new IllegalAccessException("You need the correct admin code to set StoreOwnerInfo");
    	}
        StoreOwner c = service.setStoreOwnerInfo(email, name, password);
        return convertToDTO(c);
    }

    @GetMapping(value = {"/employee/{email}", "/employee/{email}/"})
    public EmployeeDTO getEmployee(@PathVariable("email") String email) throws IllegalArgumentException{
        return convertToDTO(service.getEmployee(email));
    }
    
    @GetMapping(value = {"/carts", "/carts/"})
    public List<CartDTO> getAllCarts() throws IllegalArgumentException {
        return convertCartListToDTO(service.getAllCarts());
    }
    
    /**
     * Method partly related to Req.03 (1/3): As a user of the Grocery software system with a customer account, I 
     * would like to add items into a cart if they are online shopperable and checkout when I am ready for payment 
     * Use to create a new cart under the customer account
     * URL: localhost:8080/carts?type=aType&customeremail=aEmail&customerpassword=aPassword
     * @param type Type of shopping, either Delivery or Pick-up
     * @param customerEmail Email of customer for identification
     * @param customerPassword Password of customer for identification
     * @return The data transfer object of the cart created
     * @throws IllegalStateException When there is already an opened cart under the customer account
     */
    @PostMapping(value = {"/carts", "/carts/"})
    public CartDTO createCart(@RequestParam(name = "type")      ShoppingTypeDTO type,
                              @RequestParam(name = "customeremail")  String customerEmail, 
                              @RequestParam(name = "customerpassword") String customerPassword) throws IllegalStateException{

        Cart cart = retrieveOpenCart(customerEmail, customerPassword);
        if(cart != null) {
            throw new IllegalStateException("Failed to create a new cart: an opened cart already exists, delete the opened cart or carry on the purchase on this one");
        }
        Date creationDate = java.sql.Date.valueOf(LocalDate.now());
        Time creationTime = java.sql.Time.valueOf(LocalTime.now());
        //if(creationDate != null) {
            //System.out.println("creation date not null");
        //}
        Customer customer = service.getCustomer(customerEmail);
        cart = service.createCart(translateEnum(type), customer, creationDate, creationTime);
        return convertToDTO(cart);
    }

    @GetMapping(value = {"/cart", "/cart/"})
    public CartDTO getCart(@RequestParam(name = "customeremail")  String customerEmail, 
                            @RequestParam(name = "customerpassword") String customerPassword) throws IllegalStateException {
        Cart cart = retrieveOpenCart(customerEmail, customerPassword);
        if(cart == null) {
            throw new IllegalStateException("No cart currently opened");
        }
        return convertToDTO(cart);
    }

    @GetMapping(value = {"/cart/history", "/cart/history/"})
    public List<CartDTO> getHistoryCart(@RequestParam(name = "customeremail")  String customerEmail, 
                            @RequestParam(name = "customerpassword") String customerPassword) throws IllegalStateException {
        Customer customer = checkCustomer(customerEmail, customerPassword);
        List<Cart> carts = service.getCartsByCustomer(customer);
        List<Cart> history = new ArrayList<Cart>();
        for(Cart c : carts) {
            if(service.getOrderByCart(c) != null) {
				history.add(c);
            }
        }
        return convertCartListToDTO(history);
    }
    
    
    /**
     * Method partly related to Req.03 (1/3): As a user of the Grocery software system with a customer account, I 
     * would like to add items into a cart if they are online shopperable and checkout when I am ready for payment
     * Use to clear all items in the cart
     * URL: localhost:8080/carts/clear?customeremail=aEmail&customerpassword=aPassword
     * @param customerEmail Email of customer for identification
     * @param customerPassword Password of customer for identification
     * @throws IllegalStateException
     */
    @PostMapping(value = {"/carts/clear", "/carts/clear/"})
    public void clearCart(@RequestParam(name = "customeremail")  String customerEmail, 
                          @RequestParam(name = "customerpassword") String customerPassword) throws IllegalStateException {
        Cart cart = retrieveOpenCart(customerEmail, customerPassword);
        if(cart == null) {
            throw new IllegalStateException("Failed to clear the current cart: there is no opened cart to clear");
        }
        List<CartItem> items = service.getCartItemsByCart(cart);
        for(CartItem i : items) {
            service.setProductStock(i.getProduct().getProductName(), (i.getProduct().getStock() + i.getQuantity()));
            service.deleteCartItem(i);
        }
    }
    
    @PostMapping(value = {"/carts/delete", "/carts/delete/"})
    public void deleteCart(@RequestParam(name = "customeremail")  String customerEmail, 
            			   @RequestParam(name = "customerpassword") String customerPassword) throws IllegalStateException {
    	Cart cart = retrieveOpenCart(customerEmail, customerPassword);
    	if(cart == null) {
            throw new IllegalStateException("Failed to delete the current cart: there is no opened cart to clear");
        }
    	service.deleteCart(cart);
    }

    /**
     * This implements part of Req. 06 which relates to setting a desired time slot
     * The Grocery software system shall only allow delivery and pick-up time slots to be reserved by customers if there is one 
     * employee available to tend to the delivery or pick-up during that time slot
     * It is also related to Req.02-As a user of the Grocery software system with a customer account, I would like to schedule
     * a delivery or pick-up time among a variety of available time slots
     * URL: localhost:8080/carts/timeslot?customeremail=aEmail&customerpassword=aPassword&timeslotdate=yyyy-MM-dd&timeslotstarttime=HH:mm:ss&timeslotendtime=HH:mm:ss
     * @param customerEmail Email of customer for identification
     * @param customerPassword Password of customer for identification
     * @param timeslotdate for the date of the timeslot being chosen for this cart
     * @param timeslotstarttime for the start time of the timeslot being chosen for this cart
     * @param timeslotendtime for the end time of the timeslot being chosen for this cart
     * @return returns list of timeslots that are available
     * @throws IllegalArgumentException
     */
    @PostMapping(value = {"/carts/timeslot", "/carts/timeslot/"})
    public CartDTO setCartTimeSlot(@RequestParam(name = "customeremail") String customerEmail,
                                   @RequestParam(name = "customerpassword") String customerPassword,
                                   @RequestParam(name = "timeslotdate") @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date timeSlotDate,
                                   @RequestParam(name = "timeslotstarttime") @DateTimeFormat(pattern = "HH:mm:ss") java.util.Date timeSlotStartTime,
                                   @RequestParam(name = "timeslotendtime") @DateTimeFormat(pattern = "HH:mm:ss") java.util.Date timeSlotEndTime) 
                                   throws IllegalArgumentException, IllegalAccessException{
        Cart cart = retrieveOpenCart(customerEmail, customerPassword);
        if(cart == null) {
            throw new IllegalAccessException("No opened cart available under this customer!");
        }
        TimeSlot timeSlot = service.getTimeSlot(new Date(timeSlotDate.getTime()), new Time(timeSlotStartTime.getTime()), new Time(timeSlotEndTime.getTime()));
        if(timeSlot == null) {
            throw new IllegalArgumentException("No time slot found under the specified hours and date");
        }
        cart = service.setTimeSlot(cart, timeSlot);
        return convertToDTO(cart);
    }
    
    /**
     * This implements part of Req. 06 which relates to requesting to see all available timeslots
     * The Grocery software system shall only allow delivery and pick-up time slots to be reserved by customers if there is one 
     * employee available to tend to the delivery or pick-up during that time slot
     * It is also related to Req.02-As a user of the Grocery software system with a customer account, I would like to schedule
     * a delivery or pick-up time among a variety of available time slots
     * URL: localhost:8080/availabletimeslots
     * @return returns list of timeslots that are available
     * @throws IllegalArgumentException
     */
    @GetMapping(value = {"/availabletimeslots", "/availabletimeslots/"})
    public List<TimeSlotDTO> availableTimeSlots() throws IllegalArgumentException {
    	List<TimeSlot> tslot = service.getAllTimeSlots();
    	if(tslot == null) {
    		throw new IllegalArgumentException("No TimeSlot exists!");
    	}
    	List<Shift> shifts = service.getAllShifts();
    	if(shifts == null) {
    		throw new IllegalArgumentException("No one on shift!");
    	}
    	List<TimeSlot> availableTimeSlots = new ArrayList<>();
    	for(TimeSlot t: tslot) {
            if(t.getMaxOrderPerSlot() > 0) {
                availableTimeSlots.add(t);
            }
    	}
    	
    	return convertTimeSlotListToDTO(availableTimeSlots);
    }
    
    @GetMapping(value = {"/timeslots", "/timeslots/"})
    public List<TimeSlotDTO> getAllTimeSlots() throws IllegalArgumentException {
    	List<TimeSlot> tslot = service.getAllTimeSlots();
    	if(tslot == null) {
    		throw new IllegalArgumentException("No TimeSlot exists!");
    	}
    	List<Shift> shifts = service.getAllShifts();
    	if(shifts == null) {
    		throw new IllegalArgumentException("No one on shift!");
    	}
    	return convertTimeSlotListToDTO(tslot);
    }
    
    @PostMapping(value = {"/timeslot/delete", "/timeslot/delete/"})
    public TimeSlotDTO deleteTimeSlot(@RequestParam(name = "timeslotdate") @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date timeSlotDate,
                                @RequestParam(name = "timeslotstarttime") @DateTimeFormat(pattern = "HH:mm:ss") java.util.Date timeSlotStartTime,
                                @RequestParam(name = "timeslotendtime") @DateTimeFormat(pattern = "HH:mm:ss") java.util.Date timeSlotEndTime,
                                @RequestParam(name = "ownerEmail") String email,
                                @RequestParam(name = "ownerPassword") String password) throws IllegalArgumentException {
        CheckUser(email, password, "owner", "Only owner is able to delete a time slot"); 
        Time startTime = new Time(timeSlotStartTime.getTime());
        Time endTime = new Time(timeSlotEndTime.getTime());
        Date date = new Date(timeSlotDate.getTime());
        return (convertToDTO(service.deleteTimeSlot(startTime, endTime, date)));                         
    }

    @PostMapping(value = {"/timeslot", "/timeslot/"})
    public TimeSlotDTO createTimeSlot(@RequestParam(name = "timeslotdate") @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date timeSlotDate,
                                        @RequestParam(name = "timeslotstarttime") @DateTimeFormat(pattern = "HH:mm:ss") java.util.Date timeSlotStartTime,
                                        @RequestParam(name = "timeslotendtime") @DateTimeFormat(pattern = "HH:mm:ss") java.util.Date timeSlotEndTime,
                                        @RequestParam(name = "ownerEmail") String email,
    									@RequestParam(name = "ownerPassword") String password) throws IllegalArgumentException {
        CheckUser(email, password, "owner", "Only owner is able to create a time slot");
        Time startTime = new Time(timeSlotStartTime.getTime());
        Time endTime = new Time(timeSlotEndTime.getTime());
        Date date = new Date(timeSlotDate.getTime());
        int maxOrderPerSlot = service.getEmployeesForTimeSlot(date, startTime, endTime);
        TimeSlot t = service.createTimeSlot(startTime, endTime, date, maxOrderPerSlot);
        if(t == null) {
            throw new IllegalArgumentException("Time slot already exists!");
        }
        return convertToDTO(t);
    }

    /**
     * This implements Req. 09
     * The Grocery software system shall keep track of all the employees who work and have worked at the grocery store with employee accounts.
     * URL: localhost:8080/employees?ownerEmail=aEmail&ownerPassword=aPassword
     * @return returns list of empolyee, current and old
     * @throws IllegalArgumentException if userType is not owner
     */
    @GetMapping(value = {"/employees", "/employees/"})
    public List<EmployeeDTO> listEmployees(@RequestParam(name = "ownerEmail") String email,
    									   @RequestParam(name = "ownerPassword") String password) throws IllegalArgumentException{
    	CheckUser(email, password, "owner", "Only owner is able to list all employee");
        return convertEmployeeListToDTO(service.getAllEmployee());    		
    }
    
    /**
     * This implements Req. 10
     * The Grocery Store System shall allow the owner to remove or add employees on the employment list.
     * Deleting employ does not return anything. Just outputs string "Employee deleted" upon success.
     * URL: localhost:8080/employee?employeeEmail=aEmail&employeeName=aName&password=aPassword&status=aStatus&ownerEmail=aEmail&ownerPassword=aPassword
     * @param employeeEmail is the Email of the new employee being created
     * @param employeeName is the Name of new employee 
     * @param password is the Password of the new employee being created
     * @param status is the Status of the new employee being created (Sick, Inactive, Active)
     * @param ownerEmail is the Email of the Owner for identification
     * @param ownerPassword is the Password of the Owner for identification
     * @return returns the newly added employee
     * @throws IllegalArgumentException if userType is not owner
     */
    @PostMapping(value = {"/employee", "/employee/"})
    public EmployeeDTO owneraddEmployee(@RequestParam(name = "employeeEmail")     String email, 
							            @RequestParam(name = "employeeName")      String name, 
							            @RequestParam(name = "password")  String password,
							            @RequestParam(name = "status")     EmployeeStatusDTO status,
							            @RequestParam(name = "ownerEmail") String ownerEmail,
									    @RequestParam(name = "ownerPassword") String ownerPassword) throws IllegalArgumentException {
    	
    		CheckUser(ownerEmail, ownerPassword, "owner", "Only owner is able to add an employee");
    		Employee e = service.createEmployee(email, name, password, translateEnum(status));
            if(e == null) {
                throw new IllegalArgumentException("Employee with email " + email + " already exists");
            }
        	return convertToDTO(e);
    }
    @PostMapping(value = {"/removeemployee", "/removeemployee/"})
    public void ownerremoveEmployee(@RequestParam(name = "employeeEmail") String email,							            
						    		@RequestParam(name = "ownerEmail") String ownerEmail,
								    @RequestParam(name = "ownerPassword") String ownerPassword) 
                                    throws IllegalArgumentException {
    		CheckUser(ownerEmail, ownerPassword, "owner", "Only owner is ably to remove an employee");
    		Employee e = service.getEmployee(email);
    		service.removeEmployee(e);
    		System.out.println("Employee deleted");
    }
    
    @PostMapping(value = {"/employee/changeStatus", "/employee/changeStatus/"})
    public EmployeeDTO changeEmployeeStatus(@RequestParam(name = "employeeEmail") String email,							            
						    		@RequestParam(name = "ownerEmail") String ownerEmail,
								    @RequestParam(name = "ownerPassword") String ownerPassword,
    								@RequestParam(name = "status") EmployeeStatusDTO status)
                                    throws IllegalArgumentException {
    		CheckUser(ownerEmail, ownerPassword, "owner", "Only owner is able to change status of an employee");
    		System.out.println(email);
    		Employee e = service.getEmployee(email);
    		if (e == null) {
    			throw new IllegalArgumentException("Employee with email " + email + " do not exists");
    		}
    		return convertToDTO(service.setEmployeeStatus(e, translateEnum(status)));
    }

    /**
     * This implements Req. 11
     * The Grocery Store System shall allow the employee or customer to create a 
     * customer account with the customer email and physical address.
     * All this method does is check if it is the employee making the customer account. We assume when a customer is making
     * an account the userType will not be set
     * URL: localhost:8080/helpcustomer?email=aEmail&name=aName&password=aPassword&phone=aPhoneNumber&town=aTown&street=aStreet&postalcode=aPostalCode&unit=aUnitNumber&employeeEmail=aEmail&employeePassword=aPassword
     * @param email is the Email of the new customer being added
     * @param name is the Name of the new customer being added
     * @param password is the Password of the new customer being added
     * @param phone is the Phone number of the new customer being added
     * @param town is the Town of the address of new customer
     * @param street is the Street of the address of new customer
     * @param postalcode is the Postalcode of the address of new customer
     * @param unit is the Unit of the address of new customer
     * @param employeeEmail is the Email of the Employee adding the customer for identification
     * @param employeePassword is the Password of the Employee adding the customer for identification
     * @return returns the newly added customer
     * @throws IllegalArgumentException
     */
    @PostMapping(value = {"/helpcustomer", "/helpcustomer/"})
    public CustomerDTO helpCreateCustomer(@RequestParam(name = "email")     String email, 
                                      @RequestParam(name = "name")      String name, 
                                      @RequestParam(name = "password")  String password, 
                                      @RequestParam(name = "phone")     String phone, 
                                      @RequestParam(name = "town")      String town,
                                      @RequestParam(name = "street")      String street, 
                                      @RequestParam(name = "postalcode")  String postalcode,
                                      @RequestParam(name = "unit")     int unit,
                                      @RequestParam(name = "employeeEmail") String employeeEmail,
  								      @RequestParam(name = "employeePassword") String employeePassword) 
    throws IllegalArgumentException {
    	CheckUser(employeeEmail, employeePassword, "employee", "Only employee is able to help creat a customer account");
        Address a = service.getAddresseByUnitAndStreetAndTownAndPostalCode(unit, street, town, postalcode);
        if(a == null) {
            a = service.createAddresses(town, street, postalcode, unit);
        }
        Customer c = service.createCustomer(email, name, password, phone, a);
        return convertToDTO(c);
    }
    
    /**
     * This implements Req. 12
     * The Grocery Store System shall give the customer with a local address free shipping on online 
     * delivery orders and charge an extra fee for customers outside town limits
     * URL: localhost:8080/shipping?customeremail=aEmail&customerpassword=aPassword
     * @return returns true if free-shipping requirements are met, false otherwise.
     * @throws IllegalArgumentException
     */
    @GetMapping(value = {"/shipping", "/shipping/"})
    public boolean shippingFeeChecker(@RequestParam(name = "customeremail")  String customerEmail, 
                                      @RequestParam(name = "customerpassword") String customerPassword) throws IllegalArgumentException{
    	
        checkCustomer(customerEmail, customerPassword); // Will throw exception if email is not a customer
        return outOfTown(customerEmail);
    }
    ////helper method for testing things requiring customer
    @PostMapping(value = {"/testcustomer", "/testcustomer/"})
    public CustomerDTO createTestCustomer() {
    	Address custAddress = new Address("Montreal", "McgillStreet", "HHHH", 1234);
    	Customer c = service.createCustomer("bbbb", "Thomas", "789", "5149997777",custAddress);
        return convertToDTO(c);
    }
    
    /**
     * This is an implementation for Req.04
     * Req.04-As a user of the Grocery software system with a customer account, I would like to browse all products 
     * available in-stock and identify their availability for pick-up or delivery and their price
     * URL: localhost:8080/products?customerPage=aBoolean
     * @param customerPage boolean indicator if you are on customer perspective (true or false)
     * @return all product if user is owner or employee OR all product with stock greater than one if user is a customer
     * @throws IllegalArgumentException
     */
    @GetMapping(value = {"/products", "/products/"})
    public List<ProductDTO> getAllProduct(@RequestParam(name = "customerPage") Boolean customerPage) throws IllegalArgumentException {
    	
    	if (customerPage == false) { // provide all product if did not specify to view in customer's perspective
    		return convertProductListDTO(service.getAllProduct());
    	}
    	else { // else we provide all items that are in stock for better online shopping experience
    		return convertProductListDTO(service.getProductByStockGreaterThan(0));
    	}
    }
    
    
    /**
     * This is an partial implementation of Req.13(1/3), covering the "create" aspect of the Req.
     * Req.13-The Grocery Store System shall allow the owner to add or remove items from 
     * the store inventory and set the number of that item in stock
     * URL: localhost:8080/products?type=aType&productName=aProductName&online=aBoolean&price=aPrice&stock=aStockNumber&ownerEmail=aEmail&ownerPassword=aPassword
     * @param type is the Type of the new product being added
     * @param productName is the Product Name of the new product being added
     * @param  online is the indicator of availability online of the new product being added
     * @param price is the Price of the new product being added
     * @param stock is the Stock of the new product being added
     * @param ownerEmail is the Email of Owner for identification
     * @param ownerPassword is the Password of Owner for identification
     * @return the created product
     * @throws IllegalArgumentException
     */
    @PostMapping(value = {"/products", "/products/"})
    public ProductDTO createProduct(@RequestParam(name = "type")   PriceTypeDTO type,
                              @RequestParam(name = "productName")  String productName,
                              @RequestParam(name = "online")  String isAviliableOnline,
    						  @RequestParam(name = "price")  int price,
    					      @RequestParam(name = "stock")  int stock,
    					      @RequestParam(name = "ownerEmail") String ownerEmail,
							  @RequestParam(name = "ownerPassword") String ownerPassword) throws IllegalArgumentException{
    	CheckUser(ownerEmail, ownerPassword, "owner", "Only owner is able to create an item");
        Product p = service.createProduct(translateEnum(type), productName, isAviliableOnline, price, stock);
        if(p == null) {
            throw new IllegalStateException("Product with name " + productName + " already exists!");
        }
        return convertToDTO(p);
    }
    
    /**
     * This is an partial implementation of Req.13(2/3), Covering the "delete" aspect of the Req.
     * @param productName is the Name of the product that you want to delete
     * @param ownerEmail is the Email of Owner for identification
     * @param ownerPassword is the Password of Owner for identification
     * @return the deleted product
     * @throws IllegalArgumentException
     */
    @PostMapping(value = {"/products/delete", "/products/delete/"})
    public ProductDTO deleteProduct(@RequestParam(name = "productName") String productName,
					    		    @RequestParam(name = "ownerEmail") String ownerEmail,
								    @RequestParam(name = "ownerPassword") String ownerPassword) throws IllegalArgumentException, IllegalStateException{
    	CheckUser(ownerEmail, ownerPassword, "owner", "Only owner is able to delete an item");
    	Product p = service.deleteProduct(productName);
        return convertToDTO(p);
    }
    
    /**
     * This is an partial implementation of Req.13(3/3), Covering the "set stock" aspect of the Req.
     * localhost:8080/products/changestock?productName=aProductName&stock=aStockNumber&ownerEmail=aEmail&ownerPassword=aPassword
     * @param productName is the Name of the product you wish to change the stock of
     * @param stock  is the Stock number of the product you wish to change the stock of
     * @param ownerEmail is the Email of Owner for identification
     * @param ownerPassword is the Password of Owner for identification
     * @return the changed product
     * @throws IllegalArgumentException
     */
    @PostMapping(value = {"/products/changestock", "/products/changestock/"})
    public ProductDTO changeProductStock(@RequestParam(name = "productName") String productName,
    									 @RequestParam(name = "stock") int stock,
    									 @RequestParam(name = "ownerEmail") String ownerEmail,
    									 @RequestParam(name = "ownerPassword") String ownerPassword) throws IllegalArgumentException{
    	CheckUser(ownerEmail, ownerPassword, "owner", "Only owner is able to update an item stock");
    	if (stock < 0) {
    		throw new IllegalArgumentException("stock cannot be a negative value");
    	}
    	Product p = service.getProductByName(productName);
    	if (p == null) {
    		throw new IllegalArgumentException("the product do not exsist");
    	}
    	service.deleteProduct(productName);
    	Product newProduct = service.createProduct(p.getPriceType(), p.getProductName(), p.getIsAvailableOnline(), p.getPrice(),stock);
        return convertToDTO(newProduct);
    }
    
    /**
     * This is an implementation of the Req.15. The owner is able to set onlineAvailability upon the creation of the product, 
     * but if the owner wants to change the availability, he/she is able to do so via this method.
     * Req.15-The Grocery Store System shall allow the owner to choose what items are available for delivery and pickup online.
     * Note the isAviliableOnline is supposed to be a boolean but it is too risky to change this field for the entire project structure 
     * So it will be passed in as string by "yes" or "no".
     * localhost:8080/products/changeAvailability?productName=aProductName&isAviliableOnline=aBoolean&ownerEmail=aEmail&ownerPassword=aPassword
     * @param productName is the Name of the product you want to change the Availability of 
     * @param isAviliableOnline is the Status of the availability ("yes" or "no)
     * @param ownerEmail is the Email of Owner for identification
     * @param ownerPassword is the Password of Owner for identification
     * @return the changed product
     * @throws IllegalArgumentException
     */
    @PostMapping(value = {"/products/changeAvailability", "/products/changeAvailability/"})
    public ProductDTO changeProductAvailability(@RequestParam(name = "productName") String productName,
    									        @RequestParam(name = "isAviliableOnline") String isAviliableOnline,
    									        @RequestParam(name = "ownerEmail") String ownerEmail,
    										    @RequestParam(name = "ownerPassword") String ownerPassword) throws IllegalArgumentException{
    	CheckUser(ownerEmail, ownerPassword, "owner", "Only owner is able to change product aviliablity");
    	Product p = service.getProductByName(productName);
    	if (p == null) {
    		throw new IllegalArgumentException("the product do not exsist");
    	}
    	if (!("yes".equals(isAviliableOnline) || "no".equals(isAviliableOnline))) {
    		throw new IllegalArgumentException("field isAviliableOnline needs to be yes or no");
    	}
    	service.deleteProduct(productName);
    	Product newProduct = service.createProduct(p.getPriceType(), p.getProductName(), isAviliableOnline, p.getPrice(),p.getStock());
        return convertToDTO(newProduct);
    }
    
    /**
     * This is an implementation of Req.14.
     * Req.14-The Grocery Store System shall allow the owner to create a sales report containing all orders and their respective totals 
     * URL: localhost:8080/orders?ownerEmail=aEmail&ownerPassword=aPassword
     * @param ownerEmail is the Email of Owner for identification
     * @param ownerPassword is the Password of Owner for identification
     * @return all orders
     * @throws IllegalArgumentException
     */
    @GetMapping(value = {"/orders", "/orders"})
    public List<OrderDTO> getAllOrders( @RequestParam(name = "ownerEmail") String ownerEmail,
		    							@RequestParam(name = "ownerPassword") String ownerPassword) throws IllegalArgumentException{
    	CheckUser(ownerEmail, ownerPassword, "owner", "Only owner is able to generate sales report");
    	List<Order> list = service.getAllOrders();
    	if (list == null) {
    		throw new IllegalArgumentException("currently no orders in the system");
    	}
        return convertOrderListDTO(list);
    }
    
    @GetMapping(value = {"/orders/fulfill", "/orders/fulfill/"})
    public List<OrderDTO> getAllOrdersToFullfill( @RequestParam(name = "employeeEmail") String employeeEmail,
		    							@RequestParam(name = "employeePassword") String employeePassword) throws IllegalArgumentException{
    	CheckUser(employeeEmail, employeePassword, "employee", "Only employee is able to fullfill order");
    	List<Order> list = service.getAllIncompleteOrder();
    	if (list == null) {
    		throw new IllegalArgumentException("currently no orders in the system");
    	}
        return convertOrderListDTO(list);
    }
    
    @PostMapping(value = {"/orders/fulfill", "/orders/fulfill/"})
    public OrderDTO setOrderforFullfillment( @RequestParam(name = "employeeEmail") String employeeEmail,
		    							@RequestParam(name = "employeePassword") String employeePassword,
		    							@RequestParam(name = "creationDate") @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date creationDate,
                                        @RequestParam(name = "creationTime") @DateTimeFormat(pattern = "HH:mm:ss") java.util.Date creationTime) throws IllegalArgumentException{
    	CheckUser(employeeEmail, employeePassword, "employee", "Only employee is able to fullfill order");
    	List<Order> list = service.getAllIncompleteOrder();
    	if (list == null) {
    		throw new IllegalArgumentException("currently no orders in the system");
    	}
        Order toFulfill = null;
    	for (Order o: list) {
    		if (o.getCart().getCreationDate().compareTo(creationDate) == 0 && o.getCart().getCreationTime().compareTo(creationTime) == 0) {
                toFulfill = o;
                break;
            }
    	}
        service.setCompleted(toFulfill);
        return convertToDTO(toFulfill);
    }
    
    
    /**
     * This is an enhancement to Req.14, which generates the sales total for the owner.
     * Req.14-The Grocery Store System shall allow the owner to create a sales report containing all orders and their respective totals 
     * URL: localhost:8080/orders/total?ownerEmail=aEmail&ownerPassword=aPassword
     * @param ownerEmail is the Email of Owner for identification
     * @param ownerPassword is the Password of Owner for identification
     * @return online sales total
     * @throws IllegalArgumentException
     */
    @GetMapping(value = {"/orders/total", "/orders/total"})
    public int getAllOrdersTotal( @RequestParam(name = "ownerEmail") String ownerEmail,
		    					  @RequestParam(name = "ownerPassword") String ownerPassword) throws IllegalArgumentException{
    	CheckUser(ownerEmail, ownerPassword, "owner", "Only owner is ably to generate online sales total");
    	List<Order> list = service.getAllOrders();
    	if (list == null) {
    		return 0;
    	}
    	int sum = 0;
    	for (Order o: list) {
    		sum += o.getTotal();
    	}
    	return sum;
    }
    /**gets The total price of the cart that is currently open for the customer
     * URL: localhost:8080/cart/total?customeremail=aEmail&customerpassword=aPassword
     * @param customeremail is the Email of the Customer for identification
     * @param customerpassword is the Password of the Customer for identification
     */
    @GetMapping(value = {"/cart/total", "/cart/total/"})
    public int getCartTotal(@RequestParam(name = "customeremail") String customerEmail,
                            @RequestParam(name = "customerpassword") String customerPassword) throws IllegalArgumentException{
        Cart cart = retrieveOpenCart(customerEmail, customerPassword);
        if(cart == null) {
            throw new IllegalArgumentException("Failed to find a cart that is currently opened!");
        }
        int totalPrice = getCurrentTotal(cart);
        return totalPrice;     
    }

    /**
     * Method partly related to Req.03 (1/3): As a user of the Grocery software system with a customer account, I
     * would like to add items into a cart if they are online shopperable and checkout when I am ready for payment 
     * Use to pay the cart 
     * URL: localhost:8080/cart/pay?paymentcode=aPaymentCode&customeremail=aEmail&customerpassword=aPassword
     * @param paymentCode Payment code used to confirm the transaction
     * @param customerEmail Email of the customer for identification
     * @param customerPassword Password of the customer for identification
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    @PostMapping(value = {"/cart/pay", "/cart/pay/"})
    public OrderDTO payCart(@RequestParam(name = "paymentcode") String paymentCode, 
                            @RequestParam(name = "customeremail") String customerEmail,
                            @RequestParam(name = "customerpassword") String customerPassword) throws IllegalStateException, IllegalArgumentException{
        Cart cart = retrieveOpenCart(customerEmail, customerPassword);
        if(cart == null) {
            throw new IllegalArgumentException("Failed to find a cart that is currently opened!");
        }
        if(cart.getTimeSlot() == null) {
            throw new IllegalStateException("Cannot pay before having chosen a time slot for delivery or pick-up");
        }
        int totalPrice = getCurrentTotal(cart);
        Order o = service.createOrder(false, java.sql.Date.valueOf(LocalDate.now()), 
                            totalPrice, paymentCode, cart);     
        if(o == null) {
            throw new IllegalStateException("An order is already attached to this cart!");
        }
        return convertToDTO(o);
    }
    
    @GetMapping(value = {"/shifts", "/shifts/"})
    public List<ShiftDTO> getAllShift() throws IllegalArgumentException {
        return convertShiftListToDTO(service.getAllShifts());
    }
    
    /**
     * This is an implementation of the Req.01 
     * Req.01-"As a user of the Grocery software system with an employee account, I would like to be able to visualize my weekly work schedules."
     * URL: localhost:8080/shifts/myshifts?email=aEmail&password=aPassword
     * @param email is the Email of the employee for identification
     * @param password is the Password of the employee for identification
     * @return list of shifts belongs to the current user(employee).
     * @throws IllegalArgumentException
     */
    @GetMapping(value = {"/shifts/myshifts", "/shifts/myshifts/"})
    public List<ShiftDTO> getAllShiftbyEmployee( @RequestParam(name = "email") String email,
		    									@RequestParam(name = "password") String password) throws IllegalArgumentException{
    	UserDTO user = CheckUser(email, password, "employee", "Only employee is able to see their shift");
    	List<ShiftDTO> list = convertShiftListToDTO(service.getShiftByEmployee(convertToDomainObject((EmployeeDTO)user)));
        return list;
    }
    
    /**
     * Method related to Req.08-The Grocery software system shall allow the owner to schedule 
     * employees with active accounts to work within the daily opening hours of the store
     * Use to create a new shift and assign a specific employee to the shift
     * URL: localhost:8080/shifts?startHour=HH:mm:ss&endHour=HH:mm:ss&date=yyyy-MM-dd&employeeEmail=aEmail&ownerEmail=aEmail&ownerPassword=aPassword
     * @param startHour Starting time of the shift
     * @param endHour Ending time of the shift
     * @param date Date of the shift
     * @param email Email of the employee to which we assign the shift
     * @param ownerEmail Email of the owner for identification
     * @param ownerPassword Password of the owner for identification
     * @return Data transfer object of the shift 
     * @throws IllegalArgumentException When owner identification fails, cannot find employee in database or invalid hours
     */
    @PostMapping(value = {"/shifts", "/shifts/"})
    public ShiftDTO createShift(@RequestParam(name = "startHour") @DateTimeFormat(pattern = "HH:mm:ss") java.util.Date startHour,
                              @RequestParam(name = "endHour") @DateTimeFormat(pattern = "HH:mm:ss") java.util.Date endHour,
                              @RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date date,
                              @RequestParam(name = "employeeEmail")  String email, 
                              @RequestParam(name = "ownerEmail")  String ownerEmail,
                              @RequestParam(name = "ownerPassword")  String ownerPassword) 
                              throws IllegalArgumentException {
    	CheckUser(ownerEmail, ownerPassword, "owner", "Only owner is able to creatShift");
        Time startTime = new Time(startHour.getTime());
        Time endTime = new Time(endHour.getTime());
        Time storeOpening = service.getStore().getOpeningHour();
        Time storeClosing = service.getStore().getClosingHour();
        if(startTime.before(storeOpening) || startTime.after(storeClosing) || 
            endTime.before(storeOpening) || endTime.after(storeClosing)) {
            throw new IllegalArgumentException("Invalid start or end time for the shift");
        }
        Date shiftDate = new Date(date.getTime());
        Employee e = service.getEmployee(email);
        if(e == null) {
            throw new IllegalArgumentException("No employee with the email " + email);
        }
    	Shift shift = service.createShift(startTime, endTime, shiftDate, service.getEmployee(email));
        List<TimeSlot> timeSlotOverShift = service.getTimeSlotsBetween(shiftDate, startTime, endTime);
        if (timeSlotOverShift != null) {
            for(TimeSlot t : timeSlotOverShift) {
                service.incrementMaxOrderPerslot(t);
            }
        }
    	if (shift == null) {
    		throw new IllegalArgumentException("the shift with the same date for the employee already exsist.");
    	}
        return convertToDTO(shift);
    }
    
    @GetMapping(value = {"/address", "/address/"})
    public List<AddressDTO> getAllAddress() throws IllegalArgumentException {
    	List<Address> list = service.getAllAddresses();
    	if  (list == null) {
    		throw new IllegalArgumentException("There are currently no addresses in the system");
    	}
        return convertAddressListDTO(list);
    }
    
    @PostMapping(value = {"/address", "/address/"})
    public AddressDTO createAddress(@RequestParam(name = "town")     String town, 
                                      @RequestParam(name = "street")      String street, 
                                      @RequestParam(name = "postalcode")  String postalcode,
                                      @RequestParam(name = "unit")     int unit) 
    throws IllegalArgumentException {
    	Address a = service.createAddresses(town, street, postalcode, unit);
        return convertToDTO(a);
    }
    
    @PostMapping(value = {"/store", "/store/"})
    public StoreDTO createStore(@RequestParam(name = "telephone")     String telephone, 
                                      @RequestParam(name = "email")      String email, 
                                      @RequestParam(name = "openingHour")  @DateTimeFormat(pattern = "HH:mm:ss") java.util.Date startHour,
                                      @RequestParam(name = "closingHour")  @DateTimeFormat(pattern = "HH:mm:ss") java.util.Date endHour,
                                      @RequestParam(name = "town")     String town, 
                                      @RequestParam(name = "street")      String street, 
                                      @RequestParam(name = "postalcode")  String postalcode,
                                      @RequestParam(name = "unit")     int unit, 
                                      @RequestParam(name = "outoftownfee") int outOfTownFee,
                                      @RequestParam(name = "ownerEmail") String ownerEmail,
    								  @RequestParam(name = "ownerPassword") String ownerPassword) throws IllegalArgumentException{
    									  
    	CheckUser(ownerEmail, ownerPassword, "owner", "Only owner is able to create a store");
    	Address address = service.getAddresseByUnitAndStreetAndTownAndPostalCode(unit, street, town, postalcode);
        StoreOwner owner = service.getStoreOwner();
        if(owner == null) {
            throw new IllegalStateException("No store owner in the system, cannot create a store");
        }
        if(service.getStore() != null) {
            deleteStore(ownerEmail, ownerPassword);
        }
    	AddressDTO addressDto;
    	if (address == null) {
    		addressDto = createAddress(town, street, postalcode, unit);
    	} else {
    		addressDto = convertToDTO(address);
    	}
    	Store s = service.createStore(telephone, email, new Time(startHour.getTime()), new Time(endHour.getTime()),
    			                      owner, convertToDomainObject(addressDto), outOfTownFee);
    	if (s == null) {
    		throw new IllegalArgumentException("there is a store at the location already.");
    	}
        return convertToDTO(s);
    }
    /**
     * This is an implementation of the Req.07
     * Req.07-The Grocery software system shall allow the owner to modify the opening date and opening hours.
     * URL: localhost:8080/store/changeHours?openingHour=HH:mm:ss&closingHour=HH:mm:ss&ownerEmail=aEmail&ownerPassword=aPassword
     * @param openingHour is the new Opening Hour for the store
     * @param closingHour is the new Closing Hour for the store
     * @param ownerEmail Email of the owner for identification
     * @param ownerPassword Password of the owner for identification
     * @return the modified store
     * @throws IllegalArgumentException
     */
    @PostMapping(value = {"/store/changeHours", "/changeHours/"})
    public StoreDTO changeStoreHours(@RequestParam(name = "openingHour")  @DateTimeFormat(pattern = "HH:mm:ss") java.util.Date startHour,
                                      @RequestParam(name = "closingHour")  @DateTimeFormat(pattern = "HH:mm:ss") java.util.Date endHour,
    								  @RequestParam(name = "ownerEmail") String ownerEmail,
    								  @RequestParam(name = "ownerPassword") String ownerPassword) throws IllegalArgumentException{
    									  
    	CheckUser(ownerEmail, ownerPassword, "owner", "Only owner is able changeStore hour");
    	Store s = service.getStore();
    	if (s == null) {
    		throw new IllegalArgumentException("there is no store in the system yet");
    	}
    	service.deleteStore(s);
    	s.setOpeningHour(new Time(startHour.getTime()));
    	s.setClosingHour(new Time(endHour.getTime()));
    	service.createStore(s);
        return convertToDTO(s);
    }
    
    @PostMapping(value = {"/store/delete", "/store/delete/"})
    public StoreDTO deleteStore( @RequestParam(name = "ownerEmail") String ownerEmail,
			  					 @RequestParam(name = "ownerPassword") String ownerPassword) throws IllegalArgumentException{
    									  
    	CheckUser(ownerEmail, ownerPassword, "owner", "Only owner is able delete store");
    	Store s = service.getStore();
    	if (s == null) {
    		throw new IllegalArgumentException("there is no store in the system yet");
    	}
    	service.deleteStore(s);
        return convertToDTO(s);
    }
    
    @GetMapping(value = {"/store", "/store/"})
    public StoreDTO getStore() throws IllegalArgumentException {
    	Store store = service.getStore();
    	if  (store == null) {
    		throw new IllegalArgumentException("There are currently no any store in the system");
    	}
        return convertToDTO(store);
    }
    
    @PostMapping(value = {"/cart/item", "/cart/item/"})
    public CartItemDTO addItemToCart(@RequestParam(name = "useremail") String userEmail,
                                     @RequestParam(name = "userpassword") String userPassword,
                                     @RequestParam(name = "productname") String productName,
                                     @RequestParam(name = "quantity") Integer quantity) 
                                     throws IllegalAccessException, IllegalArgumentException {
        
        Cart cart = retrieveOpenCart(userEmail, userPassword);
        if(cart == null) {
            throw new IllegalArgumentException("Failed to find a cart that is currently opened!");
        }
        Product product = verifyProductAvailabilityAndShoppability(productName, quantity);
        List<CartItem> allItems = service.getCartItemsByCart(cart);
        boolean isSameItemInCart = false;
        CartItem c = null;
        int exsistingQuantity = 0;
        for(CartItem i : allItems) {
            if(i.getProduct().getProductName().equals(productName)) {
            	isSameItemInCart = true;
            	c = i;
            	exsistingQuantity = i.getQuantity();
            }
        }
        if (isSameItemInCart) {
        	service.setProductStock(productName, (product.getStock() - quantity));
        	return convertToDTO(service.setCartItemQuantity(c, quantity + exsistingQuantity));
        }
        else {
            CartItem item = service.createCartItem(quantity, product, cart);
            service.setProductStock(productName, (product.getStock() - quantity));
            return convertToDTO(item);
        }
    }
    /**
     * Creates a cart and add items specified. Used to make in store purchases.
     * @param user identification of employee and product name and quantity
     * @return returns instorepurchase object
     */
    @PostMapping(value = {"/instorepurchase", "/instorepurchase/"})
    public InStorePurchaseDTO createInStorePurchase(@RequestParam(name = "useremail") String userEmail,
                                                    @RequestParam(name = "userpassword") String userPassword,
                                                    @RequestParam(name = "productname") String productName,
                                                    @RequestParam(name = "quantity") Integer quantity) throws IllegalAccessException{
        checkEmployeeOrOwner(userEmail, userPassword);
        Product p = verifyProductAvailability(productName, quantity);
        Date purchaseDate = java.sql.Date.valueOf(LocalDate.now());
        InStorePurchase purchase = service.createInStorePurchase(quantity * p.getPrice(), purchaseDate);
        service.createCartItem(quantity, p, purchase);
        service.setProductStock(productName, (p.getStock() - quantity));
        return convertToDTO(purchase);
    }
    /**
     * Make a list of all the in store purchases
     * @return returns the list of all the in store purchases
     */
    @GetMapping(value = {"/instorepurchases", "/instorepurchases/"})
    public List<InStorePurchaseDTO> getAllInStorePurchases() {
        List<InStorePurchase> localList = service.getAllInStorePurchases();
        List<InStorePurchaseDTO> localListDTO = new ArrayList<InStorePurchaseDTO>();
        for (InStorePurchase isp: localList) {
            localListDTO.add(new InStorePurchaseDTO(isp.getTotal(), isp.getPurchaseDate(), convertCartItemListDTO(service.getCartItemsByInStorePurchase(isp))));
        }
        return localListDTO;
    }

    /* Helper methods ---------------------------------------------------------------------------------------------------- */
    
    public UserDTO CheckUser (String email, String password, String targetUserType, String errorMsg) throws IllegalArgumentException {
    	
    	if (service.getUser(email) == null) {
    		throw new IllegalArgumentException("the user with the email does not exsist");
    	}
    	
    	switch (targetUserType.toLowerCase()) {
		case "owner":
		case "storeowner":
			StoreOwner so = service.getStoreOwner();
			if (so != null && !so.getEmail().equals(email)) {
				throw new IllegalArgumentException(errorMsg);
			}
			StoreOwnerDTO soDto =convertToDTO(so);
			if (so.getPassword().equals(password)) {
				return soDto;
			}
			else {
				throw new IllegalArgumentException("password do not match");
			}
			
		case "customer":
			Customer c = service.getCustomer(email);
			if (c == null) {
				throw new IllegalArgumentException(errorMsg);
			}
			CustomerDTO cDto =convertToDTO(c);
			if (cDto.getPassword().equals(password)) {
				return cDto;
			}
			else {
				throw new IllegalArgumentException("password do not match");
			}
			
		case "employee":
			Employee e = service.getEmployee(email);
			if (e == null) {
				throw new IllegalArgumentException(errorMsg);
			}
			EmployeeDTO eDto =convertToDTO(e);
			if (eDto.getPassword().equals(password)) {
				return eDto;
			}
			else {
				throw new IllegalArgumentException("password do not match");
			}
			
		default:
			throw new IllegalArgumentException("target user type needs to be employee, owner, or customer");
		}
    }
    
    private List<CustomerDTO> convertCustomerListToDTO(List<Customer> customers) throws IllegalArgumentException{
        List<CustomerDTO> list = new ArrayList<CustomerDTO>();
        for(Customer c : customers) {
            list.add(convertToDTO(c));
        }
        return list;
    }
    
    private List<EmployeeDTO> convertEmployeeListToDTO(List<Employee> employees) throws IllegalArgumentException{
        List<EmployeeDTO> list = new ArrayList<EmployeeDTO>();
        for(Employee e : employees) {
            list.add(convertToDTO(e));
        }
        return list;
    }

    private List<ShiftDTO> convertShiftListToDTO(List<Shift> shifts) throws IllegalArgumentException{
        List<ShiftDTO> list = new ArrayList<ShiftDTO>();
        for(Shift s : shifts) {
            list.add(convertToDTO(s));
        }
        return list;
    }
    
    private List<ProductDTO> convertProductListDTO(List<Product> products) throws IllegalArgumentException{
        List<ProductDTO> list = new ArrayList<ProductDTO>();
        for(Product p : products) {
            list.add(convertToDTO(p));
        }
        return list;
    }
    
    private List<OrderDTO> convertOrderListDTO(List<Order> orders) throws IllegalArgumentException{
        List<OrderDTO> list = new ArrayList<OrderDTO>();
        for(Order o : orders) {
            list.add(convertToDTO(o));
        }
        return list;
    }
    
    private List<AddressDTO> convertAddressListDTO(List<Address> address) throws IllegalArgumentException{
        List<AddressDTO> list = new ArrayList<AddressDTO>();
        for(Address a : address) {
            list.add(convertToDTO(a));
        }
        return list;
    }
    
    private List<InStorePurchaseDTO> convertInStorePurchaseListToDTO(List<InStorePurchase> purchases) throws IllegalArgumentException{
        List<InStorePurchaseDTO> list = new ArrayList<InStorePurchaseDTO>();
        for(InStorePurchase isb : purchases) {
            list.add(convertToDTO(isb));
        }
        return list;
    }
    
    private CustomerDTO convertToDTO(Customer customer) {
        if(customer == null) throw new IllegalArgumentException("Customer does not exist");
        AddressDTO address = convertToDTO(customer.getAddress());
        CustomerDTO c = new CustomerDTO(customer.getEmail(), customer.getName(), customer.getPassword(), 
                                        customer.getPhone(), address);
        c.setCarts(createCartList(customer));
        return c;
    }

    private UserDTO convertToDTO(User user) {
        if(user == null) throw new IllegalArgumentException("Customer does not exist");
        UserDTO c = new UserDTO(user.getEmail(), user.getName(), user.getPassword());
        return c;
    }

    private List<CartDTO> createCartList(Customer customer) {
        List<Cart> list = service.getCartsByCustomer(customer);
        List<CartDTO> result = convertCartListToDTO(list);
        return result;
    }
    
    private StoreOwnerDTO convertToDTO(StoreOwner storeOwner) {
        if(storeOwner == null) throw new IllegalArgumentException("Store Owner does not exist");
        StoreOwnerDTO so = new StoreOwnerDTO(storeOwner.getEmail(), storeOwner.getName(), storeOwner.getPassword());
        return so;
    }
    
    private EmployeeDTO convertToDTO(Employee employee) {
        if(employee == null) throw new IllegalArgumentException("Employee does not exist");
        List<Shift> l = service.getShiftByEmployee(employee);
        EmployeeDTO e;
        if (l != null) {
            List<ShiftDTO> list = new ArrayList<ShiftDTO>();
            for (Shift shift: l) {
            	list.add(new ShiftDTO(shift.getStartHour(), shift.getEndHour(), shift.getDate(), new EmployeeDTO(employee.getEmail(), employee.getName(), employee.getPassword(), translateEnum(employee.getStatus()))));
            }
        	e = new EmployeeDTO(employee.getEmail(), employee.getName(), employee.getPassword(), translateEnum(employee.getStatus()), list);
        }
        else {
        	e = new EmployeeDTO(employee.getEmail(), employee.getName(), employee.getPassword(), translateEnum(employee.getStatus()));
        }
        return e;
    }

    private AddressDTO convertToDTO(Address address) {
        if(address == null) throw new IllegalArgumentException("Address does not exist");
        AddressDTO a = new AddressDTO(address.getTown(), address.getStreet(), address.getPostalCode(), address.getUnit());
        return a;
    }
    
    private ShiftDTO convertToDTO(Shift shift) {
        if(shift == null) throw new IllegalArgumentException("shift does not exist");
        ShiftDTO s = new ShiftDTO(shift.getStartHour(), shift.getEndHour(), shift.getDate(), convertToDTO(shift.getEmployee()));
        return s;
    }
    
    private OrderDTO convertToDTO(Order order) {
        if(order == null) throw new IllegalArgumentException("order does not exist");
        OrderDTO o = new OrderDTO(order.getCompleted(), order.getOrderDate(), order.getTotal(), order.getPayment(), convertToDTO(order.getCart()));
        return o;
    }
    
    
    private StoreDTO convertToDTO(Store store) {
        if(store == null) throw new IllegalArgumentException("store does not exist");
        StoreDTO s = new StoreDTO(store.getTelephone(), store.getEmail(), store.getOpeningHour(), 
        		                  store.getClosingHour(), convertToDTO(store.getStoreOwner()), convertToDTO(store.getAddress()),
                                  store.getOutOfTownFee());
        return s;
    }
    
    private InStorePurchaseDTO convertToDTO(InStorePurchase purchase) {
        if(purchase == null) throw new IllegalArgumentException("store does not exist");
        InStorePurchaseDTO isb = new InStorePurchaseDTO(purchase.getTotal(), purchase.getPurchaseDate());
        return isb;
    }
    

    private Address convertToDomainObject(AddressDTO address) {
        List<Address> allAddresses = service.getAllAddresses();
        for( Address a : allAddresses ) {
            if( (address.getPostalCode().equals(a.getPostalCode())) && (address.getStreet().equals(a.getStreet())) && 
                (address.getTown().equals(a.getTown())) && (address.getUnit() == a.getUnit())) {
                    return a;
            }
        }
        return null;
    }
    
    private Employee convertToDomainObject(EmployeeDTO employee) {
        List<Employee> Employees = service.getAllEmployee();
        for( Employee e : Employees ) {
            if(e.getEmail().equals(employee.getEmail())) {
                    return e;
            }
        }
        return null;
    }

    private List<CartDTO> convertCartListToDTO(List<Cart> carts) throws IllegalArgumentException{
        List<CartDTO> list = new ArrayList<CartDTO>();
        for(Cart c : carts) {
            list.add(convertToDTO(c));
        }
        return list;
    }

    private List<CartItemDTO> convertCartItemListDTO(List<CartItem> cartItems) throws IllegalArgumentException{
        List<CartItemDTO> list = new ArrayList<CartItemDTO>();
        for(CartItem c : cartItems) {
            list.add(convertToDTO(c));
        }
        return list;
    }

    private CartDTO convertToDTO(Cart cart) {
        if(cart == null) throw new IllegalArgumentException("Cart does not exist");
        TimeSlotDTO timeSlot = convertToDTO(cart.getTimeSlot());
        CartDTO.ShoppingTypeDTO type = translateEnum(cart.getType());
        if(type == null) throw new IllegalArgumentException("Invalid shopping type for cart");
        CartDTO c = new CartDTO(type, cart.getCreationDate(), cart.getCreationTime(), timeSlot);
        c.setCartItems(createCartItemsList(cart));
        return c;
    }

    private List<TimeSlotDTO> convertTimeSlotListToDTO(List<TimeSlot> timeSlots) {
    	List<TimeSlotDTO> list = new ArrayList<TimeSlotDTO>();
        for(TimeSlot t : timeSlots) {
            list.add(convertToDTO(t));
        }
        return list;
    }
    
    private TimeSlotDTO convertToDTO(TimeSlot timeSlot) {
        if(timeSlot == null) return null;
        TimeSlotDTO t = new TimeSlotDTO(timeSlot.getDate(), timeSlot.getStartTime(), 
                                        timeSlot.getEndTime(), timeSlot.getMaxOrderPerSlot());
        return t;
    }

    private List<CartItemDTO> createCartItemsList(Cart cart) {
        List<CartItemDTO> list = new ArrayList<CartItemDTO>();
        List<CartItem> items = service.getCartItemsByCart(cart);
        for( CartItem i : items ) {
            list.add(convertToDTO(i));
        }
        return list;
    }

    private CartItemDTO convertToDTO(CartItem cartItem) {
        if(cartItem == null) throw new IllegalArgumentException("Cart Item does not exist");
        ProductDTO product = convertToDTO(cartItem.getProduct());
        CartItemDTO i = new CartItemDTO(cartItem.getQuantity(), product);
        return i;
    }

    private ProductDTO convertToDTO(Product product) {
        if(product == null) throw new IllegalArgumentException("Product does not exist");
        ProductDTO.PriceTypeDTO priceType = translateEnum(product.getPriceType());
        if(priceType == null) throw new IllegalArgumentException("Invalid price type for the product");
        ProductDTO p = new ProductDTO(priceType, product.getProductName(), product.getIsAvailableOnline(), 
                                        product.getStock(), product.getPrice());
        return p;
    }

    private ProductDTO.PriceTypeDTO translateEnum(Product.PriceType priceType) {
        switch(priceType) {
            case PER_KILOS:
                return PriceTypeDTO.PER_KILOS;
            case PER_UNIT:
                return PriceTypeDTO.PER_UNIT;
            default:
                return null;
        }
    }

    private Product.PriceType translateEnum(ProductDTO.PriceTypeDTO priceType) {
        switch(priceType) {
            case PER_KILOS:
                return PriceType.PER_KILOS;
            case PER_UNIT:
                return PriceType.PER_UNIT;
            default:
                return null;
        }
    }

    private CartDTO.ShoppingTypeDTO translateEnum(Cart.ShoppingType type) {
        switch(type) {
            case Delivery:
                return ShoppingTypeDTO.Delivery;
            case Pickup:
                return ShoppingTypeDTO.Pickup;
            default:
                return null;
        }
    }

    private Cart.ShoppingType translateEnum(CartDTO.ShoppingTypeDTO type) {
        switch(type) {
            case Delivery:
                return ShoppingType.Delivery;
            case Pickup:
                return ShoppingType.Pickup;
            default:
                return null;
        }
    }
    
    private EmployeeDTO.EmployeeStatusDTO translateEnum(Employee.EmployeeStatus status) {
        switch(status) {
	        case Sick:
	            return EmployeeStatusDTO.Sick;
	        case Inactive:
	            return EmployeeStatusDTO.Inactive;
	        case Active:
	            return EmployeeStatusDTO.Active;
	        default:
	            return null;
        }
    }
    
    private Employee.EmployeeStatus translateEnum(EmployeeDTO.EmployeeStatusDTO type) {
        switch(type) {
            case Sick:
                return EmployeeStatus.Sick;
            case Inactive:
                return EmployeeStatus.Inactive;
            case Active:
                return EmployeeStatus.Active;
            default:
                return null;
        }
    }
    /**
     * Retrieve cart if one exists for user
     * @param userEmail user name to be checked
     * @param userPassword user password to be checked
     * @return returns the currently open cart of the user
     */
    private Cart retrieveOpenCart(String customerEmail, String customerPassword) throws IllegalArgumentException {
        Customer customer = checkCustomer(customerEmail, customerPassword);
        List<Cart> carts= service.getCartsByCustomer(customer);
        Cart cart = null;
        for(Cart c : carts) {
            if(service.getOrderByCart(c) == null) {
                cart = c;
                break;
            }
        }
        return cart;
    }
    /**
     * Check if user is customer
     * @param userEmail user name to be checked
     * @param userPassword user password to be checked
     * @return On success returns user object
     */
    private Customer checkCustomer(String customerEmail, String customerPassword) throws IllegalArgumentException {
        Customer customer = service.getCustomer(customerEmail);
        if(customer == null) {
            throw new IllegalArgumentException("Must be logged in as a customer!");
        }
        if(!customer.getPassword().equals(customerPassword)) {
            throw new IllegalArgumentException("Failed identification: password is incorrect");
        }
        return customer;
    }
    /**
     * Check if user is owner
     * @param userEmail user name to be checked
     * @param userPassword user password to be checked
     * @return On success returns user object
     */
    private StoreOwner checkOwner(String ownerEmail, String ownerPassword) {
        StoreOwner owner = service.getStoreOwner();
        if(!owner.getEmail().equals(ownerEmail) || !owner.getPassword().equals(ownerPassword)) {
            throw new IllegalArgumentException("Cannot create an in-store purchase! You need to have employee or store owner clearance");
        }
        return owner;
    }
    /**
     * Check if user is owner or employee 
     * @param userEmail user name to be checked
     * @param userPassword user password to be checked
     * @return On success return user object
     */
    private User checkEmployeeOrOwner(String userEmail, String userPassword) throws IllegalArgumentException {
        User u = service.getEmployee(userEmail);
        if(u == null) {
            u = service.getStoreOwner();
            if(!u.getEmail().equals(userEmail)) {
                throw new IllegalArgumentException("Cannot create an in-store purchase! You need to have employee or store owner clearance");
            }
        }
        if(!u.getPassword().equals(userPassword)) {
            throw new IllegalArgumentException("Failed identification: password is incorrect");
        }
        return u;
    }
    /**
     * Check if item of specified amount exists
     * @param productName is the name of product in string 
     * @param quanitity is the integer value of the number of items to be checked for availability 
     * @return retruns product
     */
    private Product verifyProductAvailability(String productName, int quantity) throws IllegalArgumentException {
        Product p = service.getProductByName(productName);
        if(p == null) {
            throw new IllegalArgumentException("Failed to find the product with name " + productName + " in the database");
        }
        if(quantity < 1) {
            throw new IllegalArgumentException("Invalid quantity: quantity must be bigger or equal to 1, received " + quantity);
        }
        if(quantity > p.getStock()) {
            throw new IllegalArgumentException("Invalid quantity: cannot purchase more than the available stock");
        }
        return p;
    }
    /**
     * Checks if the given item of specified quantity is available to shop online.
     * @param productName is the name of product in string 
     * @param quanitity is the integer value of the number of items to be checked for availability 
     * @return on success returns product 
     */
    private Product verifyProductAvailabilityAndShoppability(String productName, int quantity) throws IllegalArgumentException {
        Product p = verifyProductAvailability(productName, quantity);
        if(p.getIsAvailableOnline().equals("no")) {
            throw new IllegalArgumentException("Invalid product: this product is not available for online purchases");
        }
        return p;
    }
    /**
     * Calculated the total value of all the items in the customers cart
     * @return total price calculated
     */
    private int getCurrentTotal(Cart cart) {
        int totalPrice = 0;
        List<CartItem> itemList = service.getCartItemsByCart(cart);
        for(CartItem i : itemList) {
            totalPrice += i.getQuantity() * i.getProduct().getPrice();
        }
        if(outOfTown(cart.getCustomer().getEmail())){
            totalPrice += service.getStore().getOutOfTownFee();
        }
        return totalPrice;
    }
    /**
     * Compares Customer town equals to the stores town and returns true if customer is in same town or false otherwise.
     * @return if user is out of town or not as a boolean
     */
    private boolean outOfTown(String customerEmail) {
        boolean outOfTown = true;
        Customer customer = service.getCustomer(customerEmail);
        Address customeraddress = customer.getAddress();
        if(customeraddress.getTown().equals(service.getStore().getAddress().getTown())) {
        	outOfTown = false;
        }
        return outOfTown;
    }
}
