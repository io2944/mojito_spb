package cryorbiter.mojito_spb.dto;

import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;
    private String email;
    private List<RoleDto> roles;

    public UserDto() {
    }

    public UserDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }   
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }

    public void addRole(RoleDto role) {
        if (this.roles == null) {
            this.roles = new java.util.ArrayList<>();
        }
        if (this.roles.contains(role)) {
            return;
        }

        this.roles.add(role);
    }

}
