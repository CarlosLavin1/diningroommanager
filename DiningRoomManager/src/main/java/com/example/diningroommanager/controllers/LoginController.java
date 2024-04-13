package com.example.diningroommanager.controllers;


import com.example.diningroommanager.authDTO.dtos.UserRequestDTO;
import com.example.diningroommanager.jwtCreator.jwt.JWTRetriever;
import com.example.diningroommanager.login.LoginToken;
import com.example.diningroommanager.repositories.UserRepository;
import com.example.diningroommanager.services.HashingService;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.example.diningroommanager.constants.AttributeConstants.MESSAGE_KEY;
import static com.example.diningroommanager.converters.DTOConverters.convert;


@Controller
public class LoginController {

    private final UserRepository userRepository;

    private final HashingService hashingService;

    private final LoginToken loginToken;
    private final JWTRetriever jwtRetriever;

    public LoginController(UserRepository userRepository, HashingService hashingService, LoginToken loginToken, JWTRetriever jwtRetriever) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.loginToken = loginToken;
        this.jwtRetriever = jwtRetriever;
    }

    @ModelAttribute("authenticated")
    public boolean getAuthenticated(){
        return loginToken.hasToken();
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {

        try {

            var user = userRepository.getUserByUserName(username);

            if(user == null)
                return "user/login";

            if (hashingService.valid(password, user.getPassword())) {
                UserRequestDTO userDTO = convert(user);
                var token = jwtRetriever.generateJWTToken(userDTO);
                loginToken.setToken(token);
                model.addAttribute("authenticated", loginToken.hasToken());
                return "redirect:/";
            }

        } catch (Exception e) {
            model.addAttribute(MESSAGE_KEY, "invalid username or password");
            return "user/login";
        }

        model.addAttribute(MESSAGE_KEY, "login error");
        return "user/login";
    }

    @PostMapping("/logout")
    public String logout() {
        loginToken.clear();
        return "redirect:/";
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<String> exceptionHandler(Exception ex){
        return new ResponseEntity<>("Unexpected Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
