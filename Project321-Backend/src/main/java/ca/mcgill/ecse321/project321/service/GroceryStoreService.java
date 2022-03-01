package ca.mcgill.ecse321.project321.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}