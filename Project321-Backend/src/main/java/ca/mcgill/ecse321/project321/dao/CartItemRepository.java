package ca.mcgill.ecse321.project321.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.project321.model.CartItem;
import ca.mcgill.ecse321.project321.model.Product;


public interface CartItemRepository extends CrudRepository<CartItem, String> {
    List<CartItem> findByProductAndQuantity(Product product, int quantity);
}
