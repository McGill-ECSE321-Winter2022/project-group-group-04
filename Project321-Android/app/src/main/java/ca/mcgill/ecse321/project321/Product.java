package ca.mcgill.ecse321.project321;

public class Product {
    private String productName;
    private String isAvailableOnline;
    private String priceType;
    private String Price;

    public Product(String productName, String isAvailableOnline, String priceType, String price) {
        this.productName = productName;
        this.isAvailableOnline = isAvailableOnline;
        this.priceType = priceType;
        Price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getIsAvailableOnline() {
        return isAvailableOnline;
    }

    public void setIsAvailableOnline(String isAvailableOnline) {
        this.isAvailableOnline = isAvailableOnline;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
