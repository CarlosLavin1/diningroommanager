package com.example.diningroommanager.services;

import com.example.diningroommanager.authDTO.dtos.RegistrationRequestDTO;
import com.example.diningroommanager.entities.User;
import com.example.diningroommanager.repositories.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;


    private final HashingService hashingService;

    public UserService(UserRepository userRepository, HashingService hashingService) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
    }

    public boolean exists(RegistrationRequestDTO user) {
        return exists(user.getUserName());
    }

    public boolean exists(String userName){
        return userNameExists(userName);
    }

    public User register(RegistrationRequestDTO user) {
        return register(user.getUserName(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName());
    }

    //public User register(String userName, String password) {
        //return register(userName, password, null, null, null);
    //}

    public User register(String userName, String password, String email, String first, String last){
        var exists = userNameExists(userName);

        if(!exists) {
            var hashedPassword = hashingService.hash(password);

            var user = new User(userName, hashedPassword, first, last, email);

            userRepository.save(user);

            return user;
        } else {
            return null;
        }
    }

    public User getUser(String userName){
        return userRepository.getUserByUserName(userName);
    }

    public boolean userNameExists(String userName){
        var user = getUser(userName);

        return user != null;
    }
}
