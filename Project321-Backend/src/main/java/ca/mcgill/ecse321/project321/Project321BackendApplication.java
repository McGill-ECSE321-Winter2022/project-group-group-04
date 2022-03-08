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
	private static UserDTO 			currentUser;
	private static String 			userType;
	private static StoreDTO 		store;
	private static CartDTO 			cart;
	private static InStorePurchaseDTO 	purchase;

	public static void main(String[] args) {
		SpringApplication.run(Project321BackendApplication.class, args);
	}

	//Request Map
	@RequestMapping("/")
  	public String greeting(){
		cart = null;
		purchase = null;

		if (currentUser != null) {
	    	return "Hello world! Current user email is " + currentUser.getEmail() + ". And the user type is " + userType;
		} else {
			return "Hello world!";
		}
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

	public static StoreDTO getStore() {
		return store;
	}

	public static CartDTO getCart() {
		return cart;
	}

	public static void setCart(CartDTO aCart) {
		cart = aCart;
	}

	public static InStorePurchaseDTO getPurchase() {
		return purchase;
	}

	public static void setPurchase(InStorePurchaseDTO aPurchase) {
		purchase = aPurchase;
	}
}
