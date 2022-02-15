package ca.mcgill.ecse321.project321.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.project321.model.Product;


public interface ProductRepository extends CrudRepository<Product, String> {
    Product findByProductID(String productID);
}
