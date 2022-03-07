package ca.mcgill.ecse321.project321.dto;

import java.sql.Date;
import java.sql.Time;
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
    private OrderDTO            order;
    private Date                creationDate;
    private Time                creationTime;   

    public CartDTO() {}

    @SuppressWarnings("unchecked")
    public CartDTO(ShoppingTypeDTO type, CustomerDTO customer, Date creationDate, Time creationTime) {
        this(type, customer, creationDate, creationTime, null, Collections.EMPTY_LIST);
    }

    @SuppressWarnings("unchecked")
    public CartDTO(ShoppingTypeDTO type, CustomerDTO customer, Date creationDate, Time creationTime, TimeSlotDTO timeSlot) {
        this(type, customer, creationDate, creationTime, timeSlot, Collections.EMPTY_LIST);
    }

    public CartDTO(ShoppingTypeDTO type, CustomerDTO customer, Date creationDate, Time creationTime, List<CartItemDTO> cartItems) {
        this(type, customer, creationDate, creationTime, null, cartItems);
    }

    public CartDTO(ShoppingTypeDTO type, CustomerDTO customer, Date creationDate, Time creationTime, TimeSlotDTO timeSlot, List<CartItemDTO> cartItems) {
        this.type = type;
        this.customer = customer;
        this.timeSlot = timeSlot;
        this.cartItems = new ArrayList<CartItemDTO>(cartItems);
        this.order = null;
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

    public OrderDTO getOrder() {
        return order;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public Time getCreationTime() {
        return this.creationTime;
    }

    public void setCartItems(List<CartItemDTO> cartItems) {
        this.cartItems = new ArrayList<CartItemDTO>(cartItems);
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }
}
