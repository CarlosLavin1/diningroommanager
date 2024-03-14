package com.example.diningroommanager.controllers;

import com.example.diningroommanager.entities.Event;
import com.example.diningroommanager.entities.Layout;
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
public class EventController {
    private final EventRepository eventRepo;
    private final SeatingRepository seatingRepo;

    public EventController(EventRepository eventRepo, SeatingRepository seatingRepo) {
        this.eventRepo = eventRepo;
        this.seatingRepo = seatingRepo;
    }

    @GetMapping(value = "/events")
    public String getAll(Model model) {
        var items = eventRepo.findAll();
        model.addAttribute("events", items);

        return "event/index";
    }

    @GetMapping(value = "/event/details/{id}")
    public String details(Model model, @PathVariable int id) {
        var item = eventRepo.findById(id);
        if(item.isPresent())
            model.addAttribute("event", item.get());

        return "event/detail";
    }

    @GetMapping(value = "/event/create")
    public String create(Model model) {
        model.addAttribute("event", new Event());
        return "event/create";
    }

    @PostMapping(value = "/event/create")
    public String create(@Valid Event event, BindingResult br) {
        if (!br.hasErrors()) {
            eventRepo.save(event);

            return "redirect:/events";
        } else {
            return "event/create";
        }
    }

    @GetMapping(value = "/event/edit/{id}")
    public String edit(Model model, @PathVariable int id) {
        var item = eventRepo.findById(id);

        if (item.isPresent()) {
            model.addAttribute("event", item.get());
        }

        return "event/edit";
    }

    @PostMapping(value = "/event/edit/{id}")
    public String edit(Event event, Model model) {
        eventRepo.save(event);

        return "redirect:/events";
    }

    @GetMapping(value = "/event/delete/{id}")
    public String delete(@PathVariable int id, Model model) {
        var item = eventRepo.findById(id);

        if (item.isPresent()) {
            model.addAttribute("event", item.get());
        }

        return "event/delete";
    }

    @PostMapping(value = "/event/delete/{id}")
    public String delete(@PathVariable int id) {

        eventRepo.deleteById(id);

        return "redirect:/events";
    }
}
