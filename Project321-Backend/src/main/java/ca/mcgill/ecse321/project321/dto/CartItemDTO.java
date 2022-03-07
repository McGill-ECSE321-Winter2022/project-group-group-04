package ca.mcgill.ecse321.project321.dto;

public class CartItemDTO {
    private CartDTO         cart;
    private InStoreBillDTO  bill;
    private Integer         quantity;
    private ProductDTO      product;

    public CartItemDTO() {}

    public CartItemDTO(CartDTO cart, Integer quantity, ProductDTO product) {
        this.cart = cart;
        this.bill = null;
        this.quantity = quantity;
        this.product = product;
    }

    public CartItemDTO(InStoreBillDTO bill, Integer quantity, ProductDTO product) {
        this.cart = null;
        this.bill = bill;
        this.quantity = quantity;
        this.product = product;
    }

    public CartDTO getCart() {
        return cart;
    }

    public InStoreBillDTO getInStoreBill() {
        return bill;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ProductDTO getProduct() {
        return product;
    }
}
