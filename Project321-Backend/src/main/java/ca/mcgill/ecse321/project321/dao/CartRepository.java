package ca.mcgill.ecse321.project321.dao;

import ca.mcgill.ecse321.project321.model.Cart;
import ca.mcgill.ecse321.project321.model.Customer;
import ca.mcgill.ecse321.project321.model.Order;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Integer>{
    List<Cart> findByCustomer(Customer customer);
    Cart findByCustomerAndOrder(Customer customer, Order order);
    
}