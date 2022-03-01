package ca.mcgill.ecse321.project321.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartDTO {

    public enum ShoppingTypeDTO {
        Delivery,
        Pickup
    }

    private ShoppingTypeDTO     type;
    private CustomerDTO         customer;
    private TimeSlotDTO         timeSlot;
    private List<CartItemDTO>   cartItems;

    public CartDTO() {}

    @SuppressWarnings("unchecked")
    public CartDTO(ShoppingTypeDTO type, CustomerDTO customer) {
        this(type, customer, null, Collections.EMPTY_LIST);
    }

    @SuppressWarnings("unchecked")
    public CartDTO(ShoppingTypeDTO type, CustomerDTO customer, TimeSlotDTO timeSlot) {
        this(type, customer, timeSlot, Collections.EMPTY_LIST);
    }

    public CartDTO(ShoppingTypeDTO type, CustomerDTO customer, List<CartItemDTO> cartItems) {
        this(type, customer, null, cartItems);
    }

    public CartDTO(ShoppingTypeDTO type, CustomerDTO customer, TimeSlotDTO timeSlot, List<CartItemDTO> cartItems) {
        this.type = type;
        this.customer = customer;
        this.timeSlot = timeSlot;
        this.cartItems = new ArrayList<CartItemDTO>(cartItems);
    }

    public CustomerDTO getCustomer() {
        return this.customer;
    }

    public TimeSlotDTO getTimeSlot() {
        return this.timeSlot;
    }

    public ShoppingTypeDTO getShoppingType() {
        return this.type;
    }

    public List<CartItemDTO> getCartItems() {
        return this.cartItems;
    }

    public void setCartItems(List<CartItemDTO> cartItems) {
        this.cartItems = new ArrayList<CartItemDTO>(cartItems);
    }
}
