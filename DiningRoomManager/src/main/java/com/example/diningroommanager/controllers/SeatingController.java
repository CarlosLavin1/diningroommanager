package com.example.diningroommanager.controllers;

import com.example.diningroommanager.config.interfaces.EmailConfig;
import com.example.diningroommanager.entities.ReservationRequest;
import com.example.diningroommanager.entities.Seating;
import com.example.diningroommanager.entities.Status;
import com.example.diningroommanager.login.LoginToken;
import com.example.diningroommanager.repositories.*;
import com.example.diningroommanager.services.EmailSender;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.format.DateTimeFormatter;

@Controller
public class SeatingController {
    private final EventRepository eventRepo;
    private final SeatingRepository seatingRepo;
    private final LoginToken loginToken;
    private final StatusRepository statusRepository;
    private final ReservationRequestRepository reservationReqRepo;
    private final EmailSender emailSender;
    private final EmailConfig emailConfig;
    private final TableRepository tableRepository;


    public SeatingController(EventRepository eventRepo, SeatingRepository seatingRepo, LoginToken loginToken, StatusRepository statusRepository, ReservationRequestRepository reservationReqRepo, EmailSender emailSender, EmailConfig emailConfig, TableRepository tableRepository) {
        this.eventRepo = eventRepo;
        this.seatingRepo = seatingRepo;
        this.loginToken = loginToken;
        this.statusRepository = statusRepository;
        this.reservationReqRepo = reservationReqRepo;
        this.emailSender = emailSender;
        this.emailConfig = emailConfig;
        this.tableRepository = tableRepository;
    }

    @ModelAttribute("authenticated")
    public boolean getAuthenticated(){
        return loginToken.hasToken();
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
            model.addAttribute("event", event.get());
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

            var newSeating = new Seating(seating.getEvent(), seating.getStartDateAndTime());

            seatingRepo.save(newSeating);
            return "redirect:/event/details/{id}";
        } else {
            var event = eventRepo.findById(id);
            if(event.isPresent())
                model.addAttribute("event", event.get());
            return "seating/create";
        }
    }

    @GetMapping(value = "/seating/details/{id}")
    public String details(Model model, @PathVariable int id) {
        var item = seatingRepo.findById(id);

        if(item.isPresent()) {
            var seating = item.get();

            var validReservations = reservationReqRepo.getReservationRequestsBySeating_Id(id);


            model.addAttribute("seating", seating);
            model.addAttribute("validRes", validReservations);
            model.addAttribute("resApproved", 2);
        }

        return "seating/detail";
    }


    @PostMapping(value = "/seating/{seatingId}/details/{requestId}")
    public String approveReservation(@PathVariable int seatingId, @PathVariable int requestId, Model model) {
        var reservationRequest = reservationReqRepo.findById(requestId);

        if (reservationRequest.isPresent()) {
            ReservationRequest request = reservationRequest.get();
            var oldStatus = request.getStatus();
            request.setStatus(statusRepository.getStatusByValue("approved"));
            reservationReqRepo.save(request);

            // Send email notification
            sendReservationStatusEmail(request, oldStatus, request.getStatus());
        }

        // Add the necessary model attributes here
        var item = seatingRepo.findById(seatingId);
        if(item.isPresent()) {
            var seating = item.get();
            var validReservations = reservationReqRepo.getReservationRequestsBySeating_Id(seatingId);
            model.addAttribute("seating", seating);
            model.addAttribute("validRes", validReservations);
        }

        return "seating/detail";
    }


    private void sendReservationStatusEmail(ReservationRequest reservationRequest, Status oldStatus, Status newStatus) {
        try {
            // Check if the email is not null and not blank
            if (reservationRequest.getEmail() != null && !reservationRequest.getEmail().isBlank()) {
                // Prepare the subject and message for the email
                var subject = "Reservation Status Update";
                var msg = "Hello, " + reservationRequest.getFullName() + ".\n\n" +
                        "Your reservation has been updated with the following status change:\n" +
                        "Previous Status: " + oldStatus.getDisplay() + "\n" +
                        "New Status: " + newStatus.getDisplay() + "\n\n" +
                        "Thank you for your reservation!";

                // Send the email
                emailSender.sendSimpleEmail(subject, msg, emailConfig.getDefaultFromEmailAddress(), reservationRequest.getEmail());
            } else {
                System.out.println("Can't send email for user: " + reservationRequest.getFullName() + ", no email was provided");
            }
        } catch (Exception ex) {
            System.out.println("Error sending email: " + ex.getMessage());
        }
    }


    @GetMapping(value = "/seating/edit/{id}")
    public String edit(Model model, @PathVariable int id) {
        var item = seatingRepo.findById(id);

        if (item.isPresent()) {
            model.addAttribute("seating", item.get());
        }

        return "seating/edit";
    }

    @PostMapping(value = "/seating/edit/{id}")
    public String edit(@Valid Seating seatingToUpdate, BindingResult br, @PathVariable int id, Model model) {
        if(!br.hasErrors()){
            // get existing Seating object from the database
            var seatings = seatingRepo.findById(id);

            if (seatings.isPresent()){

                var seating = seatings.get();

                // Update the existing Seating object with the new data
                seating.setStartDateAndTime(seatingToUpdate.getStartDateAndTime());

                seatingRepo.save(seating);
                return "redirect:/seatings";
            }
        }
        return "seating/edit";
    }

    @GetMapping(value = "/seating/delete/{id}")
    public String delete(@PathVariable int id, Model model) {
        var item = seatingRepo.findById(id);

        if (item.isPresent()) {
            model.addAttribute("seating", item.get());
            var event = eventRepo.findById(item.get().getEvent().getId());
            if(event.isPresent()){
                model.addAttribute("event", event.get());
            }
        }

        return "seating/delete";
    }

    @PostMapping(value = "/seating/delete/{id}")
    public String delete(@PathVariable int id) {
        seatingRepo.deleteById(id);

        return "redirect:/events";
    }
}
