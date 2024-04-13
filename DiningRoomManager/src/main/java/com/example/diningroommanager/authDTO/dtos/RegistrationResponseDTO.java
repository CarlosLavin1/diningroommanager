package com.example.diningroommanager.authDTO.dtos;

import java.util.List;

public class RegistrationResponseDTO  {

    private String userName;

    private String email;

    private List<ValidationErrorDTO> errors;

    public RegistrationResponseDTO() {
    }

    public RegistrationResponseDTO(List<ValidationErrorDTO> errors) {
        this.errors = errors;
    }

    public RegistrationResponseDTO(RegistrationRequestDTO user) {
        this(user.getUserName(), user.getEmail());
    }

    public RegistrationResponseDTO(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ValidationErrorDTO> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidationErrorDTO> errors) {
        this.errors = errors;
    }
}
