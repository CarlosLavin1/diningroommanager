package com.example.diningroommanager.controllers;

import com.example.diningroommanager.repositories.LayoutRepository;
import com.example.diningroommanager.repositories.TableRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TableController {
    private final TableRepository tableRepository;
    private final LayoutRepository layoutRepository;

    public TableController(TableRepository tableRepository, LayoutRepository layoutRepository) {
        this.tableRepository = tableRepository;
        this.layoutRepository = layoutRepository;
    }


    @GetMapping("/table")
    public String table(Model model) {
        try {
            var values = tableRepository.findAll();

            model.addAttribute("table", values);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return "table/index";
    }
}
