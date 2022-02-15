package ca.mcgill.ecse321.project321.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.project321.model.Cart;
import ca.mcgill.ecse321.project321.model.Order;
import ca.mcgill.ecse321.project321.model.TimeSlot;


public interface CartRepository extends CrudRepository<Cart, String> {
    List<Cart> findByTimeSlot(TimeSlot timeSlot);
    Cart findByOrder(Order order);
}
