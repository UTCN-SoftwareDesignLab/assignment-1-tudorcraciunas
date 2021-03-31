package model.DTO;

import model.Role;
import model.User;
import model.builder.UserBuilder;

public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String role;

    public UserDTO(Long id, String username, String password, String role){
        this.id = id;
        this.password = password;
        this.username = username;
        this.role = role;
    }

    public UserDTO(String username, String password){
        this.password = password;
        this.username = username;
    }
    public UserDTO(String username, String password, String role){
        this.password = password;
        this.username = username;
        this.role = role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }


}
