package ca.mcgill.ecse321.project321.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.project321.model.Cart;
import ca.mcgill.ecse321.project321.model.Order;


public interface OrderRepository extends CrudRepository<Order, String> {
    Order findByCart(Cart cart);
}
