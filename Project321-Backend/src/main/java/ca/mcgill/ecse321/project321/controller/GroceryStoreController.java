package ca.mcgill.ecse321.project321.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.project321.Project321BackendApplication;
import ca.mcgill.ecse321.project321.dao.CartItemRepository;
import ca.mcgill.ecse321.project321.dto.AddressDTO;
import ca.mcgill.ecse321.project321.dto.CartDTO;
import ca.mcgill.ecse321.project321.dto.CartItemDTO;
import ca.mcgill.ecse321.project321.dto.CustomerDTO;
import ca.mcgill.ecse321.project321.dto.EmployeeDTO;
import ca.mcgill.ecse321.project321.dto.EmployeeDTO.EmployeeStatusDTO;
import ca.mcgill.ecse321.project321.dto.ProductDTO;
import ca.mcgill.ecse321.project321.dto.TimeSlotDTO;
import ca.mcgill.ecse321.project321.dto.UserDTO;
import ca.mcgill.ecse321.project321.dto.CartDTO.ShoppingTypeDTO;
import ca.mcgill.ecse321.project321.dto.ProductDTO.PriceTypeDTO;
import ca.mcgill.ecse321.project321.dto.ShiftDTO;
import ca.mcgill.ecse321.project321.dto.StoreOwnerDTO;
import ca.mcgill.ecse321.project321.model.Address;
import ca.mcgill.ecse321.project321.model.Cart;
import ca.mcgill.ecse321.project321.model.CartItem;
import ca.mcgill.ecse321.project321.model.Customer;
import ca.mcgill.ecse321.project321.model.Employee;
import ca.mcgill.ecse321.project321.model.Employee.EmployeeStatus;
import ca.mcgill.ecse321.project321.model.Product;
import ca.mcgill.ecse321.project321.model.TimeSlot;
import ca.mcgill.ecse321.project321.model.Cart.ShoppingType;
import ca.mcgill.ecse321.project321.model.Product.PriceType;
import ca.mcgill.ecse321.project321.model.Shift;
import ca.mcgill.ecse321.project321.model.StoreOwner;
import ca.mcgill.ecse321.project321.service.GroceryStoreService;

@CrossOrigin(origins = "*")
@RestController
public class GroceryStoreController {
    
    @Autowired
    private GroceryStoreService service;

    @GetMapping(value = {"/customers", "/customers/"})
    public List<CustomerDTO> getAllCustomers() throws IllegalArgumentException{
        return convertCustomerListToDTO(service.getAllCustomers());
    }

    @GetMapping(value = {"/customers/{email}", "/customers/{email}/"})
    public CustomerDTO getCustomer(@PathVariable("email") String email) throws IllegalArgumentException{
        return convertToDTO(service.getCustomer(email));
    }

    @PostMapping(value = {"/customers", "/customers/"})
    public CustomerDTO createCustomer(@RequestParam(name = "email")     String email, 
                                      @RequestParam(name = "name")      String name, 
                                      @RequestParam(name = "password")  String password, 
                                      @RequestParam(name = "phone")     String phone, 
                                      @RequestParam(name = "address")   AddressDTO address) 
    throws IllegalArgumentException {
        Customer c = service.createCustomer(email, name, password, phone, convertToDomainObject(address));
        //System.out.println(c.getName());
        return convertToDTO(c);
    }
    
    @GetMapping(value = {"/storeOwners/{email}", "/storeOwners/{email}/"})
    public StoreOwnerDTO getStoreOwnerDTO(@PathVariable("email") String email) throws IllegalArgumentException{
        return convertToDTO(service.getStoreOwner(email));
    }
    
    @PostMapping(value = {"/storeOwners", "/storeOwners/"})
    public StoreOwnerDTO createStoreOwner(@RequestParam(name = "email")     String email, 
                                      @RequestParam(name = "name")      String name, 
                                      @RequestParam(name = "password")  String password) 
    throws IllegalArgumentException {
        StoreOwner c = service.createStoreOwner(email, name, password);
        System.out.println(c.getName());
        return convertToDTO(c);
    }

    @GetMapping(value = {"/employee/{email}", "/employee/{email}/"})
    public EmployeeDTO getEmployee(@PathVariable("email") String email) throws IllegalArgumentException{
        return convertToDTO(service.getEmployee(email));
    }
    
    @PostMapping(value = {"/employee", "/employee/"})
    public EmployeeDTO createEmployee(@RequestParam(name = "email")     String email, 
                                      @RequestParam(name = "name")      String name, 
                                      @RequestParam(name = "password")  String password,
                                      @RequestParam(name = "status")     EmployeeStatusDTO status) 
    throws IllegalArgumentException {
        Employee c = service.createEmployee(email, name, password, translateEnum(status));
        return convertToDTO(c);
    }
    
