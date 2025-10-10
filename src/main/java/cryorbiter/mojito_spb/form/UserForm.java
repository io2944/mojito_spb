package cryorbiter.mojito_spb.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Setter;

public class UserForm {

    @Setter
    private Long id;

    @Setter
    @NotNull
    @Size(min = 2, max = 25)
    private String username;

    @NotNull
    @Size(min = 2, max = 25)
    private String password;

    @NotNull
    @Size(min = 5, max = 50)
    @Email
    private String email;

    @NotNull
    @Size(min = 1)
    private Long[] roles;

    public UserForm() {
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long[] getRoles() {
        return roles;
    }

    public void setRoles(Long[] roles) {
        this.roles = roles;
    }
}