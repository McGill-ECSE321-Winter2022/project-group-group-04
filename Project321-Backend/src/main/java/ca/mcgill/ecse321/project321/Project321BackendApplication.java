package ca.mcgill.ecse321.project321;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.project321.dto.UserDTO;
import ca.mcgill.ecse321.project321.model.User;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@SpringBootApplication
public class Project321BackendApplication {
private static UserDTO currentUser;
private static String userType;
	public static void main(String[] args) {
		SpringApplication.run(Project321BackendApplication.class, args);
	}

	//Request Map
	@RequestMapping("/")
  	public String greeting(){
		if (currentUser != null) {
	    	return "Hello world! Current user email is " + currentUser.getEmail() + ". And the user type is " + userType;
		}
    	return "Hello world!";
  	}
	
	public static void setCurrentUser(UserDTO user) {
		currentUser = user;
	}
	
	public static void setUserType(String UserType) {
		userType = UserType;
	}
	
	public static UserDTO getCurrentUser() {
		return currentUser;
	}
	
	public static String getUserType() {
		return userType;
	}

}
