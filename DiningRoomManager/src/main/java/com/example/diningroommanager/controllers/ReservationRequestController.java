package com.example.diningroommanager.controllers;

import com.example.diningroommanager.entities.ReservationRequest;
import com.example.diningroommanager.entities.Seating;
import com.example.diningroommanager.repositories.*;

import com.example.diningroommanager.services.EmailService;
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
    private TableRepository tableRepository;
    private EventRepository eventRepository;
    private final LayoutRepository layoutRepository;
    private final EmailService emailService;


    public ReservationRequestController(ReservationRequestRepository reservationRequestRepository, SeatingRepository seatingRepository, StatusRepository statusRepository, TableRepository tableRepository, EventRepository eventRepository, LayoutRepository layoutRepository, EmailService emailService) {
        this.reservationRequestRepository = reservationRequestRepository;
        this.seatingRepository = seatingRepository;
        this.statusRepository = statusRepository;
        this.tableRepository = tableRepository;
        this.eventRepository = eventRepository;
        this.layoutRepository = layoutRepository;
        this.emailService = emailService;
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

                // Send an email after the reservation is saved
                emailService.sendSimpleEmail("New Reservation", "A new reservation has been created.", "from@nbcc.com", "to@test.com");

                reservationrequest.setSeating(seating.get());
            }
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

    @PostMapping(value = "res/approve/{id}")
    public String approve(@Valid BindingResult br, ReservationRequest reservationRequest, @PathVariable int id, Model model){
        if (br.hasErrors()) {
            // handle errors
        }

        var table = tableRepository.findById(id);
        var reservation = reservationRequestRepository.findById(reservationRequest.getId());

        if (table.isPresent() && reservation.isPresent()) {
            if (table.get().getId() == reservation.get().getDiningTable().getId()) {
                reservation.get().setDiningTable(table.get());
                reservationRequestRepository.save(reservation.get());
                model.addAttribute("successMessage", "Table assigned successfully!");
            } else {
                model.addAttribute("errorMessage", "Table ID does not match the reservation.");
            }
        } else {
            model.addAttribute("errorMessage", "Error assigning table.");
        }

        return "redirect:/res/approve/" + id;
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

                model.addAttribute("res", oldRes.get());

                var seating = seatingRepository.findById(oldRes.get().getSeating().getId());

                if(seating.isPresent()){
                    model.addAttribute("seating", seating.get());
                    model.addAttribute("eventId", seating.get().getEvent().getId());
                }

                model.addAttribute("statuses", statusRepository.findAll());
                return "res/edit";
            }

            res.setSeating(oldRes.get().getSeating());

            // Check if a table is associated with the reservation
            var tableWithReservation = oldRes.get().getDiningTable();

            if (tableWithReservation == null) {
                // send a error message to the user
                model.addAttribute("resError", "A specific table from the layout of the event must be associated with the reservation before it can be approved.");

                // Repopulate the model attributes
                model.addAttribute("res", oldRes.get());
                var seating = seatingRepository.findById(oldRes.get().getSeating().getId());
                if(seating.isPresent()){
                    model.addAttribute("seating", seating.get());
                    model.addAttribute("eventId", seating.get().getEvent().getId());
                }
                model.addAttribute("statuses", statusRepository.findAll());
                return "res/edit";
            }
            res.setDiningTable(tableWithReservation);
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
