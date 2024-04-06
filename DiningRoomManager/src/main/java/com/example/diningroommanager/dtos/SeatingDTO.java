package com.example.diningroommanager.dtos;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class SeatingDTO {
    private int id;
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    @NotNull(message = "Start date and time is required")
    private LocalDateTime startDateAndTime;

    public SeatingDTO() {
    }

    public SeatingDTO(int id, LocalDateTime startDateAndTime) {
        this.id = id;
        this.startDateAndTime = startDateAndTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getStartDateAndTime() {
        return startDateAndTime;
    }

    public void setStartDateAndTime(LocalDateTime startDateAndTime) {
        this.startDateAndTime = startDateAndTime;
    }
}
