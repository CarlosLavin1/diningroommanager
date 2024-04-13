package com.example.diningroommanager.config;


import com.example.diningroommanager.config.interfaces.DefaultAdminConfig;
import com.example.diningroommanager.config.interfaces.EmailConfig;
import com.example.diningroommanager.jwtCreator.config.JWTConfig;
import com.example.diningroommanager.jwtCreator.config.PrivateKeyConfig;
import com.example.diningroommanager.jwtValidator.config.PublicKeyConfig;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EnvironmentConfig implements PublicKeyConfig, PrivateKeyConfig, JWTConfig, DefaultAdminConfig, EmailConfig {

    private final Environment environment;

    public EnvironmentConfig(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String getPrivateKeyFilePath() {
        return environment.getProperty("PrivateKey.FilePath");
    }

    @Override
    public String getPublicKeyFilePath() {
        return environment.getProperty("PublicKey.FilePath");
    }

    @Override
    public long getTimeout() {
        try{
            return Long.parseLong(Objects.requireNonNull(environment.getProperty("JWT.timeout")));
        } catch (Exception e){
            return 0;
        }
    }

    @Override
    public String getDefaultAdminUserName() {
        return environment.getProperty("Default.Admin.UserName");
    }

    @Override
    public String getDefaultAdminPassword() {
        return environment.getProperty("Default.Admin.Password");
    }

    @Override
    public String getDefaultFromEmailAddress() {
        return environment.getProperty("Default.From.Email");
    }
}
