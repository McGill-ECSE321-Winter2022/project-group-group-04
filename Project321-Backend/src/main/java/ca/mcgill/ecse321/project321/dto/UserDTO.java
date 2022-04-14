package ca.mcgill.ecse321.project321.dto;

public class UserDTO {
    private String email;
    private String name;
    private String password;
    private String type;

    public UserDTO() {}

    public UserDTO(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
