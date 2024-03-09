package com.example.diningroommanager.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
public class Seating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seatingId")
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", foreignKey = @ForeignKey(name = "FK_Seating_Event"))
    @NotNull(message = "Event is required")
    private Event event;
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    @NotNull(message = "Start date and time is required")
    private LocalDateTime StartDateAndTime;

    public Seating(int id, Event event, LocalDateTime startDateAndTime) {
        this.id = id;
        this.event = event;
        StartDateAndTime = startDateAndTime;
    }

    public Seating() {
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
        return StartDateAndTime;
    }

    public void setStartDateAndTime(LocalDateTime startDateAndTime) {
        StartDateAndTime = startDateAndTime;
    }
}
