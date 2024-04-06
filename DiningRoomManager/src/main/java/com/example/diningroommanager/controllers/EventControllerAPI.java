package com.example.diningroommanager.controllers;

import com.example.diningroommanager.dtos.EventDetailsDTO;
import com.example.diningroommanager.entities.Event;
import com.example.diningroommanager.repositories.EventRepository;
import com.example.diningroommanager.repositories.LayoutRepository;
import com.example.diningroommanager.repositories.MenuRepository;
import com.example.diningroommanager.repositories.SeatingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.diningroommanager.dtos.DTOConverters.*;

@RestController
public class EventControllerAPI {
    private final EventRepository eventRepo;
    private final SeatingRepository seatingRepo;
    private final LayoutRepository layoutRepo;
    private final MenuRepository menuRepo;

    public EventControllerAPI(EventRepository eventRepo, SeatingRepository seatingRepo, LayoutRepository layoutRepo, MenuRepository menuRepo) {
        this.eventRepo = eventRepo;
        this.seatingRepo = seatingRepo;
        this.layoutRepo = layoutRepo;
        this.menuRepo = menuRepo;
    }

    @GetMapping("/api/events")
    public ResponseEntity<List<EventDetailsDTO>> getEventsWithDetails(){
        var events = eventRepo.findAll();
        return new ResponseEntity<>(convertToEventDetailsDTOList(events), HttpStatus.OK);
    }
}
