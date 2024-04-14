package com.example.diningroommanager.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Seating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seatingId")
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", foreignKey = @ForeignKey(name = "FK_Seating_Event"))
    private Event event;
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    @NotNull(message = "Start date and time is required")
    private LocalDateTime startDateAndTime;

    @OneToMany(mappedBy = "seating", fetch = FetchType.LAZY, cascade = CascadeType.ALL)//cascade makes all related seatings get deleted if event gets deleted
    private List<ReservationRequest> reservationRequests;

    public Seating(int id, Event event, LocalDateTime startDateAndTime) {
        this.id = id;
        this.event = event;
        this.startDateAndTime = startDateAndTime;
    }

    public Seating(Event event, LocalDateTime startDateAndTime) {
        this.event = event;
        this.startDateAndTime = startDateAndTime;
    }


    public Seating() {
    }

    public List<ReservationRequest> getReservationRequests() {
        return reservationRequests;
    }

    public void setReservationRequests(List<ReservationRequest> reservationRequests) {
        this.reservationRequests = reservationRequests;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public LocalDateTime getStartDateAndTime() {
        return startDateAndTime;
    }

    public void setStartDateAndTime(LocalDateTime startDateAndTime) {
        this.startDateAndTime = startDateAndTime;
    }
}
