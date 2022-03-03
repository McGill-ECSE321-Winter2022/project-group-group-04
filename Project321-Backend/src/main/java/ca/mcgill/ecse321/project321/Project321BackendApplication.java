package ca.mcgill.ecse321.project321;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.project321.model.User;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@SpringBootApplication
public class Project321BackendApplication {
//private User currentUser;
	public static void main(String[] args) {
		SpringApplication.run(Project321BackendApplication.class, args);
	}

	//Request Map
	@RequestMapping("/")
  	public String greeting(){
    	return "Hello world!";
  	}
	
	//public void setCurrentUser(User user) {
		//this.currentUser = user;
	//}

}
