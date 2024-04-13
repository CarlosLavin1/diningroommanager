package com.example.diningroommanager.jwtCreator.jwt;


import com.example.diningroommanager.authDTO.dtos.UserRequestDTO;

public interface JWTRetriever {

    String generateJWTToken(UserRequestDTO user) throws Exception;
}
