package ca.mcgill.ecse321.project321.dao;

import ca.mcgill.ecse321.project321.model.InStorePurchase;

import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface InStorePurchaseRepository extends CrudRepository<InStorePurchase, Integer>{
    List<InStorePurchase> findByPurchaseDate(Date purchaseDate);
}
