package com.example.diningroommanager.authDTO.dtos;

public class LoginResponseDTO {

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token, String userName) {
        this(token, userName, 3600);
    }

    public LoginResponseDTO(String token, String userName, int expiresIn) {
        this(token, userName, expiresIn, "Bearer");
    }

    public LoginResponseDTO(String token, String userName, long expiresIn, String tokenType) {
        this.userName = userName;
        this.expiresIn = expiresIn;
        this.token = token;
        this.tokenType = tokenType;
    }

    private String userName;

    private long expiresIn;

    private String token;

    private String tokenType;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
