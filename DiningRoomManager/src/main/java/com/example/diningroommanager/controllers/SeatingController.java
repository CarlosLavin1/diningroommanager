package com.example.diningroommanager.controllers;

import com.example.diningroommanager.entities.Seating;
import com.example.diningroommanager.repositories.EventRepository;
import com.example.diningroommanager.repositories.SeatingRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SeatingController {
    private final EventRepository eventRepo;
    private final SeatingRepository seatingRepo;

    public SeatingController(EventRepository eventRepo, SeatingRepository seatingRepo) {
        this.eventRepo = eventRepo;
        this.seatingRepo = seatingRepo;
    }

    @GetMapping(value = "/seatings")
    public String getAll(Model model) {
        var items = eventRepo.findAll();
        model.addAttribute("seatings", items);

        return "seating/index";
    }

    @GetMapping("/seating/create/{id}")
    public String create(Model model, @PathVariable int id) {
        var event = eventRepo.findById(id);
        model.addAttribute("event", event);
        model.addAttribute("seating", new Seating());

        return "seating/create";
    }

    @GetMapping(value = "/seating/details/{id}")
    public String details(Model model, @PathVariable int id) {
        var item = seatingRepo.findById(id);
        if(item.isPresent())
            model.addAttribute("seating", item.get());

        return "seating/detail";
    }
}
