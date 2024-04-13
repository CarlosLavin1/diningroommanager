package com.example.diningroommanager.controllers;

import com.example.diningroommanager.authDTO.dtos.RegistrationRequestDTO;
import com.example.diningroommanager.repositories.UserRepository;
import com.example.diningroommanager.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static com.example.diningroommanager.constants.AttributeConstants.MESSAGE_KEY;

@Controller
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new RegistrationRequestDTO());
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") RegistrationRequestDTO registrationRequest, BindingResult br, Model model) {

        if (!br.hasErrors()) {
            try {

                var user = userService.register(registrationRequest);
                if(user != null){
                    return "redirect:/login";
                } else {
                    model.addAttribute(MESSAGE_KEY, "User Name or Email already in use");
                    return "user/register";
                }
            } catch (Exception e) {
                model.addAttribute(MESSAGE_KEY, "Error when registering user");
            }
        }

        return "user/register";
    }
}
