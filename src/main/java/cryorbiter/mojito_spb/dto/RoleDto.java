package cryorbiter.mojito_spb.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDto implements Serializable {

    private static final long serialVersionUID = 2L;
    protected Long id;
    protected String name;

    public RoleDto() {
    }

    public RoleDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
