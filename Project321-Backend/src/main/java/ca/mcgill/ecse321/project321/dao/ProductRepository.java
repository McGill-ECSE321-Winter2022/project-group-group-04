package ca.mcgill.ecse321.project321.dao;

import ca.mcgill.ecse321.project321.model.Product;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface ProductRepository extends CrudRepository<Product, String>{
    Product findByProductName(String name);
    List<Product> findByStockGreaterThan(int limit);
}
