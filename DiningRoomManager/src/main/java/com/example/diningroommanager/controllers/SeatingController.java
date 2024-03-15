package com.example.diningroommanager.controllers;

import com.example.diningroommanager.entities.Seating;
import com.example.diningroommanager.repositories.EventRepository;
import com.example.diningroommanager.repositories.SeatingRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
        var items = seatingRepo.findAll();
        model.addAttribute("seatings", items);

        return "seating/index";
    }

    @GetMapping("/seating/create/{id}")
    public String create(Model model, @PathVariable int id) {
        var event = eventRepo.findById(id);
        if (event.isPresent())
            model.addAttribute("event", event);
        model.addAttribute("seating", new Seating());

        return "seating/create";
    }

    @PostMapping("/seating/create/{id}")
    public String create(Model model, @PathVariable int id, @Valid Seating seating, BindingResult br) {
        if (!br.hasErrors()) {
            var event = eventRepo.findById(id);
            if (event.isPresent()) {
                seating.setEvent(event.get());
            }

            seatingRepo.save(seating);
            return "redirect:/event/details/{id}";
        } else {
            var event = eventRepo.findById(id);
            model.addAttribute("event", event);
            return "seating/create";
        }
    }

    @GetMapping(value = "/seating/details/{id}")
    public String details(Model model, @PathVariable int id) {
        var item = seatingRepo.findById(id);
        if(item.isPresent())
            model.addAttribute("seating", item.get());

        return "seating/detail";
    }
}
