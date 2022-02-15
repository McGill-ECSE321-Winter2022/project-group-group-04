package ca.mcgill.ecse321.project321.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.project321.model.User;

public interface UserRepository extends CrudRepository<User, String> {
    User findByEmailAddress(String emailAddress);
}
