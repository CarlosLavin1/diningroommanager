package com.example.diningroommanager.authDTO.dtos;

import java.util.List;
import java.util.Map;

public class UserTokenDTO {

    private String token;

    private String userName;

    private List<String> roles;

    private Map<String, Object> claims;

    public UserTokenDTO() {
    }

    public UserTokenDTO(String token) {
        this.token = token;
    }

    public UserTokenDTO(String token, String userName, Map<String, Object> claims) {
        this.token = token;
        this.userName = userName;
        this.claims = claims;
    }

    public UserTokenDTO(String token, String userName, Map<String, Object> claims, List<String> roles) {
        this.token = token;
        this.userName = userName;
        this.roles = roles;
        this.claims = claims;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, Object> getClaims() {
        return claims;
    }

    public void setClaims(Map<String, Object> claims) {
        this.claims = claims;
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
}
