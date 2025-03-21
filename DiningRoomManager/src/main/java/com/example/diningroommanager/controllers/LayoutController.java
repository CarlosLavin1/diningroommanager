package com.example.diningroommanager.controllers;

import com.example.diningroommanager.entities.Layout;
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
public class LayoutController {
    private final LayoutRepository layoutRepository;
    private final TableRepository tableRepository;
    private final LoginToken loginToken;


    public LayoutController(LayoutRepository layoutRepository, TableRepository tableRepository, LoginToken loginToken) {
        this.layoutRepository = layoutRepository;
        this.tableRepository = tableRepository;
        this.loginToken = loginToken;
    }

    @ModelAttribute("authenticated")
    public boolean getAuthenticated(){
        return loginToken.hasToken();
    }


    @GetMapping(value = "/layout")
    public String layout(Model model) {
        // fetch all records
        var values = layoutRepository.findAll();

        // Add to model
        model.addAttribute("layouts", values);
        int[] seats = new int[values.size()];
        int counter = 0;
        for (var layout : values)
            seats[counter++] = getTotalSeatsForTable(layout);
        model.addAttribute("seats", seats);

        return "layout/index";
    }

    private int getTotalSeatsForTable(Layout layout){
        int seats = 0;
        var tables = layout.getDiningTables();
        for(var table : tables)
            seats += table.getNumberOfSeats();
        return seats;
    }

    @GetMapping(value = "layout/create")
    public String create(Model model) {
        model.addAttribute("layout", new Layout());
        return "layout/create";
    }

    @PostMapping(value = "layout/create")
    public String create(@Valid Layout layout, BindingResult br) {
        if (!br.hasErrors()) {
            layoutRepository.save(layout);

            return "redirect:/layout";
        } else {
            return "layout/create";
        }
    }


    @GetMapping(value = "layout/edit/{id}")
    public String edit(Model model, @PathVariable int id) {
        var layout = layoutRepository.findById(id);

        if (layout.isPresent()) {
            model.addAttribute("layout", layout.get());
        }

        return "layout/edit";
    }

    @PostMapping(value = "layout/edit/{id}")
    public String edit(@Valid Layout layout, BindingResult br, Model model) {
        if (br.hasErrors()){
            return "layout/edit";
        }

        layoutRepository.save(layout);

        return "redirect:/layout";
    }

    @GetMapping(value = "/layout/detail/{id}")
    public String details(@PathVariable int id, Model model) {
        var layout = layoutRepository.findById(id);

        var tables = tableRepository.findByLayout_Id(id);
        model.addAttribute("tables", tables);

        if (layout.isPresent()) {
            model.addAttribute("layout", layout.get());
            model.addAttribute("seatCount", getTotalSeatsForTable(layout.get()));
        }

        return "layout/detail";
    }

    @GetMapping(value = "/layout/delete/{id}")
    public String delete(@PathVariable int id, Model model) {
        var layout = layoutRepository.findById(id);

        if (layout.isPresent()) {
            model.addAttribute("layout", layout.get());
        }

        return "layout/delete";
    }

    @PostMapping(value = "/layout/delete/{id}")
    public String deleteConfirm(@PathVariable int id) {

        layoutRepository.deleteById(id);

        return "redirect:/layout";
    }
}
