package ca.mcgill.ecse321.project321.dao;

import ca.mcgill.ecse321.project321.model.InStoreBill;

import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface InStoreBillRepository extends CrudRepository<InStoreBill, Integer>{
    List<InStoreBill> findByPurchaseDate(Date purchaseDate);
}
