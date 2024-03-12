package com.example.diningroommanager.controllers;

import com.example.diningroommanager.repositories.LayoutRepository;
import com.example.diningroommanager.repositories.TableRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LayoutController {
    private final LayoutRepository layoutRepository;
    private final TableRepository tableRepository;



    public LayoutController(LayoutRepository layoutRepository, TableRepository tableRepository) {
        this.layoutRepository = layoutRepository;
        this.tableRepository = tableRepository;
    }


    @GetMapping("/layout")
    public String layout(Model model){
        try {
            // fetch all records
            var values = layoutRepository.findAll();

            // Add to model
            model.addAttribute("layouts", values);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return "layout/index";
    }
}
