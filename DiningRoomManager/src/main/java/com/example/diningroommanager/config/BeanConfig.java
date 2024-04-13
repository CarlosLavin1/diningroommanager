package com.example.diningroommanager.config;


import com.example.diningroommanager.jwtCreator.config.JWTCreatorBeanConfig;
import com.example.diningroommanager.jwtValidator.config.JWTValidatorBeanConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Import({JWTValidatorBeanConfig.class, JWTCreatorBeanConfig.class})
@Configuration
public class BeanConfig {
//    @Primary
//    @Bean
//    public Logger getLogger(){
//        return LoggerFactory.getLogger(LoginConfig.class);
//    }
//
//    @Bean
//    public JavaMailSender getJavaMailSenderImpl(){
//        return new JavaMailSenderImpl();
//    }

//    @Bean
//    public LoginToken getLoginTokenImpl(){
//        return new LoginTokenImpl();
//    }
}
