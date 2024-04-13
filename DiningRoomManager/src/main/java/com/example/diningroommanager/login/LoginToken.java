package com.example.diningroommanager.login;


public interface LoginToken {

    String getToken();

    void setToken(String token);

    void clear();

    boolean hasToken();

}
