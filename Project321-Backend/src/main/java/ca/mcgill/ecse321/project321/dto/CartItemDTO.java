package ca.mcgill.ecse321.project321.dto;

public class CartItemDTO {
    private CartDTO cart;
    private Integer quantity;
    private ProductDTO product;

    public CartItemDTO() {}

    public CartItemDTO(CartDTO cart, Integer quantity, ProductDTO product) {
        this.cart = cart;
        this.quantity = quantity;
        this.product = product;
    }
}
