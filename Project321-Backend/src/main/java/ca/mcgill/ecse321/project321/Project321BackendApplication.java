package ca.mcgill.ecse321.project321;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.project321.dto.CartDTO;
import ca.mcgill.ecse321.project321.dto.InStorePurchaseDTO;
import ca.mcgill.ecse321.project321.dto.StoreDTO;
import ca.mcgill.ecse321.project321.dto.StoreOwnerDTO;
import ca.mcgill.ecse321.project321.dto.UserDTO;
import ca.mcgill.ecse321.project321.model.Store;
import ca.mcgill.ecse321.project321.controller.GroceryStoreController;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@SpringBootApplication
public class Project321BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(Project321BackendApplication.class, args);
	}

	//Request Map
	@RequestMapping("/")
  	public String greeting(){
			return "Hello world!";
  	}
}
