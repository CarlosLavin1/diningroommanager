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
                .addPathPatterns("/seating/delete/{id}**");
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/layout/create**")
                .addPathPatterns("/layout/edit/{id}**")
                .addPathPatterns("/layout/delete/{id}**");
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/menu/create**")
                .addPathPatterns("/menu/edit/{id}**")
                .addPathPatterns("/menu/delete/{id}**");
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/table/create**")
                .addPathPatterns("/table/edit/{id}**")
                .addPathPatterns("/table/delete/{id}**");
    }
}
