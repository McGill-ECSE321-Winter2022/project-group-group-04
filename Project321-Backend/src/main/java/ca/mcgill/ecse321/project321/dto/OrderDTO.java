package ca.mcgill.ecse321.project321.dto;

import java.sql.Date;

public class OrderDTO {
	  private boolean completed;
	  private Date orderDate;
	  private int total;
	  private String payment;
	  private CartDTO cart;
	  
	  public OrderDTO() {}
	  
	  public OrderDTO(boolean completed, Date orderDate, int total, String payment, CartDTO cart) {
		  this.completed = completed;
		  this.orderDate = orderDate;
		  this.total = total;
		  this.payment = payment;
		  this.cart = cart;
	  }
	  
	  public boolean getCompleted() {
		  return this.completed;
	  }
	   
	  public Date getOrderDate() {
		return this.orderDate;
	  }
	  
	  public int getTotal() {
		return this.total;
	  }
	  
	  public String getPayment() {
		return this.payment;
	  }
	  
	  public CartDTO getCart() {
		return this.cart;
	  }
}
