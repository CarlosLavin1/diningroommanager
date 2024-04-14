package com.example.diningroommanager.controllers;

import com.example.diningroommanager.config.interfaces.EmailConfig;
import com.example.diningroommanager.entities.ReservationRequest;
import com.example.diningroommanager.entities.Seating;
import com.example.diningroommanager.entities.Status;
import com.example.diningroommanager.login.LoginToken;
import com.example.diningroommanager.repositories.ReservationRequestRepository;
import com.example.diningroommanager.repositories.SeatingRepository;
import com.example.diningroommanager.repositories.StatusRepository;
import com.example.diningroommanager.repositories.TableRepository;
import com.example.diningroommanager.services.EmailSender;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@Controller
public class ReservationRequestController {
    private ReservationRequestRepository reservationRequestRepository;
    private SeatingRepository seatingRepository;
    private StatusRepository statusRepository;
    private final LoginToken loginToken;
    private final TableRepository tableRepository;
    private final EmailSender emailSender;
    private final EmailConfig emailConfig;


    public ReservationRequestController(ReservationRequestRepository reservationRequestRepository, SeatingRepository seatingRepository, StatusRepository statusRepository, LoginToken loginToken, TableRepository tableRepository, EmailSender emailSender, EmailConfig emailConfig) {
        this.reservationRequestRepository = reservationRequestRepository;
        this.seatingRepository = seatingRepository;
        this.statusRepository = statusRepository;
        this.loginToken = loginToken;
        this.tableRepository = tableRepository;
        this.emailSender = emailSender;
        this.emailConfig = emailConfig;
    }

    @ModelAttribute("authenticated")
    public boolean getAuthenticated(){
        return loginToken.hasToken();
    }

    @GetMapping(value = "/res/create/{id}")
    public String create(@PathVariable int id, Model model) {
        var seating = seatingRepository.findByIdWithEventAndTables(id);
        if (seating.isEmpty()){
            seating = seatingRepository.findById(id);
        }
        if(seating.isPresent()){
            var seatings = seating.get();
            var event = seatings.getEvent();

            // Filter out the tables that are already reserved
            var availableTables = event.getLayout().getTables().stream()
                    .filter(table -> {
                        var existingReservation = reservationRequestRepository.findByDiningTableId(table.getId());
                        return existingReservation.isEmpty();
                    })
                    .toList();


            // Get only available tables
            event.getLayout().setTables(availableTables);

            model.addAttribute("seating", seatings);
            model.addAttribute("event", event);
        }
        model.addAttribute("reservationrequest", new ReservationRequest());

        return "res/create";
    }

