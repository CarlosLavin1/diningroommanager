package com.example.diningroommanager.controllers;

import com.example.diningroommanager.entities.ReservationRequest;
import com.example.diningroommanager.entities.Seating;
import com.example.diningroommanager.login.LoginToken;
import com.example.diningroommanager.repositories.ReservationRequestRepository;
import com.example.diningroommanager.repositories.SeatingRepository;
import com.example.diningroommanager.repositories.StatusRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReservationRequestController {
    private ReservationRequestRepository reservationRequestRepository;
    private SeatingRepository seatingRepository;
    private StatusRepository statusRepository;
    private final LoginToken loginToken;


    public ReservationRequestController(ReservationRequestRepository reservationRequestRepository, SeatingRepository seatingRepository, StatusRepository statusRepository, LoginToken loginToken) {
        this.reservationRequestRepository = reservationRequestRepository;
        this.seatingRepository = seatingRepository;
        this.statusRepository = statusRepository;
        this.loginToken = loginToken;
    }

    @ModelAttribute("authenticated")
    public boolean getAuthenticated(){
        return loginToken.hasToken();
    }

    @GetMapping(value = "/res/create/{id}")
    public String create(@PathVariable int id, Model model) {
        var seating = seatingRepository.findById(id);
        if(seating.isPresent())
            model.addAttribute("seating", seating.get());
        model.addAttribute("reservationrequest", new ReservationRequest());
        return "res/create";
    }

    @PostMapping("/res/create/{id}")
    public String createReservationRequest(@PathVariable int id, @Valid @ModelAttribute(value = "reservationrequest") ReservationRequest reservationrequest, BindingResult br, Model model) {
        if (!br.hasErrors()) {
            var seating =  seatingRepository.findById(id);
            if(seating.isPresent())
                reservationrequest.setSeating(seating.get());

            reservationrequest.setStatus(statusRepository.getStatusByValue("pending"));
            reservationRequestRepository.save(reservationrequest);

            return "redirect:/seating/details/{id}";
        } else {
            var seating = seatingRepository.findById(id);
            if(seating.isPresent())
                model.addAttribute("seating", seating.get());
            //model.addAttribute("reservationrequest", reservationRequest);
            return "res/create";
        }
    }
    // list of reservations for an event
    @GetMapping(value = "/res/index/{id}")
    public String getAllForEvent(@PathVariable int id, Model model){
        var items = reservationRequestRepository.getReservationRequestsBySeating_Event_Id(id);
        model.addAttribute("reservations", items);
        model.addAttribute("resPending", 1);
        model.addAttribute("resApproved", 2);
        model.addAttribute("resDenied", 3);
        model.addAttribute("eventId", id);
        return "res/index";
    }
    //res index with search filters
    @GetMapping(value = "/res/filtered/{id}/{filter}")
    public String filterReservationRequests(@PathVariable int id, @PathVariable int filter, Model model){
        String f = "";
        switch (filter){
            case 1:
                f = "pending";
                break;
            case 2:
                f = "approved";
                break;
            default:
                f = "denied";
                break;
        }
        var items = reservationRequestRepository.getReservationRequestsByStatus_ValueAndSeating_Event_Id(f, id);
        model.addAttribute("reservations", items);
        model.addAttribute("resPending", 1);
        model.addAttribute("resApproved", 2);
        model.addAttribute("resDenied", 3);
        model.addAttribute("eventId", id);
        return "res/filtered";
    }

    @GetMapping(value = "/res/detail/{id}")
    public String resDetails(@PathVariable int id, Model model){
        var item = reservationRequestRepository.findById(id);
        if(item.isPresent())
            model.addAttribute("res", item.get());

        return "res/detail";
    }

    @GetMapping(value = "/res/edit/{id}")
    public String edit(@PathVariable int id, Model model){
        var item = reservationRequestRepository.findById(id);
        if(item.isPresent()){
            model.addAttribute("res", item.get());
            var seating = seatingRepository.findById(item.get().getSeating().getId());
            if(seating.isPresent()){
                model.addAttribute("seating", seating.get());
                model.addAttribute("eventId", seating.get().getEvent().getId());
            }
        }
        model.addAttribute("statuses", statusRepository.findAll());
        return "res/edit";
    }

    @PostMapping(value = "/res/edit/{id}")
    public String edit(ReservationRequest res, BindingResult br, @PathVariable int id, Model model){
        var oldRes =  reservationRequestRepository.findById(id);
        if(oldRes.isPresent()){
            res.setSeating(oldRes.get().getSeating());
        }
        var item = seatingRepository.findById(res.getSeating().getId());
        Seating seating = null;
        if(item.isPresent())
            seating = item.get();
        int eventId = seating.getEvent().getId();
        reservationRequestRepository.save(res);

        var items = reservationRequestRepository.getReservationRequestsBySeating_Event_Id(eventId);
        model.addAttribute("reservations", items);
        model.addAttribute("statuses", statusRepository.findAll());
        model.addAttribute("resPending", 1);
        model.addAttribute("resApproved", 2);
        model.addAttribute("resDenied", 3);
        model.addAttribute("eventId", eventId);
        return "redirect:/res/index/" + eventId;
    }

    @GetMapping(value = "/res/delete/{id}")
    public String delete(@PathVariable int id, Model model){
        var item = reservationRequestRepository.findById(id);
        if(item.isPresent())
            model.addAttribute("res", item.get());

        return "res/delete";
    }


    @PostMapping(value = "/res/delete/{id}")
    public String deleteConfirm(@PathVariable int id, Model model){
        var oldRes = reservationRequestRepository.findById(id);
        int eventId = -1;
        if(oldRes.isPresent()){
            eventId = oldRes.get().getSeating().getEvent().getId();
        }
        reservationRequestRepository.deleteById(id);
        return "redirect:/res/index/" + eventId;
    }
}
