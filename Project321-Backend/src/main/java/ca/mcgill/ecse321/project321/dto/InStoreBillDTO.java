package ca.mcgill.ecse321.project321.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InStoreBillDTO {
    private int total;
    private Date purchaseDate;
    private String paymentCode;
    private List<CartItemDTO> cartItems;
    
    public InStoreBillDTO() {}
    
    public InStoreBillDTO(int total, Date purchaseDate, String paymentCode, List<CartItemDTO> cartItems) {
    	this.total = total;
    	this.purchaseDate = purchaseDate;
    	this.cartItems = new ArrayList<CartItemDTO>(cartItems);
    }
    
    @SuppressWarnings("unchecked")
    public InStoreBillDTO(int total, Date purchaseDate, String paymentCode) {
        this(total, purchaseDate, paymentCode, Collections.EMPTY_LIST);
    }
    
    public int getTotal() {
		return this.total;
	}
    
    public Date getPurchaseDate() {
		return this.purchaseDate;
	}

    public String getPaymentCode() {
        return this.paymentCode;
    }
    
    public List<CartItemDTO> getCartItems() {
		return this.cartItems;
	}
    
    public void setCartItems(List<CartItemDTO> cartItems) {
        this.cartItems = new ArrayList<CartItemDTO>(cartItems);
    }

}
