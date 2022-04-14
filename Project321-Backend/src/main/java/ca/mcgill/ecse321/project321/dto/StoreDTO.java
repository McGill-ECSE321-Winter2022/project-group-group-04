package ca.mcgill.ecse321.project321.dto;

import java.sql.Time;


public class StoreDTO {
	  private String telephone;
	  private String email;
	  private Time openingHour;
	  private Time closingHour;
	  private StoreOwnerDTO storeOwner;
	  private AddressDTO address;
	  private int outOfTownFee;
	  
	  public StoreDTO() {}
	  
	  public StoreDTO(String telephone, String email, Time openingHour, Time closingHour, StoreOwnerDTO storeOwner, AddressDTO address, int outOfTownFee) {
		  this.telephone = telephone;
		  this.email = email;
		  this.openingHour = openingHour;
		  this.closingHour = closingHour;
		  this.storeOwner = storeOwner;
		  this.address = address;
		  this.outOfTownFee = outOfTownFee;
	  }
	  
	  public String getTelephone() {
		return telephone;
	  }
	  
	  public String getEmail() {
		return email;
	  }
	  
	  public Time getOpeningHour() {
		return openingHour;
	  }
	  
	  public Time getClosingHour() {
		return closingHour;
	  }
	  
	  public StoreOwnerDTO getStoreOwner() {
		return storeOwner;
  	  }
	  
	  public AddressDTO getAddress() {
		return address;
	  }

	  public int getOutOfTownFee() {
		  return outOfTownFee;
	  }
	  
}
