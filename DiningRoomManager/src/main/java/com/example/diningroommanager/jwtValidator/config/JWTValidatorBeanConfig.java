package com.example.diningroommanager.jwtValidator.config;


import com.example.diningroommanager.jwtValidator.jwt.JWTValidationService;
import com.example.diningroommanager.jwtValidator.publickey.PublicKeyFileService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({JWTValidationService.class, PublicKeyFileService.class})
@Configuration
public class JWTValidatorBeanConfig {

}
