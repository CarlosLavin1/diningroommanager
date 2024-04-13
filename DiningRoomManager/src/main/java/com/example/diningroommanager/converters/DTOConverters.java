package com.example.diningroommanager.converters;

import com.example.diningroommanager.authDTO.dtos.UserRequestDTO;
import com.example.diningroommanager.entities.User;


public class DTOConverters {
    public static UserRequestDTO convert(User user){
        return new UserRequestDTO(user.getId(), user.getUserName(), user.getEmail());
    }
}
