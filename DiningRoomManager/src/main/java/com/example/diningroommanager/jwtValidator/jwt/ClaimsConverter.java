package com.example.diningroommanager.jwtValidator.jwt;

import java.util.List;
import java.util.Map;

import static com.example.diningroommanager.authDTO.types.JWTClaimNames.*;


public class ClaimsConverter {

    public static String getUserName(Map<String, Object> claims){
        var claimsObject = claims.get(USER_CLAIM);
        return String.valueOf(claimsObject);
    }

    public static List<String> getRoles(Map<String, Object> claims) {
        var claimRolesObject = claims.get(ROLES_CLAIM);

        if(claimRolesObject == null){
            return null;
        }

        try{
            return (List<String>) claimRolesObject;
        } catch (ClassCastException ex){
            return  null;
        }
    }
}