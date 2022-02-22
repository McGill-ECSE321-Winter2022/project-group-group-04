package ca.mcgill.ecse321.project321.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.project321.model.InStoreBill;

public interface InStoreBillRepository extends CrudRepository<InStoreBill, String> {
    List<InStoreBill> findByPurchaseDate(Date purchaseDate);
}
