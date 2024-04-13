package com.example.diningroommanager.authDTO.dtos;

import java.util.List;

public class UserRolesRequestDTO {

    private String userName;

    private List<String> roles;

    private List<ValidationErrorDTO> errors;

    public UserRolesRequestDTO() {
    }

    public UserRolesRequestDTO(String userName) {
        this();
        this.userName = userName;
    }

    public UserRolesRequestDTO(String userName, List<String> roles) {
        this(userName);
        this.roles = roles;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<ValidationErrorDTO> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidationErrorDTO> errors) {
        this.errors = errors;
    }
}
