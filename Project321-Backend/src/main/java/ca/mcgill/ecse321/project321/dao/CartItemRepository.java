package ca.mcgill.ecse321.project321.dao;

import ca.mcgill.ecse321.project321.model.Cart;
import ca.mcgill.ecse321.project321.model.CartItem;
import ca.mcgill.ecse321.project321.model.InStorePurchase;
import ca.mcgill.ecse321.project321.model.Product;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CartItemRepository extends CrudRepository<CartItem, Integer>{
    List<CartItem> findByCart(Cart cart);
    List<CartItem> findByInStorePurchase(InStorePurchase inStorePurchase);
    CartItem findByCartAndProduct(Cart cart, Product product);
    CartItem findByInStorePurchaseAndProduct(InStorePurchase inStorePurchase, Product product);
    List<CartItem> findByProductAndQuantity(Product product, int quantity);
}
