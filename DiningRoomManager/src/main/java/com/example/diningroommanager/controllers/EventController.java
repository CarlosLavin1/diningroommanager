package com.example.diningroommanager.controllers;

import com.example.diningroommanager.entities.Event;
import com.example.diningroommanager.entities.Layout;
import com.example.diningroommanager.repositories.EventRepository;
import com.example.diningroommanager.repositories.LayoutRepository;
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
    private final LayoutRepository layoutRepo;

    public EventController(EventRepository eventRepo, SeatingRepository seatingRepo, LayoutRepository layoutRepo) {
        this.eventRepo = eventRepo;
        this.seatingRepo = seatingRepo;
        this.layoutRepo = layoutRepo;
    }

    @GetMapping(value = {"/events", "/"})
    public String getAll(Model model) {
        var items = eventRepo.findAll();
        model.addAttribute("events", items);

        return "event/index";
    }

    @GetMapping(value = "/event/details/{id}")
    public String details(Model model, @PathVariable int id) {
        var item = eventRepo.findById(id);

        if (item.isPresent()) {
            model.addAttribute("event", item.get());
            model.addAttribute("layout", item.get().getLayout());
        }

        return "event/detail";
    }

    @GetMapping(value = "/event/create")
    public String create(Model model) {
        var event = new Event();
        event.setLayout(new Layout());

        // find all the layouts and to the models attributes
        var layouts = layoutRepo.findAll();

        model.addAttribute("event", event);
        model.addAttribute("layouts", layouts);

        return "event/create";
    }

    @PostMapping(value = "/event/create")
    public String create(@Valid Event event, BindingResult br, Model model) {
        //  Check if a layout is selected
        if (event.getLayout() != null && event.getLayout().getId() != null) {

            if (!br.hasErrors()) {
                // Find the selected layout otherwise return null
                var layout = layoutRepo.findById(event.getLayout().getId()).orElse(null);

                // check if layout not null
                if (layout != null) {
                    // set the layout to event and save
                    event.setLayout(layout);
                    eventRepo.save(event);

                    return "redirect:/events";
                }
            }
        } else {
            // Add an error message to the model
            model.addAttribute("layoutErr", "Please select a layout");
        }

        // Repopulate the layouts
        model.addAttribute("layouts", layoutRepo.findAll());

        return "event/create";
    }

    @GetMapping(value = "/event/edit/{id}")
    public String edit(Model model, @PathVariable int id) {
        var item = eventRepo.findById(id);
        var layouts = layoutRepo.findAll();

        if (item.isPresent()) {
            model.addAttribute("event", item.get());
            model.addAttribute("layouts", layouts);
        }

        return "event/edit";
    }

    @PostMapping(value = "/event/edit/{id}")
    public String edit(@PathVariable int id, BindingResult br, Event events, Model model) {
        if (!br.hasErrors()) {

            // Check if a layout is selected
            if (events.getLayout() != null && events.getLayout().getId() != null) {
                // Find the selected layout otherwise return null
                var layout = layoutRepo.findById(events.getLayout().getId()).orElse(null);

                // Check if layout not null
                if (layout != null) {

                    // Update the event and save
                    eventRepo.update(id, events.getName(), events.getStartDate(), events.getEndDate(),
                            events.getSeatingDuration(), events.getDescription(), events.getPrice(), layout.getId());


                    return "redirect:/events";
                }
            } else {
                model.addAttribute("layoutErr", "Please select a layout");
            }
        }
        return "event/edit";
    }

    @GetMapping(value = "/event/delete/{id}")
    public String delete(@PathVariable int id, Model model) {
        var item = eventRepo.findById(id);

        if (item.isPresent()) {
            model.addAttribute("event", item.get());
            var seatings = seatingRepo.getSeatingsByEvent_Id(item.get().getId());
            if (!seatings.isEmpty()) {
                model.addAttribute("seatingCount", seatings.size());
            } else {
                model.addAttribute("seatingCount", 0);
            }
        }

        return "event/delete";
    }

    @PostMapping(value = "/event/delete/{id}")
    public String delete(@PathVariable int id) {

        eventRepo.deleteById(id);

        return "redirect:/events";
    }
}
