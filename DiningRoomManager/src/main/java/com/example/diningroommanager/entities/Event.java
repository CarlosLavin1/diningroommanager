package com.example.diningroommanager.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventId")
    private int id;

    @NotNull(message = "Start Date is required")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate startDate;
    @NotNull(message = "End Date is required")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate endDate;
    @NotNull(message = "Seating duration is required")
    @DateTimeFormat(pattern="HH:mm")
    private LocalTime seatingDuration;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    @NotNull(message = "Price is required")
    @Positive(message = "Price can't be negative")
    private Double price;
    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL)//cascade makes all related seatings get deleted if event gets deleted
    private List<Seating> seatings;

    public Event() {
    }

    public Event(LocalDate startDate, LocalDate endDate, LocalTime seatingDuration, String name, String description, Double price, List<Seating> seatings) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.seatingDuration = seatingDuration;
        this.name = name;
        this.description = description;
        this.price = price;
        this.seatings = seatings;
    }

    public Event(int id, LocalDate startDate, LocalDate endDate, LocalTime seatingDuration, String name, String description, Double price, List<Seating> seatings) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.seatingDuration = seatingDuration;
        this.name = name;
        this.description = description;
        this.price = price;
        this.seatings = seatings;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getSeatingDuration() {
        return seatingDuration;
    }

    public void setSeatingDuration(LocalTime seatingDuration) {
        this.seatingDuration = seatingDuration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Seating> getSeatings() {
        return seatings;
    }

    public void setSeatings(List<Seating> seatings) {
        this.seatings = seatings;
    }
}
