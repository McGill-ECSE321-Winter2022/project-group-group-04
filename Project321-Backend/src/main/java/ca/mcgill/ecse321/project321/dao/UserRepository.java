package ca.mcgill.ecse321.project321.dao;

import ca.mcgill.ecse321.project321.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String>{
    User findByEmail(String email);
}
