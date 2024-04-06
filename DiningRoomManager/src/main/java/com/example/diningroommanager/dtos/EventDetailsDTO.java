package com.example.diningroommanager.dtos;

import com.example.diningroommanager.entities.Layout;
import com.example.diningroommanager.entities.Menu;
import com.example.diningroommanager.entities.Seating;
import jakarta.persistence.*;
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

    private List<Integer> seatingIds;

    private Integer layoutId;


    private Integer menuId;

    public EventDetailsDTO() {
    }

    public EventDetailsDTO(int id, LocalDate startDate, LocalDate endDate, Integer seatingDuration, String name, String description, Double price, List<Integer> seatingIds, Integer layoutId, Integer menuId) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.seatingDuration = seatingDuration;
        this.name = name;
        this.description = description;
        this.price = price;
        this.seatingIds = seatingIds;
        this.layoutId = layoutId;
        this.menuId = menuId;
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

    public List<Integer> getSeatingIds() {
        return seatingIds;
    }

    public void setSeatingIds(List<Integer> seatingIds) {
        this.seatingIds = seatingIds;
    }

    public Integer getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(Integer layoutId) {
        this.layoutId = layoutId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
}
