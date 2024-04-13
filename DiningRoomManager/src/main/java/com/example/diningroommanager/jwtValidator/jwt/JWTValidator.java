package com.example.diningroommanager.jwtValidator.jwt;




import com.example.diningroommanager.authDTO.dtos.UserTokenDTO;
import com.example.diningroommanager.authDTO.enums.DefaultRoles;

import java.util.List;
import java.util.Map;


public interface JWTValidator {

    UserTokenDTO getUser(String token) throws Exception;

    Map<String, Object> getTokenClaims(String token) throws Exception;

    boolean isValid(String token) throws Exception;

    boolean isValid(String token, String ... roles) throws Exception;

    boolean isValid(String token, DefaultRoles... roles) throws Exception;

    boolean isValid(String token, List<String> roles) throws Exception;

}
