package ca.mcgill.ecse321.project321.dao;

import ca.mcgill.ecse321.project321.model.Cart;
import ca.mcgill.ecse321.project321.model.Order;

import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer>{
    List<Order> findByOrderDate(Date orderDate);
    List<Order> findByCompletedFalse();
    Order findByCart(Cart cart);
}
