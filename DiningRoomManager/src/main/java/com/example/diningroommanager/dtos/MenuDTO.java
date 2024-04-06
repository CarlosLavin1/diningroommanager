package com.example.diningroommanager.dtos;

import com.example.diningroommanager.entities.MenuItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;
import java.util.List;

public class MenuDTO {
    private int id;
    @NotBlank(message = "Name is required")
    private String name;
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    private List<MenuItemDTO> menuItems;

    public MenuDTO(int id, String name, String description, Date dateCreated, List<MenuItemDTO> menuItems) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateCreated = dateCreated;
        this.menuItems = menuItems;
    }

    public MenuDTO() {
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<MenuItemDTO> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItemDTO> menuItems) {
        this.menuItems = menuItems;
    }
}
