package com.example.diningroommanager.interceptor;

import com.example.diningroommanager.jwtValidator.jwt.JWTValidator;
import com.example.diningroommanager.login.LoginToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JWTInterceptor implements HandlerInterceptor {
    private final JWTValidator jwtValidator;
    private final LoginToken loginToken;

    public JWTInterceptor(JWTValidator jwtValidator, LoginToken loginToken) {
        this.jwtValidator = jwtValidator;
        this.loginToken = loginToken;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!isValid()){
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }

    private boolean isValid() {
        try {
            if(!loginToken.hasToken()){
                return false;
            }
            return jwtValidator.isValid(loginToken.getToken());
        } catch (Exception e){
            return false;
        }
    }
}
