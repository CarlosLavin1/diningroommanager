package com.example.diningroommanager.config;


import com.example.diningroommanager.interceptor.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final JWTInterceptor jwtInterceptor;

    public WebConfig(JWTInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // set interceptors for all relevant entities
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/event/create**")
                .addPathPatterns("/event/edit/{id}**")
                .addPathPatterns("/event/delete/{id}**");
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/seating/create**")
                .addPathPatterns("/seating/edit/{id}**")
                .addPathPatterns("/seating/details/{id}**")
                .addPathPatterns("/seating/delete/{id}**");
    }
}