    @PostMapping(value = {"/login", "/login/"})
    public UserDTO login(@RequestParam(name = "email")     String email, 
                         @RequestParam(name = "password")  String password, 
                         @RequestParam(name = "userType")  String userType)
    throws IllegalArgumentException {
    	switch (userType.toLowerCase()) {
		case "owner":
		case "storeowner":
			StoreOwnerDTO so =convertToDTO(service.getStoreOwner(email));
			if (so != null) {
				if (so.getPassword().equals(password)) {
					Project321BackendApplication.setCurrentUser(so);
					Project321BackendApplication.setUserType("owner");
					return so;
				}
				else {
					throw new IllegalArgumentException("password do not match");
				}
			}
			break;
			
		case "customer":
			CustomerDTO c =convertToDTO(service.getCustomer(email));
			if (c != null) {
				if (c.getPassword().equals(password)) {
					Project321BackendApplication.setCurrentUser(c);
					Project321BackendApplication.setUserType("customer");
					return c;
				}
				else {
					throw new IllegalArgumentException("password do not match");
				}
			}
			break;
			
		case "employee":
			EmployeeDTO e =convertToDTO(service.getEmployee(email));
			if (e != null) {
				if (e.getPassword().equals(password)) {
					Project321BackendApplication.setCurrentUser(e);
					Project321BackendApplication.setUserType("employee");
					return e;
				}
				else {
					throw new IllegalArgumentException("password do not match");
				}
			}
			break;
		default:
			break;
		}
    	return null;
    }
    
    @GetMapping(value = {"/carts", "/carts/"})
    public List<CartDTO> getAllCarts() throws IllegalArgumentException {
        return convertCartListToDTO(service.getAllCarts());
    }

    @PostMapping(value = {"/carts", "/carts/"})
    public CartDTO createCart(@RequestParam(name = "type")      ShoppingTypeDTO type,
                              @RequestParam(name = "customer")  CustomerDTO customer,
                              @RequestParam(name = "timeSlot")  TimeSlotDTO timeSlot) {
        Cart c = service.createCart(translateEnum(type), convertToDomainObject(customer));
        c = service.setTimeSlot(c, convertToDomainObject(timeSlot));
        return convertToDTO(c);
    }
    
    /**
     * This is an implementation for Req.04
     * Req.04-As a user of the Grocery software system with a customer account, I would like to browse all products 
     * available in-stock and identify their availability for pick-up or delivery and their price
     * @return all product if user is owner or employee OR all product with stock greater than one if user is a customer
     * @throws IllegalArgumentException
     */
    @GetMapping(value = {"/products", "/products/"})
    public List<ProductDTO> getAllProduct() throws IllegalArgumentException {
    	if ("customer".equals(Project321BackendApplication.getUserType())) {
    		return convertProductListDTO(service.getProductByStockGreaterThan(0));
    	}
        return convertProductListDTO(service.getAllProduct());
    }
    
    @PostMapping(value = {"/products", "/products/"})
    public ProductDTO createProduct(@RequestParam(name = "type")   PriceTypeDTO type,
                              @RequestParam(name = "productName")  String productName,
                              @RequestParam(name = "Online")  String isAviliableOnline,
    						  @RequestParam(name = "price")  int price,
    					      @RequestParam(name = "stock")  int stock){
    	Product p = service.createProduct(translateEnum(type), productName, isAviliableOnline, price, stock);
        return convertToDTO(p);
    }
    
    @GetMapping(value = {"/shifts", "/shifts/"})
    public List<ShiftDTO> getAllShift() throws IllegalArgumentException {
        return convertShiftListToDTO(service.getAllShifts());
    }
    
    /**
     * This is an implementation of the Req.01 
     * Req.01-"As a user of the Grocery software system with an employee account, I would like to be able to visualize my weekly work schedules."
     * @return list of shifts belongs to the current user(employee).
     * @throws IllegalArgumentException
     */
    @GetMapping(value = {"/shifts/myshifts", "/shifts/myshifts/"})
    public List<ShiftDTO> getAllShiftbyEmployee() throws IllegalArgumentException {
    	if (!Project321BackendApplication.getUserType().equals("employee")) {
    		throw new IllegalArgumentException("you do not have an employee account loged in. Unable to retrive shifts.");
    	}
    	List<ShiftDTO> list = convertShiftListToDTO(service.getShiftByEmployee(convertToDomainObject((EmployeeDTO)Project321BackendApplication.getCurrentUser())));
        return list;
    }
    
