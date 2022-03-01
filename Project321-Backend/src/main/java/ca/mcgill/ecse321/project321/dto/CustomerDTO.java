package ca.mcgill.ecse321.project321.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerDTO extends UserDTO{
    private String          phone;
    private AddressDTO      address;
    private List<CartDTO>   carts;

    public CustomerDTO() {
        super();
    }

    @SuppressWarnings("unchecked")
    public CustomerDTO(String email, String name, String password, String phone, AddressDTO address) {
        this(email, name, password, phone, address, Collections.EMPTY_LIST);
    }

    public CustomerDTO(String email, String name, String password, String phone, AddressDTO address, List<CartDTO> carts) {
        super(email, name, password);
        this.phone = phone;
        this.address = address;
        this.carts = new ArrayList<CartDTO>(carts);
    }

    public String getPhone() {
        return this.phone;
    }

    public AddressDTO getAddress() {
        return this.address;
    }

    public List<CartDTO> getCarts() {
        return this.carts;
    }

    public void setCarts(List<CartDTO> carts) {
        this.carts = new ArrayList<CartDTO>(carts);
    }
}
