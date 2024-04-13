package com.example.diningroommanager.controllers;

import com.example.diningroommanager.dtos.EventDetailsDTO;
import com.example.diningroommanager.login.LoginToken;
import com.example.diningroommanager.repositories.EventRepository;
import com.example.diningroommanager.repositories.LayoutRepository;
import com.example.diningroommanager.repositories.MenuRepository;
import com.example.diningroommanager.repositories.SeatingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.diningroommanager.dtos.DTOConverters.*;

@RestController
public class EventControllerAPI {
    private final EventRepository eventRepo;
    private final SeatingRepository seatingRepo;
    private final LayoutRepository layoutRepo;
    private final MenuRepository menuRepo;
    private final LoginToken loginToken;


    public EventControllerAPI(EventRepository eventRepo, SeatingRepository seatingRepo, LayoutRepository layoutRepo, MenuRepository menuRepo, LoginToken loginToken) {
        this.eventRepo = eventRepo;
        this.seatingRepo = seatingRepo;
        this.layoutRepo = layoutRepo;
        this.menuRepo = menuRepo;
        this.loginToken = loginToken;
    }

    @ModelAttribute("authenticated")
    public boolean getAuthenticated(){
        return loginToken.hasToken();
    }

    @GetMapping("/api/events")
    public ResponseEntity<List<EventDetailsDTO>> getAllEventsWithDetails(){
        var events = eventRepo.findAll();
        return new ResponseEntity<>(convertToEventDetailsDTOList(events), HttpStatus.OK);
    }

    @GetMapping("/api/event/{id}")
    public ResponseEntity<EventDetailsDTO> getEventWithDetailsById(@PathVariable int id){
        var item = eventRepo.findById(id);
        if(item.isPresent())
            return new ResponseEntity<>(convertToEventDetailsDTO(item.get()), HttpStatus.OK);
        return null;
    }
}