    @PostMapping(value = {"/shifts", "/shifts/"})
    public ShiftDTO createShift(@RequestParam(name = "startHour") @DateTimeFormat(pattern = "HH:mm:ss") java.util.Date startHour,
                              @RequestParam(name = "endHour") @DateTimeFormat(pattern = "HH:mm:ss") java.util.Date endHour,
                              @RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date date,
                              @RequestParam(name = "employeeEmail")  String email) 
                              throws IllegalArgumentException {
    	if (!Project321BackendApplication.getUserType().equals("owner")) {
    		throw new IllegalArgumentException("only owner is able to creat a shift.");
    	}
    	Shift shift = service.createShift(new Time(startHour.getTime()), new Time(endHour.getTime()), new java.sql.Date(date.getTime()), service.getEmployee(email));
    	if (shift == null) {
    		throw new IllegalArgumentException("the shift with the same date for the employee already exsist.");
    	}
        return convertToDTO(shift);
    }

    /* Helper methods ---------------------------------------------------------------------------------------------------- */
    private List<CustomerDTO> convertCustomerListToDTO(List<Customer> customers) throws IllegalArgumentException{
        List<CustomerDTO> list = new ArrayList<CustomerDTO>();
        for(Customer c : customers) {
            list.add(convertToDTO(c));
        }
        return null;
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
    
    
    private CustomerDTO convertToDTO(Customer customer) {
        if(customer == null) throw new IllegalArgumentException("Customer does not exist");
        AddressDTO address = convertToDTO(customer.getAddress());
        CustomerDTO c = new CustomerDTO(customer.getEmail(), customer.getName(), customer.getPassword(), 
                                        customer.getPhone(), address);
        return c;
    }
    
    private StoreOwnerDTO convertToDTO(StoreOwner storeOwner) {
        if(storeOwner == null) throw new IllegalArgumentException("Store Owner does not exist");
        StoreOwnerDTO so = new StoreOwnerDTO(storeOwner.getEmail(), storeOwner.getName(), storeOwner.getPassword());
        return so;
    }
    
    private EmployeeDTO convertToDTO(Employee employee) {
        if(employee == null) throw new IllegalArgumentException("Employee does not exist");
        EmployeeDTO e = new EmployeeDTO(employee.getEmail(), employee.getName(), employee.getPassword(), translateEnum(employee.getStatus()));
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
        return null;
    }

    private CartDTO convertToDTO(Cart cart) {
        if(cart == null) throw new IllegalArgumentException("Cart does not exist");
        TimeSlotDTO timeSlot = convertToDTO(cart.getTimeSlot());
        CustomerDTO customer = convertToDTO(cart.getCustomer());
        CartDTO.ShoppingTypeDTO type = translateEnum(cart.getType());
        if(type == null) throw new IllegalArgumentException("Invalid shopping type for cart");
        CartDTO c = new CartDTO(type, customer, timeSlot);
        c.setCartItems(createCartItemsList(cart, c));
        return c;
    }

    private TimeSlotDTO convertToDTO(TimeSlot timeSlot) {
        if(timeSlot == null) throw new IllegalArgumentException("TimeSlot does not exist");
        TimeSlotDTO t = new TimeSlotDTO(timeSlot.getDate(), timeSlot.getStartTime(), 
                                        timeSlot.getEndTime(), timeSlot.getMaxOrderPerSlot());
        return t;
    }

    private List<CartItemDTO> createCartItemsList(Cart cart, CartDTO cartDTO) {
        List<CartItemDTO> list = new ArrayList<CartItemDTO>();
        List<CartItem> items = service.getCartItemsByCart(cart);
        for( CartItem i : items ) {
            list.add(convertToDTO(i, cartDTO));
        }
        return list;
    }

    private CartItemDTO convertToDTO(CartItem cartItem, CartDTO cart) {
        if(cartItem == null) throw new IllegalArgumentException("Cart Item does not exist");
        ProductDTO product = convertToDTO(cartItem.getProduct());
        CartItemDTO i = new CartItemDTO(cart, cartItem.getQuantity(), product);
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

    private Customer convertToDomainObject(CustomerDTO customer) {
        List<Customer> allCustomers = service.getAllCustomers();
        for( Customer c : allCustomers ) {
            if( customer.getEmail().equals(c.getEmail()) ) {
                    return c;
            }
        }
        return null;
    }

    private TimeSlot convertToDomainObject(TimeSlotDTO timeSlot) {
        List<TimeSlot> allTimeSlots = service.getAllTimeSlots();
        for( TimeSlot t : allTimeSlots ){
            if(timeSlot.getDate().equals(t.getDate()) && timeSlot.getStartTime().equals(t.getStartTime()) &&
                timeSlot.getEndTime().equals(t.getEndTime())) {
                return t;
            }
        }
        return null;
    }
}