package com.example.diningroommanager.jwtCreator.config;



import com.example.diningroommanager.jwtCreator.jwt.JWTCreator;
import com.example.diningroommanager.jwtCreator.privatekey.PrivateKeyFileService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({JWTCreator.class, PrivateKeyFileService.class})
@Configuration
public class JWTCreatorBeanConfig {

}
