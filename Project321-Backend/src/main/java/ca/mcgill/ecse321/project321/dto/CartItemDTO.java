package ca.mcgill.ecse321.project321.dto;

public class CartItemDTO {
    private Integer         quantity;
    private ProductDTO      product;

    public CartItemDTO() {}

    public CartItemDTO(Integer quantity, ProductDTO product) {
        this.quantity = quantity;
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ProductDTO getProduct() {
        return product;
    }
}
