package ca.mcgill.ecse321.project321.dto;

public class ProductDTO {

    public enum PriceTypeDTO{ 
        PER_UNIT, 
        PER_KILOS
    }

    private PriceTypeDTO priceType;
    private String productName;
    private String isAvailableOnline;
    private Integer stock;
    private Integer price;

    public ProductDTO() {}

    public ProductDTO(ProductDTO.PriceTypeDTO priceType, String productName, String isAvailableOnline, 
                        Integer stock, Integer price) {
        this.priceType = priceType;
        this.productName = productName;
        this.isAvailableOnline = isAvailableOnline;
        this.stock = stock;
        this.price = price;
    }
    
}