    @PostMapping("/res/create/{id}")
    public String createReservationRequest(@PathVariable int id, @Valid @ModelAttribute(value = "reservationrequest") ReservationRequest reservationrequest, BindingResult br, Model model) {
        if (!br.hasErrors()) {
            var seating =  seatingRepository.findById(id);
            if(seating.isPresent()) {
                // Check if a reservation already exists for the selected table
                var existingReservation =
                        reservationRequestRepository.findByDiningTableId(reservationrequest.getDiningTable().getId());

                // Check if the existing reservation is not empty
                if (!existingReservation.isEmpty()) {
                    // Add an error to the BindingResult
                    br.rejectValue("diningTable.id", "error.reservationrequest", "This table is already reserved.");
                    model.addAttribute("seating", seating.get());
                    return "res/create";
                }

                // Set the Seating object on the ReservationRequest
                reservationrequest.setSeating(seating.get());

                // Send an email after the reservation is saved
                sendReservationEmail(reservationrequest, statusRepository);
            }
            reservationrequest.setStatus(statusRepository.getStatusByValue("pending"));

            // Save it
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

    private void sendReservationEmail(ReservationRequest res, StatusRepository statusRepository){
        var status = statusRepository.getStatusByValue("pending");

        // Fetch the DiningTable from its repository
        var diningTable = tableRepository.findById(res.getDiningTable().getId());
        if (diningTable.isPresent()) {
            res.setDiningTable(diningTable.get());
        }

        // format the date
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");

        var formattedDateTime = res.getSeating().getStartDateAndTime().format(formatter);

        try{
            // Check if the email is not null and blank
            if(res.getEmail() != null & !res.getEmail().isBlank()) {

                // Prepare subject and message for the email
                var subject = "Request received!";
                var msg = "Hello, " + res.getFullName() + ".\n\n" +
                        "Your new reservation has been created with the following details:\n" +
                        "Seating start date and time: " + formattedDateTime + "\n" +
                        "Group Size: " + res.getGroupSize() + "\n" +
                        "Reservation Status: " + status.getDisplay() + "\n" +
                        "Number of seats for table: " + res.getDiningTable().getNumberOfSeats() + "\n\n" +
                        "Thank you for your reservation!";

                // Send the mail
                emailSender.sendSimpleEmail(subject, msg, emailConfig.getDefaultFromEmailAddress(), res.getEmail());
            } else {
                System.out.println("Can't send email for user: " +  res.getFullName() + ", no email was provided");
            }
        } catch (Exception ex){
            System.out.println("Error sending email: " + ex.getMessage());
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
            ReservationRequest res = item.get();
            model.addAttribute("res", res);
            var seating = seatingRepository.findById(res.getSeating().getId());

            if(seating.isPresent()){
                model.addAttribute("seating", seating.get());
                model.addAttribute("eventId", seating.get().getEvent().getId());
            }

            // Fetch the DiningTable
            if (res.getDiningTable() != null) {
                var table = tableRepository.findById(res.getDiningTable().getId());
                if (table.isPresent()) {
                    res.setDiningTable(table.get());
                }
            }
        }
        model.addAttribute("statuses", statusRepository.findAll());
        return "res/edit";
    }

    @PostMapping(value = "/res/edit/{id}")
    public String edit(@Valid ReservationRequest res, BindingResult br, @PathVariable int id, Model model){
        if (br.hasErrors()){
            return "res/edit";
        }

        var oldRes =  reservationRequestRepository.findById(id);

        if(oldRes.isPresent()){

            // Check if reservation is approved
            var isApproved = oldRes.get().getStatus().getValue().equals("approved");

            if (isApproved){
                // send a error message to the user
                model.addAttribute("resError", "This reservation has been approved and cannot be changed.");

                // Repopulate the model attributes
                return getString(model, oldRes);
            }

            res.setSeating(oldRes.get().getSeating());

            // Check if a table is associated with the reservation
            var tableWithReservation = oldRes.get().getDiningTable();

            if (tableWithReservation == null) {
                // send a error message to the user
                model.addAttribute("resError", "A specific table from the layout of the event must be associated with the reservation before it can be approved.");

                // Repopulate the model attributes
                return getString(model, oldRes);
            }
            res.setDiningTable(tableWithReservation);

            // Check if the status has changed
            if (!Objects.equals(oldRes.get().getStatus().getId(), res.getStatus().getId())) {
                // Send email to the user about the status change
                sendReservationStatusEmail(res, oldRes.get().getStatus(), res.getStatus());
            }
        }

        var item = seatingRepository.findById(res.getSeating().getId());

        Seating seating = null;
        if(item.isPresent()) seating = item.get();
        assert seating != null;
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

    private String getString(Model model, Optional<ReservationRequest> oldRes) {

        // Add the current ReservationRequest to the model
        model.addAttribute("res", oldRes.get());

        // Find the Seating associated with the ReservationRequest
        var seating = seatingRepository.findById(oldRes.get().getSeating().getId());

        // If the Seating is present add the associated Event's ID to the model
        if(seating.isPresent()){
            model.addAttribute("seating", seating.get());
            model.addAttribute("eventId", seating.get().getEvent().getId());
        }

        model.addAttribute("statuses", statusRepository.findAll());
        return "res/edit";
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
