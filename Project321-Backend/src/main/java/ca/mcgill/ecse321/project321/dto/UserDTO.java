package ca.mcgill.ecse321.project321.dto;

public class UserDTO {
    private String email;
    private String name;
    private String password;

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
}
