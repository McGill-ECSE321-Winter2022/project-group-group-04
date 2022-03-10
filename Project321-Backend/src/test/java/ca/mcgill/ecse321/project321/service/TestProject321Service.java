package ca.mcgill.ecse321.project321.service;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
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
import ca.mcgill.ecse321.project321.dao.InStorePurchaseRepository ;
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

@ExtendWith(MockitoExtension.class)
public class TestProject321Service {

}

