package com.example.diningroommanager.dtos;

import jakarta.validation.constraints.NotBlank;

public class MenuItemDTO {
    private int id;

    @NotBlank(message = "Name is required")
    private String name;
    private String description;

    public MenuItemDTO(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public MenuItemDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
