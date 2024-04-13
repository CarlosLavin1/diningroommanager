package com.example.diningroommanager.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class HashingService {

    public String hash(String data){
        var encoder = new BCryptPasswordEncoder();
        return encoder.encode(data);
    }

    public boolean valid(String plainPassword, String hashedPassword){
        var encoder = new BCryptPasswordEncoder();
        return encoder.matches(plainPassword, hashedPassword);
    }
}
