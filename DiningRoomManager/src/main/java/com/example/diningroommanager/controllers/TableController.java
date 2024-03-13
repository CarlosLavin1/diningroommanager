package com.example.diningroommanager.controllers;

import com.example.diningroommanager.entities.Layout;
import com.example.diningroommanager.entities.Table;
import com.example.diningroommanager.repositories.LayoutRepository;
import com.example.diningroommanager.repositories.TableRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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


    @GetMapping(value = "table/create")
    public String create(Model model) {
        model.addAttribute("tables", new Table());
        return "table/create";
    }

    @PostMapping(value = "table/create")
    public String create(@Valid Table table, BindingResult br) {
        if (!br.hasErrors()) {
            tableRepository.save(table);

            return "redirect:/table";
        } else {
            return "table/create";
        }
    }


    @GetMapping(value = "table/edit/{id}")
    public String edit(Model model, @PathVariable int id) {
        var table = tableRepository.findById(id);

        if (table.isPresent()) {
            model.addAttribute("table", table.get());
        }

        return "table/edit";
    }

    @PostMapping(value = "table/edit/{id}")
    public String edit(Table table, Model model) {

        tableRepository.save(table);

        return "redirect:/table";
    }

    @GetMapping(value = "/table/detail/{id}")
    public String details(@PathVariable int id, Model model) {
        // also fetch tables later on
        var table = tableRepository.findById(id);

        if (table.isPresent()) {
            model.addAttribute("table", table.get());
        }

        return "table/detail";
    }

    @GetMapping(value = "/table/delete/{id}")
    public String delete(@PathVariable int id, Model model) {
        var table = tableRepository.findById(id);

        if (table.isPresent()) {
            model.addAttribute("table", table.get());
        }

        return "table/delete";
    }

    @PostMapping(value = "/table/delete/{id}")
    public String deleteConfirm(@PathVariable int id) {

        tableRepository.deleteById(id);

        return "redirect:/table";
    }
}
