package ca.mcgill.ecse321.project321.dto;

public class CartItemDTO {
    private CartDTO         cart;
    private InStorePurchaseDTO  purchase;
    private Integer         quantity;
    private ProductDTO      product;

    public CartItemDTO() {}

    public CartItemDTO(CartDTO cart, Integer quantity, ProductDTO product) {
        this.cart = cart;
        this.purchase = null;
        this.quantity = quantity;
        this.product = product;
    }

    public CartItemDTO(InStorePurchaseDTO purchase, Integer quantity, ProductDTO product) {
        this.cart = null;
        this.purchase = purchase;
        this.quantity = quantity;
        this.product = product;
    }

    public CartDTO getCart() {
        return cart;
    }

    public InStorePurchaseDTO getInStorePurchase() {
        return purchase;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ProductDTO getProduct() {
        return product;
    }
}
