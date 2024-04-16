package com.example.diningroommanager.controllers;

import com.example.diningroommanager.entities.DiningTable;
import com.example.diningroommanager.login.LoginToken;
import com.example.diningroommanager.repositories.LayoutRepository;
import com.example.diningroommanager.repositories.TableRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TableController {
    private final TableRepository tableRepository;
    private final LayoutRepository layoutRepository;
    private final LoginToken loginToken;


    public TableController(TableRepository tableRepository, LayoutRepository layoutRepository, LoginToken loginToken) {
        this.tableRepository = tableRepository;
        this.layoutRepository = layoutRepository;
        this.loginToken = loginToken;
    }

    @ModelAttribute("authenticated")
    public boolean getAuthenticated(){
        return loginToken.hasToken();
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


    @GetMapping(value = "table/create/{id}")
    public String create(Model model, @PathVariable int id) {
        var layout = layoutRepository.findById(id).orElse(null);

        if (layout != null){
            var table = new DiningTable();

            // Set the layout of the new DiningTable object
            table.setLayout(layout);
            model.addAttribute("tables", table);
        }

        return "table/create";
    }

    @PostMapping(value = "table/create/{id}")
    public String create(@Valid @ModelAttribute(value = "tables") DiningTable diningTable, BindingResult br, @PathVariable int id, Model model) {
        var layout = layoutRepository.findById(id).orElse(null);

        if (!br.hasErrors()) {
            // Create new table
            var newTable = new DiningTable(layout, diningTable.getNumberOfSeats());

            tableRepository.save(newTable);

            return "redirect:/layout/detail/" + id;
        } else {
            if (layout != null){
                diningTable.setLayout(layout);
                model.addAttribute("tables", diningTable);
            }

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
    public String edit(DiningTable diningTable, @PathVariable int id, BindingResult br, Model model) {
        // Check for errors
        if (!br.hasErrors()) {

            // Find the existing dining table
            var existingTable = tableRepository.findById(id);

            // Check if the table is present
            if (existingTable.isPresent()){
                // Get the table to update
                var tableToUpdate = existingTable.get();

                // Get the layout from the existing table
                var layout = tableToUpdate.getLayout();

                // Set and save the new number of seats
                tableToUpdate.setNumberOfSeats(diningTable.getNumberOfSeats());
                tableRepository.save(tableToUpdate);

                // Redirect back to the detail page
                return "redirect:/layout/detail/" + layout.getId();
            }
        }

        return "table/edit";
    }

    @GetMapping(value = "/table/detail/{id}")
    public String details(@PathVariable int id, Model model) {
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
        // Find the existing dining table
        var table = tableRepository.findById(id);

        if (table.isPresent()){

            // Get the dining tables
            var diningTable = table.get();

            // get the layouts
            var layout = diningTable.getLayout();

            tableRepository.deleteById(id);

            // Redirect back to the detail page
            return "redirect:/layout/detail/" + layout.getId();
        }

        return "table/delete";
    }
}
