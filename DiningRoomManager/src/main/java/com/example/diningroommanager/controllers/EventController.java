package com.example.diningroommanager.controllers;

import com.example.diningroommanager.repositories.EventRepository;
import com.example.diningroommanager.repositories.SeatingRepository;
import org.springframework.stereotype.Controller;

@Controller
public class EventController {
    private final EventRepository eventRepo;
    private final SeatingRepository seatingRepo;

    public EventController(EventRepository eventRepo, SeatingRepository seatingRepo) {
        this.eventRepo = eventRepo;
        this.seatingRepo = seatingRepo;
    }
}
