package com.example.diningroommanager.authDTO.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class RegistrationRequestDTO {

    private int id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String userName;

    @NotBlank
    @Size(min = 4, message = "Password must be at lest 4 characters.")
    private String password;

    @Email
    private String email;

    private List<ValidationErrorDTO> errors;

    public RegistrationRequestDTO(){
    }

    public RegistrationRequestDTO(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public RegistrationRequestDTO(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public RegistrationRequestDTO(int id, String firstName, String lastName, String userName, String password, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ValidationErrorDTO> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidationErrorDTO> errors) {
        this.errors = errors;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}