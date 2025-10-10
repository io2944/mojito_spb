package cryorbiter.mojito_spb.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Setter
@Getter
@Component
public class RoleForm {

    private Long id;

    @NotNull
    @Size(min = 2, max = 25)
    private String name;

    public RoleForm() {
    }

}
