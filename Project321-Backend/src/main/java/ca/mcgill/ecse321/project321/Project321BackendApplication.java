package ca.mcgill.ecse321.project321;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.project321.dto.CartDTO;
import ca.mcgill.ecse321.project321.dto.StoreDTO;
import ca.mcgill.ecse321.project321.dto.StoreOwnerDTO;
import ca.mcgill.ecse321.project321.dto.UserDTO;
import ca.mcgill.ecse321.project321.model.User;
import ca.mcgill.ecse321.project321.controller.GroceryStoreController;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@SpringBootApplication
public class Project321BackendApplication {
	private static UserDTO currentUser;
	private static String userType;
	private static StoreDTO store;
	private static CartDTO cart;
    private GroceryStoreController controller;

	public static void main(String[] args) {
		SpringApplication.run(Project321BackendApplication.class, args);
	}

	//Request Map
	@RequestMapping("/")
  	public String greeting(){
		store = controller.getStore();
		if(store == null) {
			StoreOwnerDTO storeOwner = controller.getStoreOwner();
			if(storeOwner == null) {
				storeOwner = controller.createStoreOwner("admin@email.com", "admin", "password");
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date startTime= new Date() , endTime = new Date();
			try{
				startTime = sdf.parse("0000/00/00 09:00:00");
				endTime = sdf.parse("0000/00/00 17:00:00");
			} catch(Exception e){
				// Do nothing
			}
			store = controller.createStore("514-111-1111", "store@mail.com", startTime, endTime,"town", "street", "XXXXXX", 0, 0);
			cart = null;
		}
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

}
