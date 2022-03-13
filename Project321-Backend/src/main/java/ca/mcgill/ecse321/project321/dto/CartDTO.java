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
    private TimeSlotDTO         timeSlot;
    private List<CartItemDTO>   cartItems;
    private Date                creationDate;
    private Time                creationTime;   

    public CartDTO() {}

    @SuppressWarnings("unchecked")
    public CartDTO(ShoppingTypeDTO type, Date creationDate, Time creationTime) {
        this(type, creationDate, creationTime, null, Collections.EMPTY_LIST);
    }

    @SuppressWarnings("unchecked")
    public CartDTO(ShoppingTypeDTO type, Date creationDate, Time creationTime, TimeSlotDTO timeSlot) {
        this(type, creationDate, creationTime, timeSlot, Collections.EMPTY_LIST);
    }

    public CartDTO(ShoppingTypeDTO type, Date creationDate, Time creationTime, List<CartItemDTO> cartItems) {
        this(type, creationDate, creationTime, null, cartItems);
    }

    public CartDTO(ShoppingTypeDTO type, Date creationDate, Time creationTime, TimeSlotDTO timeSlot, List<CartItemDTO> cartItems) {
        this.type = type;
        this.timeSlot = timeSlot;
        this.cartItems = new ArrayList<CartItemDTO>(cartItems);
        this.creationDate = creationDate;
        this.creationTime = creationTime;
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

    public Date getCreationDate() {
        return this.creationDate;
    }

    public Time getCreationTime() {
        return this.creationTime;
    }

    public void setCartItems(List<CartItemDTO> cartItems) {
        this.cartItems = new ArrayList<CartItemDTO>(cartItems);
    }
}
