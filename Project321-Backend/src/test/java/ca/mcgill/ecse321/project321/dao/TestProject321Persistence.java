package ca.mcgill.ecse321.project321.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
		// Fisrt, we clear registrations to avoid exceptions due to inconsistencies
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
    void contextLoads() {
	}

}