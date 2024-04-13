package com.example.diningroommanager.authDTO.dtos;


import jakarta.validation.constraints.Email;

import java.util.List;

public class UserRequestDTO extends UserRolesRequestDTO {

    public UserRequestDTO() {
        super();
    }

    public UserRequestDTO(int id, String userName, String email) {
        this(id, userName, email, null);;
    }

    public UserRequestDTO(int id, String userName,String email, List<String> roles) {
        super(userName, roles);
        this.id = id;
        this.email = email;
    }

    private int id;

    @Email
    private String email;

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

}
