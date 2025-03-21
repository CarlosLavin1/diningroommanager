package com.example.diningroommanager.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @Min(value = 30, message = "Seating duration can't be shorter than 30 minutes")
    @Max(value = 240, message = "Seating duration can't be longer than 4 hours")
    //@DateTimeFormat(pattern="HH:mm")
    private Integer seatingDuration;
    @NotBlank(message = "Name is required")
    @Size(min = 5, max = 100, message = "Name must be between 5 and 100 characters.")
    private String name;

    private String description;
    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price can't be negative")
    @Max(value = 10000, message = "Event price cannot exceed $10,000.00")
    private Double price;
    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL)//cascade makes all related seatings get deleted if event gets deleted
    private List<Seating> seatings;


    @NotNull(message = "Please select a layout")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "layoutId", foreignKey = @ForeignKey(name = "FK_Layout_Event"))
    private Layout layout;

    @NotNull(message = "Please select a menu")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menuId", foreignKey = @ForeignKey(name = "FK_Menu_Event"))
    private Menu menu;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public Event() {
    }


    public Event(LocalDate startDate, LocalDate endDate, Integer seatingDuration, String name, String description, Double price, List<Seating> seatings, Layout layout, Menu menu) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.seatingDuration = seatingDuration;
        this.name = name;
        this.description = description;
        this.price = price;
        this.seatings = seatings;
        this.layout = layout;
        this.menu = menu;
    }

    public Event(int id, LocalDate startDate, LocalDate endDate, Integer seatingDuration, String name, String description, Double price, List<Seating> seatings, Layout layout, Menu menu) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.seatingDuration = seatingDuration;
        this.name = name;
        this.description = description;
        this.price = price;
        this.seatings = seatings;
        this.layout = layout;
        this.menu = menu;
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

    public Integer getSeatingDuration() {
        return seatingDuration;
    }

    public void setSeatingDuration(Integer seatingDuration) {
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
