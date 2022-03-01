package ca.mcgill.ecse321.project321.dto;

public class AddressDTO {
    private String  town;
    private String  street;
    private String  postalCode;
    private Integer unit;

    public AddressDTO() {}

    public AddressDTO(String town, String street, String postalCode, Integer unit) {
        this.town = town;
        this.street = street;
        this.postalCode = postalCode;
        this.unit = unit;
    }

    public String getTown() {
        return this.town;
    }

    public String getStreet() {
        return this.street;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public Integer getUnit() {
        return this.unit;
    }
}
