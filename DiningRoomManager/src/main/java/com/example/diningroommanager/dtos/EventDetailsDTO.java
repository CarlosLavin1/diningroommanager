package com.example.diningroommanager.dtos;

import com.example.diningroommanager.entities.Menu;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public class EventDetailsDTO {
    private int id;

    @NotNull(message = "Start Date is required")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate startDate;
    @NotNull(message = "End Date is required")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate endDate;
    @NotNull(message = "Seating duration is required")
    //@DateTimeFormat(pattern="HH:mm")
    private Integer seatingDuration;
    @NotBlank(message = "Name is required")
    private String name;

    private String description;
    @NotNull(message = "Price is required")
    @Positive(message = "Price can't be negative")
    private Double price;

    private List<SeatingDTO> seatings;

    private MenuDTO menu;

    //private List<MenuItemDTO> menuItems;

    public EventDetailsDTO() {
    }

    public EventDetailsDTO(int id, LocalDate startDate, LocalDate endDate, Integer seatingDuration, String name, String description, Double price, List<SeatingDTO> seatings, MenuDTO menu) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.seatingDuration = seatingDuration;
        this.name = name;
        this.description = description;
        this.price = price;
        this.seatings = seatings;
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

    public List<SeatingDTO> getSeatings() {
        return seatings;
    }

    public void setSeatings(List<SeatingDTO> seatings) {
        this.seatings = seatings;
    }

    public MenuDTO getMenu() {
        return menu;
    }

    public void setMenu(MenuDTO menu) {
        this.menu = menu;
    }
}
