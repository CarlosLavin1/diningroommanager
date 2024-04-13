package com.example.diningroommanager.login;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import static com.example.diningroommanager.constants.AttributeConstants.*;


@Component
public class LoginTokenImpl implements LoginToken{
    private final HttpSession session;

    public LoginTokenImpl(HttpSession session) {
        this.session = session;
    }

    @Override
    public String getToken() {
        var attribute = session.getAttribute(LOGIN_TOKEN_KEY);
        if (attribute == null)
            return null;
        else
            return attribute.toString();
    }

    @Override
    public void setToken(String token) {
        session.setAttribute(LOGIN_TOKEN_KEY, token);
    }

    @Override
    public void clear() {
        session.removeAttribute(LOGIN_TOKEN_KEY);
    }

    @Override
    public boolean hasToken() {
        var token = getToken();
        if (token == null)
            return false;
        return !token.isBlank();
    }
}
