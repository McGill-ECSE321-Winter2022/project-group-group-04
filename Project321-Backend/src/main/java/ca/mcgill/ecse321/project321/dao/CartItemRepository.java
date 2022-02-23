package ca.mcgill.ecse321.project321.dao;

import ca.mcgill.ecse321.project321.model.Cart;
import ca.mcgill.ecse321.project321.model.CartItem;
import ca.mcgill.ecse321.project321.model.InStoreBill;
import ca.mcgill.ecse321.project321.model.Product;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CartItemRepository extends CrudRepository<CartItem, Integer>{
    List<CartItem> findByCart(Cart cart);
    List<CartItem> findByInStoreBill(InStoreBill inStoreBill);
    CartItem findByCartAndProduct(Cart cart, Product product);
    CartItem findByInStoreBillAndProduct(InStoreBill inStoreBill, Product product);
    List<CartItem> findByProductAndQuantity(Product product, int quantity);
}
